package com.github.achordion.client;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
public class AudioRecorder {

    //AudioFormat is a class that allows for interpretation of audio sounds
    private AudioFormat Format;
    //this will act as the line-in from the audio source
    private TargetDataLine Line;
    //flag for whether recording is happening or not
    private Boolean isRecording = false;

    public AudioRecorder() {
        //paramters (SamepleRate, sampleSizeInBits, Channels(1 is mono, 2 is stereo,true for singed integer,
        //true for bigEndian byte order(false is little Endian)
        Format = new AudioFormat(44100, 16, 1, true, true);
    }
    //the string filepath is for saving the audio clip
    public void startRecording(File tempFile) {
        try{
            //the line and Formate declared abover are used here
            Line = AudioSystem.getTargetDataLine(Format);
            Line.open(Format);
            Line.start();
            isRecording = true;

            //A thread must be created to handle audio recording
            Thread thread = new Thread(()->{
                try (AudioInputStream audioInStream = new AudioInputStream(Line)) {
                    AudioSystem.write(audioInStream, AudioFileFormat.Type.WAVE, tempFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }catch(LineUnavailableException e){
            e.printStackTrace();
        }
    }
    public void stopRecording() {
        if(isRecording){
            isRecording = false;
            Line.stop();
            Line.close();
        }
    }

    public void playAudio(File audioFile){
        try(AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile)){
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();

            clip.addLineListener(event -> {
                if(event.getType() == LineEvent.Type.STOP){
                    clip.close();
                }
            });
        }catch(UnsupportedAudioFileException | LineUnavailableException | IOException e){
            e.printStackTrace();
        }
    }
}
