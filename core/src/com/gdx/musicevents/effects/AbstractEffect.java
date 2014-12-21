package com.gdx.musicevents.effects;

import com.badlogic.gdx.Gdx;
import com.gdx.musicevents.State;

public abstract class AbstractEffect implements Effect {

    private boolean matchPosition = false;
    
    @Override
    public void start(State nextState, State previousState) {
        
        
        nextState.playTrack();
        /*
        if(matchPosition){
            if (previousState != null) {
                float position = previousState.getPosition();
                Gdx.app.log("MatchPosition", "Set position:" + position);
                previousState.setPosition(position);
            }
        }

        Gdx.app.log("MatchPosition", "New position:" + newEvent.getPosition());*/
    }

    public boolean isMatchPosition() {
        return matchPosition;
    }

    public void setMatchPosition(boolean matchPosition) {
        this.matchPosition = matchPosition;
    }

}
