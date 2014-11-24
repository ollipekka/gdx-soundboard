package com.gdx.musicevents;

public class Play implements Effect {
    @Override
    public void start(MusicEvent newEvent, MusicEvent oldEvent) {
        newEvent.getMusic().play();

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
