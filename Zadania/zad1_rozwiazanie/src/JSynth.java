import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;


public class JSynth {
    private Synthesizer synthesizer;
    private MidiChannel channel;
    private int channelNo = 0;  //0 by default
    private int volume;
    public static final int MAX = 127;
    public static final int MIN = 0;

    public JSynth() {

        try {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open(); //Opens the device; it should now acquire any system resources it requires and become operational
            MidiChannel[] allChannels = synthesizer.getChannels();  //Array of all available channels (range 0-16)
            channel = allChannels[channelNo];    //We need one channel, no. 0
            volume = channel.getController(7);
        } catch (MidiUnavailableException e) {
            throw new IllegalStateException("Midi support is not available!");
        }
    }

    public void setVolume(int volume) {    //https://en.scratch-wiki.info/wiki/MIDI_Notes
        if (volume < 0 || volume > 127)
            throw new IllegalArgumentException("Midi volume level must be in the range 0 to 127");
        this.volume = volume;
        channel.controlChange(7, volume);
    }

    public int getVolume() {
        return volume;
    }

    public void noteOn(int noteNumber) {
        if (noteNumber < 0 || noteNumber > 127)
            throw new IllegalArgumentException("Midi note numbers must be in the range 0 to 127");
        channel.noteOn(noteNumber, volume);
    }
}
