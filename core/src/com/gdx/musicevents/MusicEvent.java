package com.gdx.musicevents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ObjectMap;
import com.gdx.musicevents.effects.Effect;
import com.gdx.musicevents.effects.Play;
import com.gdx.musicevents.effects.Stop;

public class MusicEvent {
    private final String name;
    private final String fileName;
    private boolean looping;
    private final ObjectMap<String, Effect> inTransitions = new ObjectMap<String, Effect>();
    private final ObjectMap<String, Effect> outTransitions = new ObjectMap<String, Effect>();

    private transient FileHandle fileHandle;
    private transient Music music;
    private transient float position = 0;


    public MusicEvent(){
        name = null;
        fileName = null;
    }
    public MusicEvent(final String name, final FileHandle fileHandle) {
        this.name = name;
        this.fileName = fileHandle.path();
        this.fileHandle = fileHandle;
        this.looping = false;
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

        if(music.isPlaying()) {
            position += dt;
        }
    }

    public void dispose() {
        if(music.isPlaying()){
            music.stop();
        }

        music.dispose();
    }

    public Effect endTransition(MusicEvent event){
        Effect effect = outTransitions.get(event.getName());

        if(effect == null){
            effect = new Stop();
        }

        //effect.start(this, event);
        return effect;

    }

    public Effect startTransition(MusicEvent event) {

        Effect effect;
        if (event == null) {
            effect = new Play();
        } else {

            effect = inTransitions.get(event.getName());

            if (effect == null) {
                effect = new Play();
            }
        }

        //effect.start(this, event);

        return effect;

    }


    public ObjectMap<String, Effect> getInTransitions() {
        return inTransitions;
    }

    public ObjectMap<String, Effect> getOutTransitions() {
        return outTransitions;
    }


    public void addInTransition(String eventName, Effect effect) {
        this.inTransitions.put(eventName, effect);
    }

    public void removeInTransition(String name) {
        this.inTransitions.remove(name);

    }
    public void addOutTransition(String eventName, Effect effect) {
        this.outTransitions.put(eventName, effect);
    }

    public void removeOutTransition(String name) {
        this.outTransitions.remove(name);

    }

    public void init(){
        this.fileHandle = Gdx.files.internal(this.fileName);
        this.music = Gdx.audio.newMusic(this.fileHandle);
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

}