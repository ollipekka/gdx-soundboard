package com.gdx.musicevents;

import com.badlogic.gdx.math.MathUtils;

public class FadeIn extends VolumeEffect {
    public FadeIn(float totalTime) {
        super(totalTime);
    }

    @Override
    protected void volumeFunc(float originalVolume, float totalTime, float elapsedTime) {
        if(MathUtils.isZero(elapsedTime)){
            event.getMusic().play();
        }
        event.getMusic().setVolume(originalVolume * (elapsedTime) / totalTime);
    }
}
