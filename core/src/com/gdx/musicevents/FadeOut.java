package com.gdx.musicevents;

public class FadeOut extends VolumeEffect {
    public FadeOut(float totalTime) {
        super(totalTime);
    }

    @Override
    protected void volumeFunc(float originalVolume, float totalTime, float elapsedTime) {
        event.getMusic().setVolume(originalVolume * (totalTime - elapsedTime) / totalTime);

        if(isDone()){
            event.getMusic().stop();
            event.getMusic().setVolume(originalVolume);
        }

    }
}
