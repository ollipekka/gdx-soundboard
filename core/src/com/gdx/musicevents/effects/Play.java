package com.gdx.musicevents.effects;

import com.badlogic.gdx.Gdx;
import com.gdx.musicevents.MusicEvent;

public class Play extends AbstractEffect {

    public Play(){}
    @Override
    public void start(MusicEvent newEvent, MusicEvent oldEvent) {
        String message = "Start new: " + newEvent.getName();

        if(oldEvent != null){
            message += " old " + oldEvent.getName();
        }
        Gdx.app.log("Play", message);
        
        super.start(newEvent, oldEvent);

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
