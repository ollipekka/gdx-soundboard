package com.gdx.musicevents;

public class FadeOut extends VolumeEffect {
    public FadeOut(float totalTime) {
        super(totalTime);
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
        event.getMusic().stop();
        event.getMusic().setVolume(originalVolume);
    }


    @Override
    protected void volumeFunc(float originalVolume, float totalTime, float elapsedTime) {
        event.getMusic().setVolume(originalVolume * (totalTime - elapsedTime) / totalTime);
    }
}
