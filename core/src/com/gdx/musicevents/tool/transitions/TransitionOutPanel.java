package com.gdx.musicevents.tool.transitions;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ObjectMap;
import com.gdx.musicevents.*;

public class TransitionOutPanel extends Table {


    MusicEvent musicEvent;
    List<EffectDecorator> outTransitions;

    final Button add;
    final Button remove;

    public TransitionOutPanel(final Skin skin, final Stage stage, final MusicEventManager manager) {
        super(skin);

        this.top().left();
        this.add("Transition out").colspan(2).row();
        outTransitions = new List<EffectDecorator>(skin);
        outTransitions.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                remove.setDisabled(outTransitions.getItems().size == 0 && outTransitions.getSelectedIndex() != -1);
            }
        });
        this.add(new ScrollPane(outTransitions)).colspan(2).fill().expand().row();

        add = new TextButton("Add", skin);
        add.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                AddTransitionOutDialog dialog = new AddTransitionOutDialog(skin, TransitionOutPanel.this, manager, musicEvent);
                dialog.show(stage);
            }
        });

        this.add(add).fillX().expandX();


        remove = new TextButton("Remove", skin);
        remove.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                EffectDecorator decorator = outTransitions.getSelected();

                musicEvent.removeOutTransition(decorator.name);

                setMusicEvent(musicEvent);
            }
        });
        remove.setDisabled(true);
        this.add(remove).fillX().expandX();
    }
    public void setMusicEvent(MusicEvent musicEvent) {
        this.musicEvent = musicEvent;

        outTransitions.getItems().clear();

        ObjectMap<String, Effect> effects = musicEvent.getOutTransitions();

        for(ObjectMap.Entry<String, Effect> entry : effects){
            outTransitions.getItems().add(new EffectDecorator(entry.key, entry.value));
        }
        remove.setDisabled(effects.size == 0);
    }

    public Button getAddButton() {
        return add;
    }
}
