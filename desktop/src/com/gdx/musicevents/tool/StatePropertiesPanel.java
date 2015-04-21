package com.gdx.musicevents.tool;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gdx.musicevents.MusicState;
import com.gdx.musicevents.Track;

public class StatePropertiesPanel extends Table {

    private final CheckBox resumeTrack;
    private final CheckBox randomTrack;
    private final CheckBox loopTrack;

    private MusicState musicState;
    
    public StatePropertiesPanel(Skin skin) {
        setBackground(skin.getDrawable("panel-background"));
        
        this.top().left();
        this.defaults().top().left().pad(2);
        
        final Label propertiesLabel = new Label("Properties", skin, "title");
        this.add(propertiesLabel).fillX().expandX().row();
        
        resumeTrack = new CheckBox("Resume track", skin);
        resumeTrack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                musicState.setResumeTrack(resumeTrack.isChecked());
            }
        });
        
        this.add(resumeTrack).left().row();
        
        randomTrack = new CheckBox("Randomize track", skin);
        randomTrack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                musicState.setRandomTrack(randomTrack.isChecked());
            }
        });
        
        this.add(randomTrack).left().row();

        loopTrack = new CheckBox("Loop track", skin);
        loopTrack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                musicState.setLoopTrack(loopTrack.isChecked());
            }
        });

        this.add(loopTrack).left().row();
    }

    public MusicState getMusicState() {
        return musicState;
    }



    public void setMusicState(MusicState musicState) {
        this.musicState = musicState;
        resumeTrack.setChecked(musicState.isResumeTrack());
        randomTrack.setChecked(musicState.isRandomTrack());
        loopTrack.setChecked(musicState.isLoopTrack());
    }

}
