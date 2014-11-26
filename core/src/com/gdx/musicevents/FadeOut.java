package com.gdx.musicevents;

import com.badlogic.gdx.Gdx;

public class FadeOut extends VolumeEffect {

    final float offset;

    public FadeOut(float offset, float totalTime) {
        super(totalTime);
        this.offset = offset;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
        Gdx.app.log("FadeOut", "Stop");
        event.getMusic().stop();
        event.getMusic().setVolume(originalVolume);
    }


    @Override
    protected void volumeFunc(float originalVolume, float totalTime, float elapsedTime) {
        event.getMusic().setVolume(originalVolume * (totalTime - elapsedTime) / totalTime);
    }
}
