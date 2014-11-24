package com.gdx.musicevents;

public class FadeIn extends VolumeEffect {
    public FadeIn(float totalTime) {
        super(totalTime);
    }

    @Override
    public void start() {
        event.getMusic().play();

    }

    @Override
    public void stop() {

    }

    @Override
    protected void volumeFunc(float originalVolume, float totalTime, float elapsedTime) {
        event.getMusic().setVolume(originalVolume * (elapsedTime) / totalTime);
    }
}
