package com.gdx.musicevents;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.tools.flame.EventManager;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.gdx.musicevents.effects.Effect;
import com.gdx.musicevents.effects.Play;
import com.gdx.musicevents.effects.Stop;

public class State implements OnCompletionListener{

    private final String name;
    
    private final Array<Track> tracks = new Array<Track>();

    private final ObjectMap<String, Effect> enterTransitions = new ObjectMap<String, Effect>();
    private final ObjectMap<String, Effect> exitTransitions = new ObjectMap<String, Effect>();
    
    private boolean looping = false;
    
    private transient MusicEventManager manager;
    private transient int currentTrackIndex;
    
    public State(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public void addTrack(Track track){
        track.init(this);
        this.getTracks().add(track);
    }
    
    public void removeTrack(Track track){
        this.getTracks().removeValue(track, true);
    }

    @Override
    public void onCompletion(Music music) {
        if(isLooping()){
            Track currentTrack = null;
            if(getTracks().size > 1) {
                currentTrackIndex = MathUtils.random(getTracks().size - 1);
                currentTrack = getCurrentTrack();
            } else {
                currentTrack = getCurrentTrack();
                currentTrack.reset();
            }
            currentTrack.play();
        } else {
            currentTrackIndex = (currentTrackIndex + 1) % tracks.size;
            Track currentTrack = getCurrentTrack();
            if(currentTrack != null){
                currentTrack.play();
            }
            
        }
    }
    
    public void init(MusicEventManager manager) {
        this.manager = manager;
        for(int i = 0; i < getTracks().size; i++){
            Track track = getTracks().get(i);
            track.init(this);
        }
    }
    
    public Effect enter(State previousState) {
        
        Effect effect = null;
        if(previousState != null){
            effect = enterTransitions.get(previousState.getName());
        }

        if(effect == null){
            effect = new Play();
        }

        effect.start(this, previousState);
        
        
        return effect;
        
    }
    
    public Effect exit(State nextState) {
        Effect effect = exitTransitions.get(nextState.getName());

        if(effect == null){
            effect = new Stop();
        }

        effect.start(this, nextState);
        return effect;
    }
    
    public void update(float dt) {
        Track currentTrack = getCurrentTrack();
        currentTrack.update(dt);
    }
    
    public void play() {
        State currentState = manager.getCurrentEvent();
        if(currentState != this) {
            manager.play(this.name);
        } else {
            Track currentTrack = getCurrentTrack();
            currentTrack.play();
        }
    }
    
    public void stop() {
        Track currentTrack = getCurrentTrack();
        currentTrack.stop();
    }
    
    public void dispose() {
        for(int i = 0; i < getTracks().size; i++) {
            Track track = getTracks().get(i);
            track.dispose();
        }
        
        Track currentTrack = getCurrentTrack();
        currentTrack.stop();
    }

    public ObjectMap<String, Effect> getEnterTransitions() {
        return enterTransitions;
    }

    public ObjectMap<String, Effect> getExitTransitions() {
        return exitTransitions;
    }

    public void addEnterTransition(String eventName, Effect effect) {
        this.enterTransitions.put(eventName, effect);
    }

    public void removeEnterTransition(String name) {
        this.enterTransitions.remove(name);

    }
    public void addExitTransition(String eventName, Effect effect) {
        this.exitTransitions.put(eventName, effect);
    }

    public void removeExitTransition(String name) {
        this.exitTransitions.remove(name);

    }
    
    public Track getCurrentTrack() {
        if(currentTrackIndex < 0 || currentTrackIndex >= tracks.size){
            return null;
        }
        
        return getTracks().get(currentTrackIndex);
    }

    public boolean isLooping() {
        return looping;
    }

    public void setLooping(boolean looping) {
        this.looping = looping;
    }
    
    @Override
    public String toString() {
        return this.name;
    }

    public Array<Track> getTracks() {
        return tracks;
    }
}
