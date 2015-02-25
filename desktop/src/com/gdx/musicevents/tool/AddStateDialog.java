package com.gdx.musicevents.tool;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.gdx.musicevents.MusicEventManager;
import com.gdx.musicevents.MusicState;

public class AddStateDialog extends Dialog {
    MusicEventManager eventManager;
    final Skin skin;
    final Stage stage;

    final TextField eventName;


    public AddStateDialog(final Stage stage, final Skin skin, final MusicEventManager eventManager) {

        super("Add State", skin);
        this.stage = stage;
        this.skin = skin;
        this.eventManager = eventManager;


        Table content = this.getContentTable();
        Label label = new Label("State name", skin);
        label.setAlignment(Align.left);
        content.add(label).left().fillX().expandX().row();

        eventName = new TextField("", skin);
        content.add(eventName).right().fillX().expandX().row();

        Table buttons = this.getButtonTable();
        buttons.defaults().fillX().expandX();
        
        this.button("Ok", true);
        this.button("Cancel", false);
        

        key(Keys.ENTER, true);
        key(Keys.ESCAPE, false);
    }

    @Override
    protected void result(Object object) {
        boolean result = (Boolean)object;

        if(result) {
            eventManager.add(new MusicState(eventName.getText()));
        }
    }
}
