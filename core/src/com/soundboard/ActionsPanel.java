package com.soundboard;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class ActionsPanel extends Table {
    public ActionsPanel(Skin skin) {
        super(skin);

        Button save = new TextButton("Save", skin);
        Button load = new TextButton("Load", skin);
        Button clear = new TextButton("Clear", skin);


        this.defaults().expandX().fillX();
        this.add(save);
        this.add(load);
        this.add(clear);
    }
}
