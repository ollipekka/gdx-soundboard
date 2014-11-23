package com.soundboard;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class EventListPanel extends Table {

    final Skin skin;

    final SoundBoard planner;
    final List<Event> eventList;


    public EventListPanel(final Skin skin, final SoundBoard planner) {
        super(skin);

        this.skin = skin;
        this.planner = planner;

        this.defaults().fillX().expandX();

        this.add(new Label("Events", skin)).colspan(2).row();

        eventList = new List<Event>(skin);
        eventList.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(eventList.getSelectedIndex() > -1){
                    Event selected = eventList.getSelected();
                    planner.showEvent(selected);
                }
            }
        });

        this.add(eventList).fillY().expandY().colspan(2).row();
        TextButton add = new TextButton("Add", skin);

        add.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                final Dialog addDialog = new AddEventDialog(skin, EventListPanel.this);
                addDialog.show(planner.getStage());

            }
        });

        this.add(add);
        TextButton remove = new TextButton("Remove", skin);
        this.add(remove);

    }


    public void addEvent(Event event) {
        this.eventList.getItems().add(event);
    }

    public void clearEvents(){
        Array<Event> items = eventList.getItems();
        for(int i = 0; i < items.size; i++){
            Event event = items.get(i);
            event.dispose();
        }

        items.clear();
    }
}
