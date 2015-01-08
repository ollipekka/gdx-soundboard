package com.gdx.musicevents.effects;

import com.badlogic.gdx.Gdx;
import com.gdx.musicevents.MusicState;


public class FadeIn implements StartEffect {
    protected transient float originalVolume;
    protected final float totalTime;
    protected transient float elapsedTime = 0;
    protected final float offset;
    protected transient float elapsedOffset;
    protected transient boolean started = false;

    protected transient MusicState nextState;
    protected transient MusicState previousState;

    public FadeIn() {
        this(0, 0);

    }
    public FadeIn(float offset, float totalTime) {
        this.offset = offset;
        this.totalTime = totalTime;
    }

    @Override
    public void startStart(MusicState nextState, MusicState previousState) {
        Gdx.app.log("FadeIn", "Start " + nextState.toString());
        
        this.nextState = nextState;
        this.previousState = previousState;
        
        this.originalVolume = nextState.getVolume();
        this.elapsedTime = 0;
        this.elapsedOffset = 0;
        nextState.play();

        started = true;
    }
    
    public void update(float dt) {

        if(elapsedOffset < offset) {
            elapsedOffset += dt;
        } else {
            elapsedTime += dt;

            if(isDone()){
                stopStart();
            } else {
                nextState.setVolume(originalVolume * (elapsedTime) / totalTime);
                Gdx.app.log("FadeIn", "Volume: " + nextState.getVolume());
            }
        }
    }
    
    @Override
    public void stopStart() {
        Gdx.app.log("FadeIn", "Stop");
    }
    
    public boolean isDone(){
        return elapsedTime >= totalTime;
    }

}
