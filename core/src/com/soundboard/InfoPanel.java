package com.soundboard;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.text.DecimalFormat;

public class InfoPanel extends Table {

    DecimalFormat twoDForm = new DecimalFormat("###.##");
    private final Label nowPlaying;
    public InfoPanel(Skin skin) {
        super(skin);

        nowPlaying = new Label("Now Playing", skin);

        this.add(nowPlaying).center();
    }


    public void show(Event event){
        if(event == null){
            nowPlaying.setText("Paused");
        } else {

            String twoDecimalForm = twoDForm.format(event.getMusic().getPosition());

            nowPlaying.setText("Now playing: " + event.toString() + " " + twoDecimalForm);
        }
    }

}
