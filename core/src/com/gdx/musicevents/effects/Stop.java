package com.gdx.musicevents.effects;

import com.badlogic.gdx.Gdx;
import com.gdx.musicevents.MusicEvent;

public class Stop implements Effect {

    public Stop(){}

    @Override
    public void start(MusicEvent newEvent, MusicEvent oldEvent) {

        String message = "Stop new: " + newEvent.getName();

        if(oldEvent != null){
            message += " old " + oldEvent.getName();
        }
        Gdx.app.log("Stop", message);
        newEvent.getMusic().stop();
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
