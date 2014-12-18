package com.gdx.musicevents.effects;

import com.badlogic.gdx.Gdx;
import com.gdx.musicevents.MusicEvent;
import com.gdx.musicevents.State;

public class Play extends AbstractEffect {

    public Play(){}
    @Override
    public void start(State nextState, State previousState) {
        String message = "Start new: " + nextState.getName();

        if(previousState != null){
            message += " old " + previousState.getName();
        }
        Gdx.app.log("Play", message);
        
        super.start(nextState, previousState);

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
