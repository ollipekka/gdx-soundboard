package com.gdx.musicevents.tool.transitions;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.gdx.musicevents.effects.AbstractEffect;
import com.gdx.musicevents.effects.FadeIn;
import com.gdx.musicevents.MusicEventManager;
import com.gdx.musicevents.State;
import com.gdx.musicevents.effects.Play;

public class AddTransitionInDialog extends Dialog {


    private final static String DEFAULT = "Default";
    private final static String FADE_IN = "Fade in";


    final SelectBox<String> selectBox;
    final Cell<? extends Actor> propertiesCell;
    final List<State> availableEvents;
    final State musicEvent;
    final TransitionInPanel panel;

    final Button add;

    public AddTransitionInDialog(final Skin skin, final TransitionInPanel panel, final MusicEventManager manager, final State musicEvent) {
        super("Add transition", skin);
        this.musicEvent = musicEvent;
        this.panel = panel;
        availableEvents = new List<State>(skin);

        Array<State> events = manager.getEvents();
        events.removeValue(musicEvent, true);

        Array<String> usedEventNames = musicEvent.getEnterTransitions().keys().toArray();

        for(int i = 0; i < usedEventNames.size; i++){
            String usedEventName = usedEventNames.get(i);
            for(int j = 0; j < events.size; j++){
                State event = events.get(j);
                if(event.getName().equals(usedEventName)) {
                    events.removeIndex(j);
                    break;
                }
            }
        }

        availableEvents.setItems(events);
        getContentTable().add(new ScrollPane(availableEvents)).minSize(100, 200);

        final Table effectPanel = new Table(skin);
        effectPanel.top().left();
        selectBox = new SelectBox<String>(skin);
        selectBox.setItems(DEFAULT, FADE_IN);

        effectPanel.add(selectBox).fillX().expandX().row();

        propertiesCell = effectPanel.add(new DefaultStartEffectPanel(skin, false)).fill().expand();

        selectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selected = selectBox.getSelected();

                if(selected.equals(DEFAULT)){
                    propertiesCell.setActor(new DefaultStartEffectPanel(skin, true));

                } else if(selected.equals(FADE_IN)) {
                    propertiesCell.setActor(new FadeEffectPanel(skin, true));

                }
            }
        });

        getContentTable().add(effectPanel).minSize(400, 200);



        add = new TextButton("Add", skin);
        add.setDisabled(events.size == 0);
        button(add, true);
        button("Cancel", false);
    }

    @Override
    protected void result(Object object) {
        boolean success = (Boolean)object;

        if(success){
            String eventName = availableEvents.getSelected().getName();
            String effectName = selectBox.getSelected();

            AbstractEffect effect = null;
            if(effectName.equals(DEFAULT)){
                DefaultStartEffectPanel startEffectPanel = (DefaultStartEffectPanel) propertiesCell.getActor();
                effect = new Play();
                effect.setMatchPosition(startEffectPanel.matchPosition.isChecked());
            } else if(effectName.equals(FADE_IN)){
                FadeEffectPanel fadeEffectPanel = (FadeEffectPanel) propertiesCell.getActor();

                effect = new FadeIn(fadeEffectPanel.offset.getValue(), fadeEffectPanel.duration.getValue());
                effect.setMatchPosition(fadeEffectPanel.matchPosition.isChecked());
            }

            musicEvent.addEnterTransition(eventName, effect);
            panel.setMusicEvent(musicEvent);

        }

    }
}
