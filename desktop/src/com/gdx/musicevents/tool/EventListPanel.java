package com.gdx.musicevents.tool;

import java.text.Collator;
import java.util.Comparator;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gdx.musicevents.MusicEvent;
import com.gdx.musicevents.MusicEventListener;
import com.gdx.musicevents.MusicEventManager;

public class EventListPanel extends Table {

    final Skin skin;

    final MusicEventTool planner;
    final MusicEventManager manager;
    final List<MusicEvent> eventList;

    final TextButton remove;


    Comparator<MusicEvent> comparator = new Comparator<MusicEvent>() {
        @Override
        public int compare(MusicEvent o1, MusicEvent o2) {

            Collator comp = java.text.Collator.getInstance();
            return comp.compare(o1.getName(), o2.getName());
        }
    };

    public EventListPanel(final Skin skin, final MusicEventTool planner) {
        super(skin);

        this.skin = skin;
        this.planner = planner;
        this.manager = planner.eventManager;

        this.defaults().fillX().expandX();

        this.add(new Label("Events", skin)).colspan(2).row();

        manager.addListener(new MusicEventListener(){

            @Override
            public void eventAdded(MusicEvent event) {;
                eventList.getItems().add(event);
                remove.setDisabled(eventList.getItems().size == 0);
                eventList.getItems().sort(comparator);
            }

            @Override
            public void eventRemoved(MusicEvent event) {
                eventList.getItems().removeValue(event, true);
                remove.setDisabled(eventList.getItems().size == 0);
                eventList.getItems().sort(comparator);;
            }
        });

        eventList = new List<MusicEvent>(skin);
        eventList.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(eventList.getSelectedIndex() > -1){
                    MusicEvent selected = eventList.getSelected();
                    planner.showEvent(selected);
                }

            }
        });

        this.add(eventList).fillY().expandY().colspan(2).row();
        TextButton add = new TextButton("Add", skin);

        add.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                final Dialog addDialog = new AddEventDialog(planner.getStage(), skin, manager);
                addDialog.show(planner.getStage());

            }
        });

        this.add(add);
        remove = new TextButton("Remove", skin);
        remove.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                manager.remove(eventList.getSelected());
            }
        });

        this.add(remove);

        remove.setDisabled(eventList.getItems().size == 0);


    }
}
