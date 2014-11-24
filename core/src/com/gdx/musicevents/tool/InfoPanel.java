package com.gdx.musicevents.tool;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gdx.musicevents.MusicEvent;

import java.text.DecimalFormat;

public class InfoPanel extends Table {

    DecimalFormat twoDForm = new DecimalFormat("###.##");
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

            String twoDecimalForm = twoDForm.format(musicEvent.getMusic().getPosition());

            nowPlaying.setText("Now playing: " + musicEvent.toString() + " " + twoDecimalForm);
        }
    }

}
