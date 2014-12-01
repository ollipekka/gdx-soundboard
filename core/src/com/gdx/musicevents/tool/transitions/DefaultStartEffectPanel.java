package com.gdx.musicevents.tool.transitions;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class DefaultStartEffectPanel extends Table {
    
    final CheckBox matchPosition;
    
    public DefaultStartEffectPanel(Skin skin, boolean matchPositionEnabled) {
        super(skin);

        this.top().left();
        
        if(matchPositionEnabled){
            matchPosition = new CheckBox("Match position", skin);
            this.add(matchPosition).row();
        } else {
            matchPosition = null;
        }
    }
}
