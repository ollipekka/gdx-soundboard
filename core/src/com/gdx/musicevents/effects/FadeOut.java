package com.gdx.musicevents.effects;

import com.badlogic.gdx.Gdx;
import com.gdx.musicevents.MusicState;

/**
 * FadeOut effect gradually decreases the volume of the song to zero.
 */
public class FadeOut implements StopEffect {
    protected transient float originalVolume;
    protected final float totalTime;
    protected transient float elapsedTime = 0;
    protected final float offset;
    protected transient float elapsedOffset;
    protected transient boolean started = false;

    protected transient MusicState nextState;
    protected transient MusicState previousState;

    public FadeOut() {
        this(0, 0);
    }

    public FadeOut(float offset, float totalTime) {
        this.offset = offset;
        this.totalTime = totalTime;
    }

    @Override
    public void initialize(MusicState nextState, MusicState previousState) {
        this.nextState = nextState;
        this.previousState = previousState;
    }
    @Override
    public void beginStop() {
        this.originalVolume = nextState.getVolume();
        this.elapsedTime = 0;
        this.elapsedOffset = 0;

        started = true;
    }

    @Override
    public void update(float dt) {
        if (elapsedOffset < offset) {
            elapsedOffset += dt;
        } else {
            elapsedTime += dt;
            if (isDone()) {
                endStop();
            } else {
                nextState.setVolume(
                        originalVolume * (totalTime - elapsedTime) / totalTime);
                Gdx.app.debug("FadeOut", "Volume: " + nextState.getVolume());
            }
        }
    }

    @Override
    public void endStop() {
        Gdx.app.debug("FadeOut", "Stop " + previousState.toString());
        nextState.setVolume(originalVolume);
        nextState.stop();
    }

    @Override
    public boolean isDone() {
        return elapsedTime >= totalTime;
    }

    
    @Override
    public String toString() {
        return "Fading out " + nextState;
    }
}
