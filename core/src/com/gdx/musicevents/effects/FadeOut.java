package com.gdx.musicevents.effects;

import com.badlogic.gdx.Gdx;
import com.gdx.musicevents.MusicState;

public class FadeOut implements StopEffect {
    protected transient float originalVolume;
    protected final float totalTime;
    protected transient float elapsedTime = 0;
    protected final float offset;
    protected transient float elapsedOffset;
    protected transient boolean started = false;

    protected transient MusicState nextState;
    protected transient MusicState previousState;

    public FadeOut() {
        this(0, 0);

    }
    public FadeOut(float offset, float totalTime) {
        this.offset = offset;
        this.totalTime = totalTime;
    }

    @Override
    public void startStop(MusicState nextState, MusicState previousState) {
        Gdx.app.log("FadeOut", "Start " + nextState);
        
        this.nextState = nextState;
        this.previousState = previousState;
        
        this.originalVolume = nextState.getVolume();
        this.elapsedTime = 0;
        this.elapsedOffset = 0;

        started = true;
    }
    
    public void update(float dt) {

        if(elapsedOffset < offset) {
            elapsedOffset += dt;
        } else {

            elapsedTime += dt;

            if(isDone()){
                stopStop();
            } else {
                nextState.setVolume(originalVolume * (totalTime - elapsedTime) / totalTime);
                Gdx.app.log("FadeOut", "Volume: " + nextState.getVolume());
            }
        }
    }
    
    @Override
    public void stopStop() {
        Gdx.app.log("FadeOut", "Stop " + previousState.toString());
        nextState.setVolume(originalVolume);
        nextState.stop();
    }
    
    public boolean isDone(){
        return elapsedTime >= totalTime;
    }


}
