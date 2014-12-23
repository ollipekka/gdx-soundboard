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
    
    private transient MusicEventManager manager;
    private transient int currentTrackIndex = -1;
    
    
    private transient float volume = 1;
    
    public State(){
        this("");
    }
    
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
        currentTrackIndex = (currentTrackIndex + 1) % tracks.size;
        Track currentTrack = getCurrentTrack();
        if(currentTrack != null){
            currentTrack.play();
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
        if(currentTrack != null){
            currentTrack.update(dt);
        }
    }
    
    public void play() {
        currentTrackIndex = 0;
        State currentState = manager.getCurrentState();
        if(currentState != this) {
            manager.play(this.name);
        } else {
            playTrack();
        }
    }
    

    public void playTrack() {
        Track currentTrack = getCurrentTrack();
        if(currentTrack != null){
            currentTrack.setVolume(this.volume);
            currentTrack.play();
        }
    }
    
    public boolean isPlaying(){
        Track currentTrack = this.getCurrentTrack();
        
        return currentTrack != null;
    }
    public void stop() {
        Track currentTrack = getCurrentTrack();
        if(currentTrack != null){
            currentTrack.stop();
        }
        
        this.currentTrackIndex = -1;
    }
    
    public void dispose() {
        for(int i = 0; i < getTracks().size; i++) {
            Track track = getTracks().get(i);
            track.dispose();
        }
        
        Track currentTrack = getCurrentTrack();
        if(currentTrack != null) {
            currentTrack.stop();
        }
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
    
    @Override
    public String toString() {
        return this.name;
    }

    public Array<Track> getTracks() {
        return tracks;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
        Track track = this.getCurrentTrack();
        if(track != null){
            track.setVolume(volume);
        }
    }

}
