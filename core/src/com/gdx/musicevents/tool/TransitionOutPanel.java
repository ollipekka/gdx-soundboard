package com.gdx.musicevents.tool;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.gdx.musicevents.Effect;

public class TransitionOutPanel extends Table {
    public TransitionOutPanel(Skin skin) {
        super(skin);

        this.top().left();
        this.add("Transition out").colspan(2).row();
        this.add(new ScrollPane(new List<Effect>(skin))).colspan(2).fill().expand().row();
        this.add(new TextButton("Add", skin)).fillX().expandX();
        this.add(new TextButton("Remove", skin)).fillX().expandX();
    }
}
