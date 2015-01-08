package com.gdx.musicevents.tool.transitions;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class FadeEffectPanel extends DefaultStartEffectPanel {

    final Slider offset;
    final Slider duration;

    public FadeEffectPanel(Skin skin) {
        super(skin);


        //final DecimalFormat twoDecimalFormat = new DecimalFormat("###.##");
        final Label offsetLabel = new Label("", skin);
        this.add(offsetLabel).colspan(2).fillX().expandX().row();


        offset = new Slider(0, 10, 0.1f, false, skin);
        this.add(offset).fillX().expandX().row();

        offset.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int offsetInt = (int)(offset.getValue() * 100);
                offsetLabel.setText("Offset " + offsetInt / 100.0f + " sec.");
            }
        });

        offset.setValue(0.2f);


        final Label durationLabel = new Label("", skin);
        this.add(durationLabel).fillX().expandX().row();
        duration = new Slider(0f, 10, 0.1f, false, skin);
        this.add(duration).fillX().expandX().row();
        duration.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int durationInt = (int)(duration.getValue() * 100);
                durationLabel.setText("Duration " + durationInt / 100.0f+ " sec.");
            }
        });

        duration.setValue(0.5f);

    }
}
