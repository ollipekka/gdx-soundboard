package com.gdx.musicevents.effects;

import com.gdx.musicevents.MusicEvent;

public interface Effect {
    public void start(MusicEvent newEvent, MusicEvent oldEvent);
    public void stop();
    public void update(float dt);

    public boolean isDone();
}
