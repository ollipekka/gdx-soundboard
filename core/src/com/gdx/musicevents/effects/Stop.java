package com.gdx.musicevents.effects;

import com.gdx.musicevents.MusicState;

public class Stop implements StopEffect {
    private MusicState nextState;

    public Stop() {
    }

    @Override
    public void initialize(MusicState nextState, MusicState previousState) {
        this.nextState = nextState;
    }
    
    @Override
    public void beginStop() {
        nextState.stop();
    }

    @Override
    public void endStop() {

    }

    @Override
    public void update(float dt) {
    }

    @Override
    public boolean isDone() {
        return true;
    }
}
