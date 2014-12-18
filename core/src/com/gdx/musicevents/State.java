package com.gdx.musicevents;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.gdx.musicevents.effects.Effect;
import com.gdx.musicevents.effects.Stop;

public class State implements OnCompletionListener{

    private final String name;
    
    private final Array<Track> tracks = new Array<Track>();

    private final ObjectMap<String, Effect> enterTransitions = new ObjectMap<String, Effect>();
    private final ObjectMap<String, Effect> exitTransitions = new ObjectMap<String, Effect>();
    
    private boolean looping = false;
    
    private transient int currentTrackIndex;
    
    public State(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public void addTrack(Track track){
        this.tracks.add(track);
    }
    
    public void removeTrack(Track track){
        this.tracks.removeValue(track, true);
    }

    @Override
    public void onCompletion(Music music) {
        if(looping){
            Track currentTrack = null;
            if(tracks.size > 1) {
                currentTrackIndex = MathUtils.random(tracks.size - 1);
                currentTrack = getCurrentTrack();
            } else {
                currentTrack = getCurrentTrack();
                currentTrack.reset();
            }
            currentTrack.play();
        } else {
            currentTrackIndex += 1;
            Track currentTrack = getCurrentTrack();
            currentTrack.play();
        }
    }
    
    public void enter(State previousState) {

        Effect effect = enterTransitions.get(previousState.getName());

        if(effect == null){
            effect = new Stop();
        }

        effect.start(this, previousState);
        
    }
    
    public void exit(State nextState) {
        Effect effect = exitTransitions.get(nextState.getName());

        if(effect == null){
            effect = new Stop();
        }

        effect.start(this, nextState);
    }
    
    public void update(float dt) {
        Track currentTrack = getCurrentTrack();
        currentTrack.update(dt);
    }
    
    public void play() {
        Track currentTrack = getCurrentTrack();
        currentTrack.play();
    }
    
    public void stop() {
        Track currentTrack = getCurrentTrack();
        currentTrack.stop();
    }

    private Track getCurrentTrack(){
        return tracks.get(currentTrackIndex);
    }
}
