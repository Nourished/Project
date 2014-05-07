



/** class SoundRecord
 *
 *  create SoundRecord objects which will hold the vital information regarding
 *  the wave table passed in. SoundRecord stores the number of samples, bits per
 *  sample, number of channels, the sampling rate, if it is normalized, and
 *  the sample table.
 * 
 * @author PHILIP FRACZKOWSKI
 * @std#: 4597290
 */
public class SoundRecord {
    int samples;
    int bitsPreSample;
    int channels;
    int sampleRate;
    boolean normalized;
    short[] channelOne;
    short[] channelTwo;
    
    public SoundRecord() {
     this.samples = 0;
     this.bitsPreSample = 0;
     this.channels = 0;
     this.sampleRate = 0;
     normalized = false;
    // short[] channelOne = null;
    // short[] channelTwo = null;
}
       
    public void populate(int length){
        this.channelOne = new short[length];        
    }
    
    public void copy( short[] dataFrom){
        for(int i = 0 ; i < dataFrom.length ; i++){
            this.channelOne[i] = dataFrom[i];
        }
        
    }
    
}