
/**
 * class SoundRecord
 *
 * create SoundRecord objects which will hold the vital information regarding
 * the wave table passed in. SoundRecord stores the number of samples, bits per
 * sample, number of channels, the sampling rate, if it is normalized, and the
 * sample table.
 *
 * @author PHILIP FRACZKOWSKI
 * @std#: 4597290
 */
public class SoundRecord {

    int samples;
    int bitsPerSample;
    int channels;
    int sampleRate;
    boolean normalized;
    int[] channelOne;
    int[] channelTwo;

    public SoundRecord() {
        this.samples = 0;
        this.bitsPerSample = 0;
        this.channels = 0;
        this.sampleRate = 0;
        this.normalized = false;
        int[] channelOne = null;
        int[] channelTwo = null;
    }

    public void populate(int length) {
        this.channelOne = new int[length];
    }

    public void copy(short[] dataFrom) {
        for (int i = 0; i < dataFrom.length; i++) {
            this.channelOne[i] = dataFrom[i];
        }

    }

    public void printOut() {
        System.out.println("-------------------------------------");
        System.out.println("\t\tSOUND RECORD DATA");
        System.out.println("-------------------------------------");
        System.out.println("Samples: " + samples + "\nBits: " + bitsPerSample
                + "\nChannels " + channels + "\nSample Rate "
                + sampleRate + "\nNormalized " + normalized + "\n");

        for (int i = 0; i < channelOne.length; i++) {
            System.out.println("Channel 1[" + i + "] " + channelOne[i]);
            if(channels == 2)
                System.out.println("Channel 2[" + i + "] " + channelTwo[i]);
        }
        System.out.println("-------------------------------------");
    }

}
