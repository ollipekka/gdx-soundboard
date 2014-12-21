package com.gdx.musicevents.effects;

import com.gdx.musicevents.State;

public abstract class VolumeEffect extends AbstractEffect {

    protected float originalVolume;
    protected final float totalTime;
    protected float elapsedTime = 0;
    protected float offset = 0;
    boolean started = false;

    protected transient State nextState;
    protected transient State previousState;

    public VolumeEffect(float offset, float totalTime) {
        this.offset = offset;
        this.totalTime = totalTime;
    }

    @Override
    public void start(State nextState, State previousState) {
        
        this.nextState = nextState;
        this.previousState = previousState;
        
        this.originalVolume = nextState.getVolume();
        this.elapsedTime = 0;
        nextState.playTrack();

        started = true;
    }



    public void update(float dt) {

        if(offset > 0) {
            offset -= dt;
        } else {

            elapsedTime += dt;

            if(isDone()){
                stop();
            } else {
                volumeFunc(originalVolume, totalTime, elapsedTime);
            }
        }
    }

    public boolean isDone(){
        return elapsedTime >= totalTime;
    }

    protected abstract void volumeFunc(float originalVolume, float totalTime, float elapsedTime);


}
