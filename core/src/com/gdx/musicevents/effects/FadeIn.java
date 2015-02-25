package com.gdx.musicevents.effects;

import com.badlogic.gdx.Gdx;
import com.gdx.musicevents.MusicState;

/**
 * Fade in effect gradually increases the volume of the state / track to audible
 * level.
 */
public class FadeIn implements StartEffect {
    protected transient float originalVolume;
    protected final float totalTime;
    protected transient float elapsedTime = 0;
    protected final float offset;
    protected transient float elapsedOffset;
    protected transient boolean started = false;

    protected transient MusicState nextState;
    protected transient MusicState previousState;

    public FadeIn() {
        this(0, 0);

    }

    public FadeIn(float offset, float totalTime) {
        this.offset = offset;
        this.totalTime = totalTime;
    }

    @Override
    public void initialize(MusicState nextState, MusicState previousState) {
        this.nextState = nextState;
        this.previousState = previousState;
    }
    
    @Override
    public void beginStart() {
        this.originalVolume = nextState.getVolume();
        this.elapsedTime = 0;
        this.elapsedOffset = 0;
        nextState.setVolume(0);
        nextState.play();

        started = true;

    }

    @Override
    public void update(float dt) {

        if (elapsedOffset < offset) {
            elapsedOffset += dt;
        } else {
            elapsedTime += dt;

            if (isDone()) {
                endStart();
            } else {
                nextState.setVolume(originalVolume * (elapsedTime) / totalTime);
            }
        }
    }

    @Override
    public void endStart() {
        Gdx.app.debug("FadeIn", "Stop");
    }

    @Override
    public boolean isDone() {
        return elapsedTime >= totalTime;
    }

    @Override
    public String toString() {
        return "Fade in " + nextState;
    }
}
