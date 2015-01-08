package com.gdx.musicevents.effects;

import com.gdx.musicevents.MusicState;

public interface StartEffect extends Effect {

    public void startStart(MusicState nextState, MusicState previousState);
    public void stopStart();
}
