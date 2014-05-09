/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Phil
 */
public class Envelope {

    public Envelope() {
        
    }

    /* double[] sinEnvelope(SoundRecord audio,int envelopeDuration)
     *  
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
     */
    public double[] hammEnvelope(SoundRecord audio, int envelopeDuration) {

        double[] envelope = new double[envelopeDuration * audio.sampleRate];

        for (int i = 0; i < envelope.length; i++) {
            envelope[i] = 0.54 - 0.46 * Math.cos(2 * Math.PI * (double) i / (audio.sampleRate * envelopeDuration));
            System.out.println(envelope[i]);
        }

        return envelope;
    }//End of hammEnvelope

    /* double[] gaussianEnvelope(SoundRecord audio,int envelopeDuration, double sigma)
     *  
     */
    public double[] gaussianEnvelope(SoundRecord audio, int envelopeDuration, double sigma) {

        double[] envelope = new double[envelopeDuration * audio.sampleRate];

        for (int i = 0; i < envelope.length; i++) {

            envelope[i] = Math.pow(Math.E, -0.5 * Math.pow(((i - (audio.sampleRate * envelopeDuration) / 2) / (sigma * (audio.sampleRate * envelopeDuration) / 2)), 2));
        }

        return envelope;
    }//End of gaussianEnvelope

    /* adsrEnvelope(SoundRecord audio,int envelopeDuration, double attack, double decay, double sustain, double release)
     *    
     */
    public double[] adsrEnvelope(SoundRecord audio, int envelopeDuration, double attack, double decay, double sustain, double release) {

        double[] envelope = new double[envelopeDuration * audio.sampleRate];

        attack = attack * audio.sampleRate;
        decay = decay * audio.sampleRate;
       // sustain = sustain * audio.sampleRate;
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
            System.out.println(envelope[i]);
        }
        
        

        return envelope;

    }//End of adsrEnvelope

      

}
