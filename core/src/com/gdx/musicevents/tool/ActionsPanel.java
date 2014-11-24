package com.gdx.musicevents.tool;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gdx.musicevents.MusicEventManager;

public class ActionsPanel extends Table {
    public ActionsPanel(Skin skin, final MusicEventManager manager) {
        super(skin);

        Button save = new TextButton("Save", skin);


        Button load = new TextButton("Load", skin);


        Button clear = new TextButton("Clear", skin);
        clear.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.clear();
            }
        });


        Button stop = new TextButton("Stop", skin);
        stop.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.stop();
            }
        });

        this.defaults().expandX().fillX();
        this.add(save);
        this.add(load);
        this.add(clear);
        this.add(stop);
    }
}
