package com.gdx.musicevents.tool.transitions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ObjectMap;
import com.gdx.musicevents.effects.Effect;
import com.gdx.musicevents.MusicEventManager;
import com.gdx.musicevents.State;

public class TransitionInPanel extends Table {
    State musicEvent;
    List<EffectDecorator> inTransitions;

    final Button add;
    final Button remove;

    public TransitionInPanel(final Skin skin, final Stage stage, final MusicEventManager manager) {
        super(skin);
        Pixmap pm1 = new Pixmap(1, 1, Format.RGB565);
        pm1.setColor(Color.DARK_GRAY);
        pm1.fill();
        this.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm1))));
        
        this.top().left();
        this.defaults().top().left();

        final Label enterLabel = new Label("Enter", skin);
        this.add(enterLabel).colspan(2).row();
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

                setMusicEvent(musicEvent);
            }
        });
        this.add(remove).fillX().expandX();


        remove.setDisabled(true);
    }
    public void setMusicEvent(State musicEvent) {
        this.musicEvent = musicEvent;

        inTransitions.getItems().clear();

        ObjectMap<String, Effect> effects = musicEvent.getEnterTransitions();

        for(ObjectMap.Entry<String, Effect> entry : effects){
            inTransitions.getItems().add(new EffectDecorator(entry.key, entry.value));
        }
        remove.setDisabled(effects.size == 0 || inTransitions.getSelected() == null);
    }

    public Button getAddButton() {
        return add;
    }
}
