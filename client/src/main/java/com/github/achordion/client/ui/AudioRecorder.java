package com.github.achordion.client.ui;

import com.github.achordion.client.protocol.MainHandler;
import com.github.achordion.client.protocol.handling.events.AudioEvent;

import javax.sound.sampled.*;
import java.io.*;

public class AudioRecorder {

    //AudioFormat is a class that allows for interpretation of audio sounds
    private AudioFormat Format;
    //this will act as the line-in from the audio source
    private TargetDataLine Line;
    //flag for whether recording is happening or not
    private Boolean isRecording = false;
    private MainHandler mainHandler;
    //this will hold listeners
    public AudioRecorder() {
        //paramters (SamepleRate, sampleSizeInBits, Channels(1 is mono, 2 is stereo,true for singed integer,
        //true for bigEndian byte order(false is little Endian)
       this.Format = new AudioFormat(
               AudioFormat.Encoding.PCM_SIGNED,
               44100,
               16,
               1,
               2,
               44100,
               true);
       this.mainHandler = MainHandler.getInstance();
    }


    //the string filepath is for saving the audio clip
    public void startRecording() {
        try{
            //the line and Formate declared abover are used here
            Line = AudioSystem.getTargetDataLine(Format);
            Line.open(Format);
            Line.start();
            isRecording = true;

            //A thread must be created to handle audio recording
            Thread recordingthread = new Thread(()->{
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int bytesReadTotal = 0;
                while(isRecording){
                    int bytesRead = Line.read(buffer, 0, buffer.length);
                    if(bytesRead > 0){
                        out.write(buffer, 0, bytesRead);
                        bytesReadTotal += bytesRead;
                    }
                }
                InputStream ips = new ByteArrayInputStream(out.toByteArray());
                out = new ByteArrayOutputStream();
                try (AudioInputStream audioInStream = new AudioInputStream(ips, this.Format, bytesReadTotal)) {
                    AudioSystem.write(audioInStream, AudioFileFormat.Type.WAVE, out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                byte[] audiodata = out.toByteArray();
                AudioEvent event = new AudioEvent(this,audiodata);
                mainHandler.sendAudioEvent(event);
            });
            recordingthread.start();
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
    //DELETE ME!!!
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
