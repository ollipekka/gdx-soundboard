package com.gdx.musicevents.tool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gdx.musicevents.MusicEventManager;

public class MenuDialog extends Dialog {
    
    public MenuDialog(Skin skin, final MusicEventManager manager){
        super("Menu", skin);
        
        
        Table content = getContentTable();
        content.defaults().expandX().fillX().minWidth(175);
        /* Save button. */
        Button save = new TextButton("Save", skin);
        save.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                manager.save("music/music-events.json");
            }
        });
        content.add(save).row();
        
        /* Load button. */
        Button load = new TextButton("Load", skin);
        load.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                manager.load("music/music-events.json");
            }
        });
        content.add(load).row();
        
        /* Clear button. */
        Button clear = new TextButton("Clear", skin);
        clear.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.clear();
            }
        });
        
        content.add(clear).row();
        
        /* Exit button. */
        Button exit = new TextButton("Exit", skin);
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        content.add(exit).row();
        
        /* Cancel button. */
        Button cancel = new TextButton("Cancel", skin);
        cancel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();
            }
        });
        content.add(cancel).row();
    }

}
