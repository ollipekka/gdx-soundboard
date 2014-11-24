package com.gdx.musicevents.tool;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gdx.musicevents.*;

public class EventDetailsPanel extends Table {

    private final Skin skin;
    private final Label eventName;
    private final Label clipName;
    private final Label clipLength;
    private final CheckBox looping;
    private final CheckBox matchPosition;
    private final CheckBox fadeIn;
    private final CheckBox fadeOut;

    private final Button play;

    MusicEvent displayedMusicEvent;


    public EventDetailsPanel(Skin skin, final MusicEventTool planner) {
        super(skin);
        this.skin = skin;

        final MusicEventManager manager = planner.eventManager;

        manager.addListener(new MusicEventListener() {
            @Override
            public void eventAdded(MusicEvent event) {

            }

            @Override
            public void eventRemoved(MusicEvent event) {
                if(event == displayedMusicEvent){
                    EventDetailsPanel.this.setVisible(false);
                }
            }
        });

        this.defaults().center().pad(10);

        Table infoPanel = new Table(skin);
        eventName = Scene2dUtils.addLabel("Event name", infoPanel, skin);
        clipName = Scene2dUtils.addLabel("Clip name", infoPanel, skin);
        clipLength = Scene2dUtils.addLabel("Clip length", infoPanel, skin);

        looping = new CheckBox("Looping", skin);


        looping.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent evt, Actor actor) {
                displayedMusicEvent.setLooping(looping.isChecked());
            }
        });

        infoPanel.add(looping).colspan(2).left().row();



        play = new TextButton("Play", skin);
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent evt, Actor actor) {

                manager.play(displayedMusicEvent.getName());

            }
        });
        infoPanel.add(play).fillX().colspan(2);

        this.add(infoPanel);

        Table transitionPanel = new Table(skin);
        transitionPanel.add("Transition start").row();

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


        this.add(transitionPanel);

        this.setVisible(false);

    }


    public void show(MusicEvent musicEvent) {
        this.setVisible(true);
        this.displayedMusicEvent = musicEvent;
        this.eventName.setText(musicEvent.getName());
        this.clipName.setText(musicEvent.getFileHandle().name());
        this.looping.setChecked(musicEvent.isLooping());

        Effect transitionIn = displayedMusicEvent.getTransitionIn();
        fadeIn.setChecked(transitionIn instanceof FadeIn);
        matchPosition.setChecked(transitionIn instanceof MatchPosition);
        Effect transitionOut = displayedMusicEvent.getTransitionOut();
        fadeOut.setChecked(transitionOut instanceof FadeOut);

    }
}
