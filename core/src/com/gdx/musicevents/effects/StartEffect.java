package com.gdx.musicevents.effects;

import com.gdx.musicevents.MusicState;

/**
 * Effect that's played on the state that's being transitioned to. The effect starts the song.
 */
public interface StartEffect extends Effect {
    public void startStart(MusicState nextState, MusicState previousState);
    public void stopStart();
}
