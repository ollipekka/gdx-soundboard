package com.gdx.musicevents;

import com.badlogic.gdx.Gdx;

public abstract class AbstractEffect implements Effect {

    private boolean matchPosition = false;
    
    @Override
    public void start(MusicEvent newEvent, MusicEvent oldEvent) {
        newEvent.getMusic().play();
        if(matchPosition){
            if (oldEvent != null) {
                float position = oldEvent.getPosition();
                Gdx.app.log("MatchPosition", "Set position:" + position);
                newEvent.setPosition(position);
            }
        }

        Gdx.app.log("MatchPosition", "New position:" + newEvent.getPosition());
    }

    public boolean isMatchPosition() {
        return matchPosition;
    }

    public void setMatchPosition(boolean matchPosition) {
        this.matchPosition = matchPosition;
    }

}
