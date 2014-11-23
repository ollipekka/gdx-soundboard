package com.soundboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class EventDetailsPanel extends Table {

    private final Skin skin;
    private final Label eventName;
    private final Label clipName;
    private final Label clipLength;
    private final CheckBox looping;
    private final CheckBox matchPosition;

    private final Button play;

    Event displayedEvent;


    public EventDetailsPanel(Skin skin, final SoundBoard planner) {
        super(skin);
        this.skin = skin;


        this.defaults().center();

        eventName = Scene2dUtils.addLabel("Event name", this, skin);
        clipName = Scene2dUtils.addLabel("Clip name", this, skin);
        clipLength = Scene2dUtils.addLabel("Clip length", this, skin);

        looping = new CheckBox("Looping", skin);


        looping.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent evt, Actor actor) {
                displayedEvent.getMusic().setLooping(looping.isChecked());
            }
        });

        this.add(looping).colspan(2).left().row();

        matchPosition = new CheckBox("MatchPosition", skin);

        matchPosition.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent evt, Actor actor) {
                displayedEvent.setMatchPosition(matchPosition.isChecked());
            }
        });

        this.add(matchPosition).colspan(2).left().row();

        play = new TextButton("Play", skin);
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent evt, Actor actor) {

                float position = 0;

                Event playingEvent = planner.getPlayingEvent();

                if(playingEvent != null){
                    if(displayedEvent.isMatchPosition()){
                        position = playingEvent.getPosition();
                    }
                    playingEvent.getMusic().stop();
                }

                planner.setPlayingEvent(displayedEvent);

                planner.getPlayingEvent().getMusic().play();
                if(displayedEvent.isMatchPosition()){

                    Gdx.app.log("EventDetailsPanel", "Set position:" + position);
                    planner.getPlayingEvent().setPosition(position);
                }

            }
        });
        this.add(play).fillX().colspan(2);

    }


    public void show(Event event) {
        if(event == null){
            this.setVisible(false);
        }else {
            this.setVisible(true);
            this.displayedEvent = event;
            this.eventName.setText(event.getName());
            this.clipName.setText(event.getFileHandle().name());
            this.looping.setChecked(event.getMusic().isLooping());
            this.matchPosition.setChecked(event.isMatchPosition());
        }

    }
}
