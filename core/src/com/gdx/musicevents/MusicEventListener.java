package com.gdx.musicevents;

public interface MusicEventListener {
    public void stateAdded(MusicState state);
    public void stateRemoved(MusicState state);
    public void stateChanged(MusicState nextState, MusicState previewsState);
}
