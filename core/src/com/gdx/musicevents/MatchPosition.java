package com.gdx.musicevents;

import com.badlogic.gdx.Gdx;

public class MatchPosition implements Effect {

    boolean done = false;

    MusicEvent newEvent;
    MusicEvent oldEvent;


    public MatchPosition(){}

    @Override
    public void start(MusicEvent newEvent, MusicEvent oldEvent){
        this.newEvent = newEvent;
        this.oldEvent = oldEvent;


        newEvent.getMusic().play();
        if (oldEvent != null) {
            float position = oldEvent.getPosition();
            Gdx.app.log("MatchPosition", "Set position:" + position);
            newEvent.setPosition(position);
        }

        Gdx.app.log("MatchPosition", "New position:" + newEvent.getPosition());

    }

    @Override
    public void stop() {

    }

    @Override
    public void update(float dt) {
        done = true;
    }

    @Override
    public boolean isDone() {
        return done;
    }
}
