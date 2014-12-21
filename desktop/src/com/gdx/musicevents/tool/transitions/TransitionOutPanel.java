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
import com.gdx.musicevents.effects.Effect;
import com.gdx.musicevents.tool.EventDetailsPanel;
import com.gdx.musicevents.MusicEventManager;
import com.gdx.musicevents.State;

public class TransitionOutPanel extends Table {


    State musicEvent;
    List<EffectDecorator> outTransitions;

    final Button add;
    final Button remove;

    public TransitionOutPanel(final Skin skin, final Stage stage, final MusicEventManager manager, final EventDetailsPanel eventDetailsPanel) {
        super(skin);

        setBackground(skin.getDrawable("panel-background"));
        this.top().left();
        this.defaults().top().left();
        final Label exitLabel = new Label("Exit", skin);
        this.add(exitLabel).colspan(2).row();
        outTransitions = new List<EffectDecorator>(skin);
        outTransitions.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                remove.setDisabled(outTransitions.getItems().size == 0 || outTransitions.getSelected() == null);
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

                musicEvent.removeExitTransition(decorator.name);

                setMusicEvent(musicEvent);
            }
        });
        remove.setDisabled(true);
        this.add(remove).fillX().expandX();
    }
    public void setMusicEvent(State musicEvent) {
        this.musicEvent = musicEvent;

        outTransitions.getItems().clear();

        ObjectMap<String, Effect> effects = musicEvent.getExitTransitions();

        for(ObjectMap.Entry<String, Effect> entry : effects){
            outTransitions.getItems().add(new EffectDecorator(entry.key, entry.value));
        }
        remove.setDisabled(effects.size == 0 || outTransitions.getSelected() == null);
    }

    public Button getAddButton() {
        return add;
    }
}
