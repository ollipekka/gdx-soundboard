package com.gdx.musicevents.tool;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gdx.musicevents.MusicEventListener;
import com.gdx.musicevents.MusicEventManager;
import com.gdx.musicevents.MusicState;
import com.gdx.musicevents.Track;
import com.gdx.musicevents.tool.transitions.TransitionInPanel;
import com.gdx.musicevents.tool.transitions.TransitionOutPanel;

public class MusicStatePanel extends Table {

    MusicState displayedState;

    final TrackListPanel trackListPanel;
    final TransitionInPanel transitionInPanel;
    final TransitionOutPanel transitionOutPanel;

    private StatePropertiesPanel statePropertiesPanel;

    private final MusicEventManager manager;

    

    public MusicStatePanel(Skin skin, final MusicEventTool planner) {
        super(skin);
        manager = planner.getEventManager();

        
        manager.addListener(new MusicEventListener() {
            @Override
            public void stateAdded(MusicState event) {
                transitionInPanel.getAddButton().setDisabled(manager.getEvents().size <= 1);
                transitionOutPanel.getAddButton().setDisabled(manager.getEvents().size <= 1);
            }

            @Override
            public void stateRemoved(MusicState event) {

                transitionInPanel.getAddButton().setDisabled(manager.getEvents().size <= 1);
                transitionOutPanel.getAddButton().setDisabled(manager.getEvents().size <= 1);
                if(event == displayedState){
                    MusicStatePanel.this.setVisible(false);
                }
            }

            @Override
            public void stateChanged(MusicState nextState, MusicState previewsState) {

            }
        });

        this.pad(2);
        this.defaults().top().left();
        
        statePropertiesPanel = new StatePropertiesPanel(skin);
        this.add(statePropertiesPanel).fill().expand();

        /* Small vertical border. */
        this.add().pad(1).fillY().expandY();
        
        trackListPanel = new TrackListPanel(skin, planner.getStage(), this);
        this.add(trackListPanel).fill().expand().row();
        
        /* Small horizontal border. */
        this.add().pad(1).fillX().expandX().row();
        
        transitionInPanel = new TransitionInPanel(skin, planner.getStage(), planner.getEventManager(), this);
        this.add(transitionInPanel).fill().expand();

        /* Small vertical border. */
        this.add().pad(1).fillY().expandY();
        
        transitionOutPanel = new TransitionOutPanel(skin, planner.getStage(), planner.getEventManager(), this);
        this.add(transitionOutPanel).fill().expand();

        this.setVisible(false);

    }



    public void show(MusicState musicState) {
        this.setVisible(true);
        this.displayedState = musicState;

        this.statePropertiesPanel.setMusicState(displayedState);
        this.trackListPanel.setMusicState(displayedState);
        this.transitionInPanel.setMusicState(displayedState);
        this.transitionOutPanel.setMusicState(displayedState);
    }


    public MusicEventManager getManager() {
        return manager;
    }
}
