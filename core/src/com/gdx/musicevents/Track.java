package com.gdx.musicevents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

public class Track {
    private final String name;
    private final String fileName;
    
    private transient FileHandle fileHandle;
    private transient Music music;
    private transient float position = 0;
    
    public Track(){
        name = null;
        fileName = null;
    }
    public Track(final String name) {
        this.name = name;
        this.fileName = fileHandle.path();
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
    public String getName() {
        return name;
    }
    
    public float getPosition() {
        return position;
    }

}
