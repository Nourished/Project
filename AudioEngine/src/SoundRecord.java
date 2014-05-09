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
 * 
 * @autho ERIC GUMMERSON
 * @std#: 4585469
 */
public class SoundRecord {

    int samples;        //Number of samples in audio file
    int bitsPerSample;  //Number of bits per sample
    int channels;       //Number of channels in audio file
    int sampleRate;     //Sampling rate of audio file
    boolean normalized; //Audio file normalization status
    int[] channelOne;   //channel one integer array for sample values
    int[] channelTwo;   //channel two integer array for sample values

    public SoundRecord() {
        this.samples = 0;           
        this.bitsPerSample = 0;
        this.channels = 0;
        this.sampleRate = 0;
        this.normalized = false;
        int[] channelOne = null;
        int[] channelTwo = null;
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
