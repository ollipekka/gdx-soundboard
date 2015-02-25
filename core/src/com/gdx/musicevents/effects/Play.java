package com.gdx.musicevents.effects;

import com.gdx.musicevents.MusicState;

public class Play implements StartEffect {

    
    private MusicState nextState;
    
    public Play() {
    }

    @Override
    public void beginStart() {
        nextState.play();
        nextState.setVolume(1);
    }

    @Override
    public void endStart() {
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public void initialize(MusicState nextState, MusicState previousState) {
        this.nextState = nextState;
    }
}
