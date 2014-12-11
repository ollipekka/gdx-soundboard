package com.gdx.musicevents.effects;

import com.badlogic.gdx.Gdx;

public class FadeOut extends VolumeEffect {

    public FadeOut(){
        super(0, 0);
    }

    public FadeOut(float offset, float totalTime) {
        super(offset, totalTime);
    }


    @Override
    public void stop() {
        Gdx.app.log("FadeOut", "Stop");
        newEvent.getMusic().stop();
        newEvent.getMusic().setVolume(originalVolume);
    }


    @Override
    protected void volumeFunc(float originalVolume, float totalTime, float elapsedTime) {
        newEvent.getMusic().setVolume(originalVolume * (totalTime - elapsedTime) / totalTime);
    }
}
