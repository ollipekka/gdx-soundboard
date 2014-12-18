package com.gdx.musicevents.effects;

import com.badlogic.gdx.Gdx;
import com.gdx.musicevents.MusicEvent;
import com.gdx.musicevents.State;

public class Stop implements Effect {

    public Stop(){}

    @Override
    public void start(State nextState, State previousState) {

        String message = "Stop new: " + nextState.getName();

        if(previousState != null){
            message += " old " + previousState.getName();
        }
        Gdx.app.log("Stop", message);
        nextState.stop();
    }

    @Override
    public void stop() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public boolean isDone() {
        return true;
    }
}
