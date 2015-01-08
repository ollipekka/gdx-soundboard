package com.gdx.musicevents.effects;

import com.badlogic.gdx.Gdx;
import com.gdx.musicevents.MusicState;

public class Stop implements StopEffect {

    public Stop(){}

    @Override
    public void startStop(MusicState nextState, MusicState previousState) {
        Gdx.app.log("Stop", "Stopped: " + nextState);
        
        nextState.stop();
    }

    @Override
    public void stopStop() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public boolean isDone() {
        return true;
    }
}
