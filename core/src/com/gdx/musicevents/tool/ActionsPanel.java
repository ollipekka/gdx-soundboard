package com.gdx.musicevents.tool;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gdx.musicevents.MusicEventManager;

public class ActionsPanel extends Table {
    public ActionsPanel(Skin skin, final MusicEventManager manager) {
        super(skin);

        Button save = new TextButton("Save", skin);
        save.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                manager.save("music/music-events.json");
            }
        });


        Button load = new TextButton("Load", skin);

        load.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                manager.load("music/music-events.json");
            }
        });

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
