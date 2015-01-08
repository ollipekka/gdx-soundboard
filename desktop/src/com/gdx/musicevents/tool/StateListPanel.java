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
import com.gdx.musicevents.MusicEventListener;
import com.gdx.musicevents.MusicEventManager;
import com.gdx.musicevents.MusicState;

public class StateListPanel extends Table {

    final Skin skin;

    final MusicEventTool planner;
    final MusicEventManager manager;
    final List<MusicState> eventList;

    final TextButton remove;
    
    final Table content;


    Comparator<MusicState> comparator = new Comparator<MusicState>() {
        @Override
        public int compare(MusicState o1, MusicState o2) {

            Collator comp = java.text.Collator.getInstance();
            return comp.compare(o1.getName(), o2.getName());
        }
    };

    public StateListPanel(final Skin skin, final MusicEventTool planner) {
        super(skin);
        
        this.pad(2);
        
        this.skin = skin;
        this.planner = planner;
        this.manager = planner.getEventManager();

        content = new Table();
        content.setBackground(skin.getDrawable("panel-background"));
        content.defaults().top().left().fillX().expandX().pad(2);

        content.add(new Label("Events", skin, "title")).colspan(2).row();

        manager.addListener(new MusicEventListener(){

            @Override
            public void stateAdded(MusicState event) {
                boolean wasEmpty = eventList.getItems().size == 0;
                
                eventList.getItems().add(event);
                remove.setDisabled(eventList.getItems().size == 0);
                eventList.getItems().sort(comparator);
                
                if(wasEmpty){
                    eventList.setSelectedIndex(0);
                }
            }

            @Override
            public void stateRemoved(MusicState event) {
                eventList.getItems().removeValue(event, true);
                remove.setDisabled(eventList.getItems().size == 0);
                eventList.getItems().sort(comparator);
            }
        });

        eventList = new List<MusicState>(skin);
        eventList.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(eventList.getSelectedIndex() > -1){
                    MusicState selected = eventList.getSelected();
                    planner.showEvent(selected);
                }

            }
        });

        content.add(eventList).fillY().expandY().colspan(2).row();
        TextButton add = new TextButton("Add", skin);

        add.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                final Dialog addDialog = new AddStateDialog(planner.getStage(), skin, manager);
                addDialog.show(planner.getStage());

            }
        });

        content.add(add);
        remove = new TextButton("Remove", skin);
        remove.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                manager.remove(eventList.getSelected());
            }
        });

        content.add(remove);

        remove.setDisabled(eventList.getItems().size == 0);
        
        this.add(content).fill().expand();


    }
}
