package com.gdx.musicevents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

public class Track {
    private final String fileName;
    
    private transient FileHandle fileHandle;
    private transient Music music;
    private transient float position = 0;
    
    public Track() {
        fileName = null;
    }
    
    public Track(String fileName){
        this.fileName = fileName;
    }

    public void init(State state) {
        this.fileHandle = Gdx.files.internal(this.fileName);
        this.music = Gdx.audio.newMusic(this.fileHandle);
        this.music.setOnCompletionListener(state);
    }

    public void update(float dt) {
        if(music.isPlaying()) {
            position += dt;
        }
    }
    
    public void reset() {
        position = 0;
    }
    
    public void play() {
        music.play();
    }
    
    public void stop() {
        music.stop();
    }
    
    public float getPosition() {
        return position;
    }
    
    public void setPosition(float position){
        this.position = position;
        this.music.setPosition(position);
    }
    
    public void dispose() {
        music.dispose();
        
    }
    
    public float getVolume() {
        return music.getVolume();
    }
    
    public void setVolume(float volume){
        music.setVolume(volume);
    }
    
    @Override
    public String toString() {
        return fileName;
    }

}
