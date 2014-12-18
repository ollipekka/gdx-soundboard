package com.gdx.musicevents.tool;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.gdx.musicevents.MusicEventManager;
import com.gdx.musicevents.State;

public class AddStateDialog extends Dialog {
    MusicEventManager eventManager;
    final Skin skin;
    final Stage stage;

    final TextField eventName;


    public AddStateDialog(final Stage stage, final Skin skin, final MusicEventManager eventManager) {

        super("Add Event", skin);
        this.stage = stage;
        this.skin = skin;
        this.eventManager = eventManager;


        Table content = this.getContentTable();

        eventName = Scene2dUtils.addTextField("Event name", content, skin);

        this.button("Ok", true);
        this.button("Cancel", false);
    }

    @Override
    protected void result(Object object) {
        boolean result = (Boolean)object;

        if(result) {
            eventManager.add(new State(eventName.getText()));
        }
    }
}
