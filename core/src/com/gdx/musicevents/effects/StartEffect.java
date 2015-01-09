package com.gdx.musicevents.effects;

import com.gdx.musicevents.MusicState;

/**
 * Effect that is played on the state that's being transitioned to. The effect starts the song.
 */
public interface StartEffect extends Effect {
    public void beginStart(MusicState nextState, MusicState previousState);
    public void endStart();
}
