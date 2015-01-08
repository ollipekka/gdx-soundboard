package com.gdx.musicevents.tool;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gdx.musicevents.MusicEventListener;
import com.gdx.musicevents.MusicEventManager;
import com.gdx.musicevents.MusicState;
import com.gdx.musicevents.tool.transitions.TransitionInPanel;
import com.gdx.musicevents.tool.transitions.TransitionOutPanel;

public class EventDetailsPanel extends Table {

    MusicState displayedState;

    final TrackListPanel trackListPanel;
    final TransitionInPanel transitionInPanel;
    final TransitionOutPanel transitionOutPanel;


    public EventDetailsPanel(Skin skin, final MusicEventTool planner) {
        super(skin);
        final MusicEventManager manager = planner.getEventManager();

        
        manager.addListener(new MusicEventListener() {
            @Override
            public void eventAdded(MusicState event) {
                transitionInPanel.getAddButton().setDisabled(manager.getEvents().size <= 1);
                transitionOutPanel.getAddButton().setDisabled(manager.getEvents().size <= 1);
            }

            @Override
            public void eventRemoved(MusicState event) {

                transitionInPanel.getAddButton().setDisabled(manager.getEvents().size <= 1);
                transitionOutPanel.getAddButton().setDisabled(manager.getEvents().size <= 1);
                if(event == displayedState){
                    EventDetailsPanel.this.setVisible(false);
                }
            }
        });

        this.pad(2);
        this.defaults().top().left();
        
        trackListPanel = new TrackListPanel(skin, planner.getStage(), this);
        this.add(trackListPanel).fill().expand().colspan(2).row();
        this.add().pad(1).fillX().expandX().row();
        
        transitionInPanel = new TransitionInPanel(skin, planner.getStage(), planner.getEventManager(), this);
        this.add(transitionInPanel).fill().expand();

        transitionOutPanel = new TransitionOutPanel(skin, planner.getStage(), planner.getEventManager(), this);
        this.add(transitionOutPanel).fill().expand();

/*

        matchPosition = new CheckBox("Match position", skin);

        matchPosition.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent evt, Actor actor) {
                if(matchPosition.isChecked()) {
                    displayedMusicEvent.setTransitionIn(new MatchPosition());
                }
            }
        });

        transitionPanel.add(matchPosition).left().row();

        fadeIn = new CheckBox("Fade start", skin);

        fadeIn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent evt, Actor actor) {
                if(fadeIn.isChecked()) {
                    displayedMusicEvent.setTransitionIn(new FadeIn(0.5f));
                }
            }
        });

        ButtonGroup inGroup = new ButtonGroup(matchPosition, fadeIn);
        inGroup.setMinCheckCount(0);
        inGroup.setMaxCheckCount(1);

        transitionPanel.add(fadeIn).left().row();

        transitionPanel.add("Transition out").row();
        fadeOut = new CheckBox("Fade out", skin);

        fadeOut.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent evt, Actor actor) {
                if(fadeOut.isChecked()){
                    displayedMusicEvent.setTransitionOut(new FadeOut(0.3f));
                }
            }
        });

        transitionPanel.add(fadeOut).left().row();
        this.add(transitionPanel).fill().expand();

*/


        this.setVisible(false);

    }


    public void show(MusicState musicEvent) {
        this.setVisible(true);
        this.displayedState = musicEvent;

        this.trackListPanel.show(displayedState);
        this.transitionInPanel.setMusicEvent(displayedState);
        this.transitionOutPanel.setMusicEvent(displayedState);





    }
}
