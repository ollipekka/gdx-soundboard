package com.gdx.musicevents.tool.transitions;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.text.DecimalFormat;

public class FadeEffectPanel extends Table {

    final Slider offset;
    final Slider duration;

    public FadeEffectPanel(Skin skin) {
        super(skin);

        top().left();

        final DecimalFormat twoDecimalFormat = new DecimalFormat("###.##");
        final Label offsetLabel = new Label("", skin);
        this.add(offsetLabel).colspan(2).fillX().expandX().row();


        offset = new Slider(0, 10, 0.1f, false, skin);
        this.add(offset).fillX().expandX().row();

        offset.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                offsetLabel.setText("Offset " + twoDecimalFormat.format(offset.getValue()) + " sec.");
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
                durationLabel.setText("Duration " + twoDecimalFormat.format(duration.getValue()) + " sec.");
            }
        });

        duration.setValue(0.5f);

    }
}
