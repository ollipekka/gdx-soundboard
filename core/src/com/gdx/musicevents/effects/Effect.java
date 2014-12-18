package com.gdx.musicevents.effects;

import com.gdx.musicevents.State;

public interface Effect {
    public void start(State nextState, State previousState);
    public void stop();
    public void update(float dt);

    public boolean isDone();
}
