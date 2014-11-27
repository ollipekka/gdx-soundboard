package com.gdx.musicevents;

import com.badlogic.gdx.Gdx;

public class FadeIn extends VolumeEffect {

    public FadeIn(float offset, float totalTime) {
        super(offset, totalTime);
    }

    @Override
    public void start() {
        Gdx.app.log("FadeIn", "Start");
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
