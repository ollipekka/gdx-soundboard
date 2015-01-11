package com.gdx.musicevents.tool.transitions;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.gdx.musicevents.effects.FadeOut;
import com.gdx.musicevents.effects.StopEffect;
import com.gdx.musicevents.MusicEventManager;
import com.gdx.musicevents.MusicState;
import com.gdx.musicevents.effects.Stop;

public class AddTransitionOutDialog extends Dialog {


    private final static String DEFAULT = "Default";
    private final static String FADE_OUT = "Fade out";


    final SelectBox<String> selectBox;
    final Cell<? extends Actor> propertiesCell;
    final List<MusicState> availableEvents;
    final MusicState musicEvent;
    final TransitionOutPanel panel;

    public AddTransitionOutDialog(final Skin skin, final TransitionOutPanel panel, final MusicEventManager manager, final MusicState musicEvent) {
        super("Add transition", skin);
        this.musicEvent = musicEvent;
        this.panel = panel;
        availableEvents = new List<MusicState>(skin);

        Array<MusicState> events = manager.getEvents();
        events.removeValue(musicEvent, true);

        Array<String> usedEventNames = musicEvent.getExitTransitions().keys().toArray();

        for(int i = 0; i < usedEventNames.size; i++){
            String usedEventName = usedEventNames.get(i);
            for(int j = 0; j < events.size; j++){
                MusicState event = events.get(j);
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
        selectBox.setItems(DEFAULT, FADE_OUT);

        effectPanel.add(selectBox).fillX().expandX().row();

        propertiesCell = effectPanel.add(new DefaultEndEffectPanel(skin)).fill().expand();

        selectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selected = selectBox.getSelected();

                if(selected.equals(DEFAULT)){
                    propertiesCell.setActor(new DefaultEndEffectPanel(skin));

                } else if(selected.equals(FADE_OUT)) {
                    propertiesCell.setActor(new FadeEffectPanel(skin));
                }
            }
        });

        getContentTable().add(effectPanel).minSize(400, 200);


        button("Add", true);
        button("Cancel", false);


        key(Keys.ENTER, true);
        key(Keys.ESCAPE, false);
    }

    @Override
    protected void result(Object object) {
        boolean success = (Boolean)object;

        if(success){
            String eventName = availableEvents.getSelected().getName();
            String effectName = selectBox.getSelected();

            StopEffect effect = null;
            if(effectName.equals(DEFAULT)){
                effect = new Stop();
            } else if(effectName.equals(FADE_OUT)){
                FadeEffectPanel fadeEffectPanel = (FadeEffectPanel) propertiesCell.getActor();

                effect = new FadeOut(fadeEffectPanel.offset.getValue(), fadeEffectPanel.duration.getValue());
            }

            musicEvent.addExitTransition(eventName, effect);
            panel.setMusicState(musicEvent);
        }

    }
}
