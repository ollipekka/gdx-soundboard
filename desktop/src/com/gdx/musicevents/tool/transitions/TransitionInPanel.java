package com.gdx.musicevents.tool.transitions;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ObjectMap;
import com.gdx.musicevents.MusicEventManager;
import com.gdx.musicevents.MusicState;
import com.gdx.musicevents.effects.StartEffect;
import com.gdx.musicevents.tool.MusicStatePanel;

public class TransitionInPanel extends Table {
    MusicState musicEvent;
    List<EffectDecorator> inTransitions;

    final Button add;
    final Button remove;

    public TransitionInPanel(final Skin skin, final Stage stage, final MusicEventManager manager, MusicStatePanel eventDetailsPanel) {
        super(skin);

        setBackground(skin.getDrawable("panel-background"));

        this.top().left();
        this.defaults().top().left().pad(2);

        final Label enterLabel = new Label("Enter", skin, "title");
        this.add(enterLabel).fillX().expandX().colspan(2).row();
        inTransitions = new List<EffectDecorator>(skin);
        this.add(new ScrollPane(inTransitions)).colspan(2).fill().expand().row();
        inTransitions.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                remove.setDisabled(inTransitions.getItems().size == 0 || inTransitions.getSelected() == null);
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

                musicEvent.removeEnterTransition(decorator.name);

                setMusicState(musicEvent);
            }
        });
        this.add(remove).fillX().expandX();


        remove.setDisabled(true);
    }
    public void setMusicState(MusicState musicEvent) {
        this.musicEvent = musicEvent;

        inTransitions.getItems().clear();

        ObjectMap<String, StartEffect> effects = musicEvent.getEnterTransitions();

        for(ObjectMap.Entry<String, StartEffect> entry : effects){
            inTransitions.getItems().add(new EffectDecorator(entry.key, entry.value));
        }
        remove.setDisabled(effects.size == 0 || inTransitions.getSelected() == null);
    }

    public Button getAddButton() {
        return add;
    }
}
