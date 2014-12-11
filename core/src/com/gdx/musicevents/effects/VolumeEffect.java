package com.gdx.musicevents.effects;

import com.badlogic.gdx.math.MathUtils;
import com.gdx.musicevents.MusicEvent;

public abstract class VolumeEffect extends AbstractEffect {

    protected float originalVolume;
    protected final float totalTime;
    protected float elapsedTime = 0;
    protected float offset = 0;
    boolean started = false;

    protected transient MusicEvent newEvent;
    protected transient MusicEvent oldEvent;

    public VolumeEffect(float offset, float totalTime) {
        this.offset = offset;
        this.totalTime = totalTime;
    }

    @Override
    public void start(MusicEvent newEvent, MusicEvent oldEvent) {
        
        this.newEvent = newEvent;
        this.oldEvent = oldEvent;
        
        this.originalVolume = newEvent.getMusic().getVolume();
        this.elapsedTime = 0;

        started = true;
    }



    public void update(float dt) {

        if(offset > 0) {
            offset -= dt;
        } else {

            if(MathUtils.isZero(elapsedTime)){
                super.start(newEvent, oldEvent);
            }
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
