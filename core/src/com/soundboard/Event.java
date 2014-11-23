package com.soundboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

public class Event {
    private final String name;
    private final FileHandle fileHandle;
    private boolean matchPosition;

    private final Music music;


    public Event(final String name, final FileHandle fileHandle) {
        this.name = name;
        this.fileHandle = fileHandle;
        music = Gdx.audio.newMusic(fileHandle);
        music.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                Gdx.app.log("SoundBoard", "Event complete " + name);
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

    @Override
    public String toString() {
        return name + " (" + fileHandle.name() + ")";
    }

}