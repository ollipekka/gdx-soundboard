package com.gdx.musicevents.effects;

import com.badlogic.gdx.Gdx;
import com.gdx.musicevents.State;

public class FadeOut extends VolumeEffect {

    public FadeOut(){
        super(0, 0);
    }

    public FadeOut(float offset, float totalTime) {
        super(offset, totalTime);
    }

    @Override
    public void start(State nextState, State previousState) {
        Gdx.app.log("FadeOut", "Start");
        super.start(nextState, previousState);
    }
    
    @Override
    public void stop() {
        Gdx.app.log("FadeOut", "Stop");
        nextState.stop();
        nextState.setVolume(originalVolume);
    }


    @Override
    protected void volumeFunc(float originalVolume, float totalTime, float elapsedTime) {
        
        nextState.setVolume(originalVolume * (totalTime - elapsedTime) / totalTime);
        Gdx.app.log("FadeOut", "Volume: " + nextState.getVolume());
    }
}
