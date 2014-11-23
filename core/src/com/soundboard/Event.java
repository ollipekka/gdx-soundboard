package com.soundboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

public class Event {
    private final String name;
    private final FileHandle fileHandle;
    private boolean matchPosition;

    private final Music music;

    private boolean looping = false;

    private float position = 0;

    public Event(final String name, final FileHandle fileHandle) {
        this.name = name;
        this.fileHandle = fileHandle;
        music = Gdx.audio.newMusic(fileHandle);
        music.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                Gdx.app.log("SoundBoard", "Event complete " + name);
                position = 0;
                if(looping){
                    music.play();
                }
            }
        });
    }

    public String getName() {
        return name;
    }

    public FileHandle getFileHandle() {
        return fileHandle;
    }

    public boolean isMatchPosition() {
        return matchPosition;
    }

    public void setMatchPosition(boolean matchPosition) {
        this.matchPosition = matchPosition;
    }

    public Music getMusic() {
        return music;
    }

    public boolean isLooping() {
        return looping;
    }

    public void setLooping(boolean looping) {
        this.looping = looping;
    }

    public float getPosition() {
        return position;
    }

    public void setPosition(float position) {
        this.position = position;
        music.setPosition(position);
    }

    @Override
    public String toString() {
        return name + " (" + fileHandle.name() + ")";
    }

    public void update(float dt){
        position += dt;
    }

}