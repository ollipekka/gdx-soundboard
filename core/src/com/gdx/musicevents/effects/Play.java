package com.gdx.musicevents.effects;

import com.badlogic.gdx.Gdx;
import com.gdx.musicevents.MusicState;

public class Play implements StartEffect {

    public Play() {
    }

    @Override
    public void startStart(MusicState nextState, MusicState previousState) {
        Gdx.app.debug("Play", nextState.toString());

        nextState.play();
        nextState.setVolume(1);
    }

    @Override
    public void stopStart() {
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public boolean isDone() {
        return true;
    }
}
