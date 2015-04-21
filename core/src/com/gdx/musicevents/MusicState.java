package com.gdx.musicevents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.gdx.musicevents.effects.Effect;
import com.gdx.musicevents.effects.Play;
import com.gdx.musicevents.effects.StartEffect;
import com.gdx.musicevents.effects.Stop;
import com.gdx.musicevents.effects.StopEffect;

public class MusicState implements OnCompletionListener{

    private final String name;
    
    private final Array<Track> tracks = new Array<Track>();

    private final ObjectMap<String, StartEffect> enterTransitions = new ObjectMap<String, StartEffect>();
    private final ObjectMap<String, StopEffect> exitTransitions = new ObjectMap<String, StopEffect>();
    
    
    private transient int oldTrackIndex = -1;
    private transient int currentTrackIndex = -1;
    
    private transient float volume = 1;
    
    private boolean resumeTrack = false;
    
    private boolean randomTrack = false;

    private boolean loopTrack = false;
    
    
    public MusicState(){
        this("");
    }
    
    public MusicState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public void addTrack(Track track){
        this.getTracks().add(track);
    }
    
    public void removeTrack(Track track){
        this.getTracks().removeValue(track, true);
    }

    @Override
    public void onCompletion(Music music) {
        if (!loopTrack) {
            if (randomTrack) {
                currentTrackIndex = MathUtils.random(0, tracks.size - 1);
            } else {
                currentTrackIndex = (currentTrackIndex + 1) % tracks.size;
            }
        }
        Track currentTrack = getCurrentTrack();
        if(currentTrack != null){
            currentTrack.reset();
            currentTrack.play();
        }
    }
    
    public void init(String basePath) {
        for(int i = 0; i < getTracks().size; i++){
            Track track = getTracks().get(i);
            track.init(basePath, this);
        }
    }
    
    public StartEffect enter(MusicState previousState) {
        
        StartEffect effect = null;
        if(previousState != null){
            effect = enterTransitions.get(previousState.getName());
        }

        if(effect == null){
            effect = new Play();
        }

        effect.initialize(this, previousState);
        return effect;
    }
    
    public StopEffect exit(MusicState nextState) {
        StopEffect effect = exitTransitions.get(nextState.getName());

        if(effect == null){
            effect = new Stop();
        }

        effect.initialize(this, nextState);
        return effect;
    }
    
    public void update(float dt) {
        Track currentTrack = getCurrentTrack();
        if(currentTrack != null){
            currentTrack.update(dt);
        }
    }
    
    public void play() {
        
        if(!resumeTrack){
            currentTrackIndex = 0;
            playTrack();
        } else {
            resumeTrack();
        }
    }
    
    private void playTrack() {
        Track currentTrack = getCurrentTrack();
        if(currentTrack != null) {
            currentTrack.setVolume(this.volume);
            currentTrack.play();
        }
    }
    
    private void resumeTrack() {
        
        if(oldTrackIndex == -1){
            currentTrackIndex = 0;
        } else {
            currentTrackIndex = oldTrackIndex;
        }
        
        Track currentTrack = getCurrentTrack();
        if(currentTrack != null) {
            currentTrack.play();
            currentTrack.setPosition(currentTrack.getPosition());
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
        oldTrackIndex = currentTrackIndex;
        currentTrackIndex = -1;
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

    public ObjectMap<String, StartEffect> getEnterTransitions() {
        return enterTransitions;
    }

    public ObjectMap<String, StopEffect> getExitTransitions() {
        return exitTransitions;
    }

    public void addEnterTransition(String eventName, StartEffect effect) {
        this.enterTransitions.put(eventName, effect);
    }

    public void removeEnterTransition(String name) {
        this.enterTransitions.remove(name);

    }
    public void addExitTransition(String eventName, StopEffect effect) {
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

    public boolean isResumeTrack() {
        return resumeTrack;
    }

    public void setResumeTrack(boolean resumeTrack) {
        this.resumeTrack = resumeTrack;
    }

    public boolean isRandomTrack() {
        return randomTrack;
    }

    public void setRandomTrack(boolean randomTrack) {
        this.randomTrack = randomTrack;
    }

    public boolean isLoopTrack() {
        return loopTrack;
    }

    public void setLoopTrack(boolean loopTrack) {
        this.loopTrack = loopTrack;
    }
}
