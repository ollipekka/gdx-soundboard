package com.gdx.musicevents;

public class Stop implements Effect {
    @Override
    public void start(MusicEvent newEvent, MusicEvent oldEvent) {
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
