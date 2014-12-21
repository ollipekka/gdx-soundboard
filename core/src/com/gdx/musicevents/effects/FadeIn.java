package com.gdx.musicevents.effects;

import com.badlogic.gdx.Gdx;
import com.gdx.musicevents.State;


public class FadeIn extends VolumeEffect {

    public FadeIn() {
        super(0, 0);

    }
    public FadeIn(float offset, float totalTime) {
        super(offset, totalTime);
    }

    @Override
    public void start(State nextState, State previousState) {
        Gdx.app.log("FadeIn", "Start");
        super.start(nextState, previousState);
    }

    @Override
    protected void volumeFunc(float originalVolume, float totalTime, float elapsedTime) {
        nextState.setVolume(originalVolume * (elapsedTime) / totalTime);
    }
    @Override
    public void stop() {
        Gdx.app.log("FadeIn", "Stop");
    }
}
