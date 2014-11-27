package com.gdx.musicevents.tool.transitions;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ObjectMap;
import com.gdx.musicevents.Effect;
import com.gdx.musicevents.MusicEvent;
import com.gdx.musicevents.MusicEventManager;

public class TransitionInPanel extends Table {
    MusicEvent musicEvent;
    List<EffectDecorator> inTransitions;

    final Button add;
    final Button remove;

    public TransitionInPanel(final Skin skin, final Stage stage, final MusicEventManager manager) {
        super(skin);

        this.top().left();
        this.add("Transition in").colspan(2).row();
        inTransitions = new List<EffectDecorator>(skin);
        this.add(new ScrollPane(inTransitions)).colspan(2).fill().expand().row();
        inTransitions.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                remove.setDisabled(inTransitions.getItems().size == 0 && inTransitions.getSelectedIndex() != -1);
            }
        });
        add = new TextButton("Add", skin);
        add.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                AddTransitionInDialog dialog = new AddTransitionInDialog(skin, TransitionInPanel.this, manager, musicEvent);
                dialog.show(stage);
            }
        });

        this.add(add).fillX().expandX();


        remove = new TextButton("Remove", skin);
        remove.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                EffectDecorator decorator = inTransitions.getSelected();

                musicEvent.removeInTransition(decorator.name);

                setMusicEvent(musicEvent);
            }
        });
        this.add(remove).fillX().expandX();


        remove.setDisabled(true);
    }
    public void setMusicEvent(MusicEvent musicEvent) {
        this.musicEvent = musicEvent;

        inTransitions.getItems().clear();

        ObjectMap<String, Effect> effects = musicEvent.getInTransitions();

        for(ObjectMap.Entry<String, Effect> entry : effects){
            inTransitions.getItems().add(new EffectDecorator(entry.key, entry.value));
        }
        remove.setDisabled(effects.size == 0);
    }

    public Button getAddButton() {
        return add;
    }
}
