package com.gdx.musicevents.effects;

import com.gdx.musicevents.MusicState;

/**
 * Effect that's played when track stops.
 * 
 */
public interface StopEffect extends Effect {

    public void startStop(MusicState nextState, MusicState previousState);
    public void stopStop();
}
