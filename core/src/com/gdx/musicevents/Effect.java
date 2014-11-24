package com.gdx.musicevents;

public interface Effect {
    public void start(MusicEvent newEvent, MusicEvent oldEvent);
    public void update(float dt);

    public boolean isDone();
}
