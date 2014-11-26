package com.gdx.musicevents.tool.transitions;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.gdx.musicevents.*;

public class AddTransitionInDialog extends Dialog {


    private final static String DEFAULT = "Default";
    private final static String MATCH_POSITION = "Match position";
    private final static String FADE_IN = "Fade in";


    final SelectBox<String> selectBox;
    final Cell propertiesCell;
    final List<MusicEvent> availableEvents;
    final MusicEvent musicEvent;
    final TransitionInPanel panel;

    public AddTransitionInDialog(final Skin skin, final TransitionInPanel panel, final MusicEventManager manager, final MusicEvent musicEvent) {
        super("Add transition", skin);
        this.musicEvent = musicEvent;
        this.panel = panel;
        availableEvents = new List<MusicEvent>(skin);

        Array<MusicEvent> events = manager.getEvents();
        events.removeValue(musicEvent, true);

        Array<String> usedEventNames = musicEvent.getInTransitions().keys().toArray();

        for(int i = 0; i < usedEventNames.size; i++){
            String usedEventName = usedEventNames.get(i);
            for(int j = 0; j < events.size; j++){
                MusicEvent event = events.get(j);
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
        selectBox.setItems(DEFAULT, FADE_IN, MATCH_POSITION);

        effectPanel.add(selectBox).fillX().expandX().row();

        propertiesCell = effectPanel.add(new NoPropertiesPanel(skin)).fill().expand();

        selectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selected = selectBox.getSelected();

                if(selected.equals(DEFAULT)){
                    propertiesCell.setActor(new NoPropertiesPanel(skin));

                } else if(selected.equals(FADE_IN)) {
                    propertiesCell.setActor(new FadeEffectPanel(skin));

                } else if(selected.equals(MATCH_POSITION)){
                    propertiesCell.setActor(new NoPropertiesPanel(skin));
                }
            }
        });

        getContentTable().add(effectPanel).minSize(400, 200);


        button("Add", true);
        button("Cancel", false);
    }

    @Override
    protected void result(Object object) {
        boolean success = (Boolean)object;

        if(success){
            String eventName = availableEvents.getSelected().getName();
            String effectName = selectBox.getSelected();

            Effect effect = null;
            if(effectName.equals(MATCH_POSITION)){
                effect = new MatchPosition();
            } else if(effectName.equals(DEFAULT)){
                effect = new Play();
            } else if(effectName.equals(FADE_IN)){
                FadeEffectPanel fadeEffectPanel = (FadeEffectPanel) propertiesCell.getActor();

                effect = new FadeIn(fadeEffectPanel.offset.getValue(), fadeEffectPanel.duration.getValue());
            }

            musicEvent.addInTransition(eventName, effect);
            panel.setMusicEvent(musicEvent);

        }

    }
}
