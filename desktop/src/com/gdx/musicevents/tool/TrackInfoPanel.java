package com.gdx.musicevents.tool;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gdx.musicevents.State;

public class TrackInfoPanel extends Table {

    private final Button play;
    private final Button stop;
    
    private final CheckBox looping;
    
    private State state;

    public TrackInfoPanel(Skin skin) {
        
        this.top().left();
        this.defaults().top().left();
        
        looping = new CheckBox("Looping", skin);
        this.add(looping).row();

        play = new TextButton("Play", skin);
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent evt, Actor actor) {
                state.play();
            }
        });
        this.add(play).fillX().expandX();
        
        stop = new TextButton("Stop", skin);
        stop.addListener(new ChangeListener(){
           @Override
            public void changed(ChangeEvent event, Actor actor) {
               state.stop();
            } 
        });
        
        this.add(stop).fillX().expandX();
    }

    public void show(State state) {
        this.state = state;
        this.looping.setChecked(state.isLooping());
    }

}
