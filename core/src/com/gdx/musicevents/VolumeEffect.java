package com.gdx.musicevents;

public abstract class VolumeEffect implements Effect {


    protected MusicEvent event;
    protected float originalVolume;
    protected final float totalTime;
    protected float elapsedTime = 0;
    boolean started = false;

    public VolumeEffect(float totalTime) {
        this.totalTime = totalTime;
    }

    @Override
    public void start(MusicEvent newEvent, MusicEvent oldEvent) {
        this.event = newEvent;
        this.originalVolume = event.getMusic().getVolume();
        this.elapsedTime = 0;

        started = true;
    }

    public void update(float dt) {
        volumeFunc(originalVolume, totalTime, elapsedTime);
        elapsedTime += dt;
    }

    public boolean isDone(){
        return elapsedTime >= totalTime;
    }

    protected abstract void volumeFunc(float originalVolume, float totalTime, float elapsedTime);


}
