package com.gdx.musicevents.tool;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gdx.musicevents.State;
import com.gdx.musicevents.Track;

public class StateInfoPanel extends Table {

    private final Label nowPlaying;
    public StateInfoPanel(Skin skin) {
        super(skin);

        nowPlaying = new Label("Now Playing", skin);

        this.add(nowPlaying).center();
    }


    public void show(State musicEvent){
        if(musicEvent == null){
            nowPlaying.setText("Paused");
        } else {
            Track track = musicEvent.getCurrentTrack();
            int intPosition = (int)(track.getPosition() * 100);
            String twoDecimalForm = Float.toString(intPosition / 100.0f);

            nowPlaying.setText("Now playing: " + musicEvent.toString() + " " + twoDecimalForm);
        }
    }

}
