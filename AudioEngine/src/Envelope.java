/**
 * class Envelope
 *
 * the Envelope class handles all envelope operations. 
 * -sine, hann, hamming, gaussian, and attack-decay-sustain-release envelopes
 *  
 * Used the following web sites for help with envelopes
 * - http://www.media.aau.dk/~sts/ad/granular.html
 * - http://michaelkrzyzaniak.com/AudioSynthesis/2_Audio_Synthesis/11_Granular_Synthesis/1_Window_Functions/
 * - http://michaelkrzyzaniak.com/AudioSynthesis/2_Audio_Synthesis/3_Envelopes/5_ADSR/
 * - 
 * @author PHILIP FRACZKOWSKI
 * @std#: 4597290
 * 
 * @autho ERIC GUMMERSON
 * @std#: 4585469
 */

public class Envelope {

    public Envelope() {        
    }

    /* double[] sinEnvelope(SoundRecord audio,int envelopeDuration)
     *  
     * - this method creates and returns a sine envelope
     */
    public double[] sinEnvelope(SoundRecord audio) {

        double[] envelope = new double[1 * audio.sampleRate];

        for (int i = 0; i < envelope.length; i++) {
            envelope[i] = Math.sin(Math.PI * ( (double) i / audio.sampleRate));            
        }

        return envelope;
    }//End of sinEnvelope

    /* double[] hannEnvelope(SoundRecord audio,int envelopeDuration)
     *  
     * -  this method creates and returns a hann envelope
     */
    public double[] hannEnvelope(SoundRecord audio, int envelopeDuration) {

        double[] envelope = new double[envelopeDuration * audio.sampleRate];

        for (int i = 0; i < envelope.length; i++) {
            envelope[i] = 0.5 * (1 - Math.cos(2 * Math.PI * i / (envelopeDuration * audio.sampleRate)));
            
        }

        return envelope;
    }//End of hannEnvelope

    /* double[] hammEnvelope(SoundRecord audio,int envelopeDuration)
     *  
     * -  this method creates and returns a hamm envelope
     */
    public double[] hammEnvelope(SoundRecord audio, int envelopeDuration) {

        double[] envelope = new double[envelopeDuration * audio.sampleRate];

        for (int i = 0; i < envelope.length; i++) {
            envelope[i] = 0.54 - 0.46 * Math.cos(2 * Math.PI * (double) i / (audio.sampleRate * envelopeDuration));
        }

        return envelope;
    }//End of hammEnvelope

    /* double[] gaussianEnvelope(SoundRecord audio,int envelopeDuration, double width)
     * 
     * -  this method creates and returns a gaussian envelope
     * - width determines the spread of the guassian equation
     */
    public double[] gaussianEnvelope(SoundRecord audio, int envelopeDuration, double width) {

        double[] envelope = new double[envelopeDuration * audio.sampleRate];

        for (int i = 0; i < envelope.length; i++) {

            envelope[i] = Math.pow(Math.E, -0.5 * Math.pow(((i - (audio.sampleRate * envelopeDuration) / 2) / (width * (audio.sampleRate * envelopeDuration) / 2)), 2));
        }

        return envelope;
    }//End of gaussianEnvelope

    /* adsrEnvelope(SoundRecord audio,int envelopeDuration, double attack, double decay, double sustain, double release)
     *    
     *  -  this method creates and returns a attack decay sustan release (ADSR) envelope
     *  - attact, decay, sustain release variables are passed in range between 0-1
     */
    public double[] adsrEnvelope(SoundRecord audio, int envelopeDuration, double attack, double decay, double sustain, double release) {

        double[] envelope = new double[envelopeDuration * audio.sampleRate];

        attack = attack * audio.sampleRate;
        decay = decay * audio.sampleRate;
        release = release * audio.sampleRate;

        for (int i = 0; i < envelope.length; i++) {

            if (i < attack) {
                envelope[i] = ( (double) i / attack);
            } else if (i < (attack + decay)) {
                envelope[i] = 1 - (1 - sustain) * ((  (double) i - attack) / decay);
            } else if (i <= ((envelopeDuration * audio.sampleRate) - release)) {
                envelope[i] = sustain;
            } else {
                envelope[i] = sustain * ((((envelopeDuration * audio.sampleRate) - (double) i) / release));
            }
        }      

        return envelope;

    }//End of adsrEnvelope     

} //End of Envelope.java
