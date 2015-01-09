package com.gdx.musicevents.effects;

import com.gdx.musicevents.MusicState;

/**
 * The effect that is applied on the state where transitioned from. The effect
 * ends the song.
 * 
 */
public interface StopEffect extends Effect {

    public void beginStop(MusicState nextState, MusicState previousState);

    public void endStop();
}
