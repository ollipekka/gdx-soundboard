package com.gdx.musicevents.effects;


public class FadeIn extends VolumeEffect {

    public FadeIn() {
        super(0, 0);

    }
    public FadeIn(float offset, float totalTime) {
        super(offset, totalTime);
    }


    @Override
    protected void volumeFunc(float originalVolume, float totalTime, float elapsedTime) {
        newEvent.getCurrentTrack().setVolume(originalVolume * (elapsedTime) / totalTime);
    }
    @Override
    public void stop() {
    }
}
