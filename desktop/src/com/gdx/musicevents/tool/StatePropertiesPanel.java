package com.gdx.musicevents.tool;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gdx.musicevents.MusicState;

public class StatePropertiesPanel extends Table {

    private final CheckBox resumeTrack;
    
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
        
        this.add(resumeTrack).left();
    }

    public MusicState getMusicState() {
        return musicState;
    }

    public void setMusicState(MusicState musicState) {
        this.musicState = musicState;
        resumeTrack.setChecked(musicState.isResumeTrack());
    }
    
    
    

}
