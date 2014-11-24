package com.gdx.musicevents;

import com.badlogic.gdx.Gdx;

public class MatchPosition implements Effect {

    boolean done = false;

    MusicEvent newEvent;
    MusicEvent oldEvent;

    boolean started = false;

    public MatchPosition(){}

    @Override
    public void start(MusicEvent newEvent, MusicEvent oldEvent){
        this.newEvent = newEvent;
        this.oldEvent = oldEvent;

        started = true;
    }

    @Override
    public void stop() {

    }

    @Override
    public void update(float dt) {
        if(started) {
            float position = 0;

            if (oldEvent != null) {
                if (newEvent.isMatchPosition()) {
                    position = newEvent.getPosition();
                }
                oldEvent.getMusic().stop();
            }

            if (newEvent.isMatchPosition()) {
                Gdx.app.log("EventDetailsPanel", "Set position:" + position);
                newEvent.setPosition(position);
            }
            newEvent.getMusic().play();

            done = true;
        }
    }

    @Override
    public boolean isDone() {
        return done;
    }
}
