package com.gdx.musicevents.tool;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gdx.musicevents.MusicEvent;

public class InfoPanel extends Table {

    private final Label nowPlaying;
    public InfoPanel(Skin skin) {
        super(skin);

        nowPlaying = new Label("Now Playing", skin);

        this.add(nowPlaying).center();
    }


    public void show(MusicEvent musicEvent){
        if(musicEvent == null){
            nowPlaying.setText("Paused");
        } else {
            int intPosition = (int)(musicEvent.getPosition() * 100);
            String twoDecimalForm = Float.toString(intPosition / 100.0f);

            nowPlaying.setText("Now playing: " + musicEvent.toString() + " " + twoDecimalForm);
        }
    }

}
