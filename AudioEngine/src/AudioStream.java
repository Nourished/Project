/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

/**
 *
 * @author Phil
 */
public class AudioStream {

    File song;
    boolean songLoaded = false;
    /**
     * Buffers hold sound data.
     */
    IntBuffer buffer = BufferUtils.createIntBuffer(1);

    /**
     * Sources are points emitting sound.
     */
    IntBuffer source = BufferUtils.createIntBuffer(1);

    /**
     * Position of the source sound.
     */
    FloatBuffer sourcePos = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f}).rewind();

    /**
     * Velocity of the source sound.
     */
    FloatBuffer sourceVel = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f}).rewind();

    /**
     * Position of the listener.
     */
    FloatBuffer listenerPos = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f}).rewind();

    /**
     * Velocity of the listener.
     */
    FloatBuffer listenerVel = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f}).rewind();

    /**
     * Orientation of the listener. (first 3 elements are "at", second 3 are
     * "up")
     */
    FloatBuffer listenerOri = (FloatBuffer) BufferUtils.createFloatBuffer(6).put(new float[]{0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f}).rewind();

    /**
     * boolean LoadALData()
     *
     * This function will load our sample data from the disk using the Alut
     * utility and send the data into OpenAL as a buffer. A source is then also
     * created to play that buffer.
     */
    public int loadALData() {
        // Load wav data into a buffer.
        AL10.alGenBuffers(buffer);

        if (AL10.alGetError() != AL10.AL_NO_ERROR) {
            return AL10.AL_FALSE;
        }

        //Loads the wave file from your file system
        java.io.FileInputStream fin = null;
        BufferedInputStream bin = null;
        try {
            fin = new FileInputStream(song);
            bin = new BufferedInputStream(fin);
        } catch (java.io.FileNotFoundException ex) {
            ex.printStackTrace();
            return AL10.AL_FALSE;
        }

        WaveData waveFile = WaveData.create(bin);

        AL10.alBufferData(buffer.get(0), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();

        // Bind the buffer with the source.
        AL10.alGenSources(source);

        if (AL10.alGetError() != AL10.AL_NO_ERROR) {
            return AL10.AL_FALSE;
        }

        AL10.alSourcei(source.get(0), AL10.AL_BUFFER, buffer.get(0));
        AL10.alSourcef(source.get(0), AL10.AL_PITCH, 1.0f);
        AL10.alSourcef(source.get(0), AL10.AL_GAIN, 50.0f);
        AL10.alSource(source.get(0), AL10.AL_POSITION, sourcePos);
        AL10.alSource(source.get(0), AL10.AL_VELOCITY, sourceVel);

        // Do another error check and return.
        if (AL10.alGetError() == AL10.AL_NO_ERROR) {
            return AL10.AL_TRUE;
        }

        return AL10.AL_FALSE;
    }

    /**
     * void setListenerValues()
     *
     * We already defined certain values for the Listener, but we need to tell
     * OpenAL to use that data. This function does just that.
     */
    public void setListenerValues() {
        AL10.alListener(AL10.AL_POSITION, listenerPos);
        AL10.alListener(AL10.AL_VELOCITY, listenerVel);
        AL10.alListener(AL10.AL_ORIENTATION, listenerOri);
    }

    /**
     * void killALData()
     *
     * We have allocated memory for our buffers and sources which needs to be
     * returned to the system. This function frees that memory.
     */
    public void killALData() {
        if(songLoaded){
            songLoaded = false;
            AL10.alDeleteSources(source);
            AL10.alDeleteBuffers(buffer);
            AL.destroy();
        }
    }

    public void play() {
        AL10.alSourcePlay(source.get(0));
    }

    public void pause() {
        AL10.alSourcePause(source.get(0));
        
    }

    public void stop() {
        AL10.alSourceStop(source.get(0));

    }
    
    public File getSongFile(){
        if(songLoaded)
            return song;
        else
            return null;
    }
    
    public String getSongFileName(){
        if(songLoaded)
            return song.getName();
        else
            return "";
    }
    
    public String getSongPath(){
        if(songLoaded)
            return song.getAbsolutePath();
        else
            return "";
    }

    public void execute(File file) {
        // Initialize OpenAL and clear the error bit.

        song = file;

        try {
            AL.create();
        } catch (LWJGLException le) {
            le.printStackTrace();
            return;
        }
        AL10.alGetError();

        // Load the wav data.
        if (loadALData() == AL10.AL_FALSE) {
            System.out.println("Error loading data.");
            songLoaded = false;
            song = null;
            return;
        }

        setListenerValues();
        songLoaded = true;

    }
    
    public void updateStatus(boolean b){
        songLoaded = b;
    }
    
    public boolean status(){
        return songLoaded;
    }
}