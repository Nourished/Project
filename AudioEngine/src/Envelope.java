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
    public double[] sinEnvelope(SoundRecord audio, int envelopeDuration) {

        double[] envelope = new double[envelopeDuration * audio.sampleRate];

        for (int i = 0; i < envelope.length; i++) {
            envelope[i] = Math.sin(Math.PI * ((double) i / audio.sampleRate));
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
            envelope[i] = .54 - 0.46 * Math.cos(2 * Math.PI * i / (audio.sampleRate * envelopeDuration));
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
        sustain = sustain * audio.sampleRate;
        release = release * audio.sampleRate;

        for (int i = 0; i < envelope.length; i++) {

            if (i < attack) {
                envelope[i] = (i / attack);
            } else if (i < (attack + decay)) {
                envelope[i] = 1 - (1 - sustain) * ((i - attack) / decay);
            } else if (i <= ((envelopeDuration * audio.sampleRate) - release)) {
                envelope[i] = sustain;
            } else {
                envelope[i] = sustain * ((((envelopeDuration * audio.sampleRate) - i) / release));
            }
        }

        return envelope;

    }//End of adsrEnvelope

    /* audioToText(string fileName)    
        Pass in a string which is the path of the 
     */
    void audioToTxt(String fileName, String filePath) {

        String newFileName = fileName.substring(0, fileName.length() - 4);

        String base = System.getProperty("user.dir") + "\\src\\";
        //System.out.println(base);
        
        filePath = filePath.substring(0, filePath.length()- fileName.length());
        System.out.println(filePath);
        System.out.println(fileName + " " + newFileName);
        
        String cmd
                = "wav2txt " + fileName + " > " + newFileName + ".txt";
        try {
            Runtime.getRuntime().exec(
                    new String[]{"cmd.exe", "cd " + filePath, cmd});
            System.out
                    .println("Txt file constructed: \\src\\" + newFileName + ".txt");

        } catch (IOException ex) {
            System.out.println("FAILED: " + ex.getMessage());

        }
    }//End of audioToTxt

    /* txtToAudio(String fileName)
     */
    void txtToAudio(String fileName) {

     String base = System.getProperty("user.dir") + "/src/music/";
        String cmd
                = base + "txt2wav " + base + fileName + ".wav < " + base + fileName + ".txt";
        try {
            Runtime.getRuntime().exec(
                    new String[] {"cmd.exe", "/c",  cmd});
            System.out
                    .println("Wav file constructed: /src/music/" + fileName + ".wav");

        } catch (IOException ex) {
            System.out.println("FAILED: " + ex.getMessage());
    
        }   
    }//End of txtToAudio

    /* readFile(String fileName, SoundRecord record)
     */
    void readFile(String fileName, SoundRecord record) {

        File file = new File(fileName);

        try {
            Scanner lineReader = new Scanner(file);
            lineReader.next();
            record.samples = lineReader.nextInt();
            lineReader.next();
            record.bitsPreSample = lineReader.nextInt();
            lineReader.next();
            record.channels = lineReader.nextInt();
            lineReader.next();
            record.sampleRate = lineReader.nextInt();
            lineReader.next();
            record.normalized = false;
            lineReader.next();

            if (record.channels == 1) {
                record.channelOne = new short[record.samples];
            } else {
                record.channelOne = new short[record.samples];
                record.channelTwo = new short[record.samples];
            }

            if (record.channels == 1) {
                for (int i = 0; i < record.channelOne.length; i++) {
                    record.channelOne[i] = lineReader.nextShort();
                }
            } else {
                for (int i = 0; i < record.channelOne.length; i++) {
                    record.channelOne[i] = lineReader.nextShort();
                    record.channelTwo[i] = lineReader.nextShort();
                }
            }

            lineReader.close();

        } catch (Exception e) {
            System.out.println("Error reading file " + fileName);
        }

    }//End of readFile

    /* outputFile(String fileName, SoundRecord record)     
     */
    void outputFile(String fileName, SoundRecord record) {

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            out.write("SAMPLES: \t" + record.channelOne.length);
            out.newLine();
            out.write("BITSPERSAMPLE: \t" + record.bitsPreSample);
            out.newLine();
            out.write("CHANNELS: \t" + record.channels);
            out.newLine();
            out.write("SAMPLERATE: \t" + record.sampleRate);
            out.newLine();
            out.write("NORMALIZED: \t" + "FALSE");
            out.newLine();

            if (record.channels == 1) {
                for (int i = 0; i < record.channelOne.length; i++) {
                    out.write(record.channelOne[i] + "\n");
                    out.newLine();
                }
            } else {
                for (int i = 0; i < record.channelOne.length; i++) {
                    out.write(record.channelOne[i] + "\t");
                    out.write(record.channelTwo[i] + "\n");
                    out.newLine();
                }
            }

            out.close();
        } catch (IOException e) {
            System.out.println("Error outputing the file " + fileName);
        }

    } //End of outPutFile

}
