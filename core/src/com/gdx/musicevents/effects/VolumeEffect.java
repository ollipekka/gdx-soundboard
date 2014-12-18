package com.gdx.musicevents.effects;

import com.gdx.musicevents.State;

public abstract class VolumeEffect extends AbstractEffect {

    protected float originalVolume;
    protected final float totalTime;
    protected float elapsedTime = 0;
    protected float offset = 0;
    boolean started = false;

    protected transient State newEvent;
    protected transient State oldEvent;

    public VolumeEffect(float offset, float totalTime) {
        this.offset = offset;
        this.totalTime = totalTime;
    }

    @Override
    public void start(State nextState, State previousState) {
        
        this.newEvent = newEvent;
        this.oldEvent = oldEvent;
        
        this.originalVolume = newEvent.getCurrentTrack().getVolume();
        this.elapsedTime = 0;

        started = true;
    }



    public void update(float dt) {

        if(offset > 0) {
            offset -= dt;
        } else {

            /*
            if(MathUtils.isZero(elapsedTime)){
                super.start(newEvent, oldEvent);
            }*/
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
