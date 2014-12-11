package com.gdx.musicevents.tool.transitions;

import com.gdx.musicevents.effects.Effect;
import com.gdx.musicevents.effects.FadeIn;
import com.gdx.musicevents.effects.FadeOut;
import com.gdx.musicevents.effects.Play;
import com.gdx.musicevents.effects.Stop;

public class EffectDecorator {
    String name;
    Effect effect;

    public EffectDecorator(String name, Effect effect) {
        this.name = name;
        this.effect = effect;
    }

    @Override
    public String toString() {
        return name + "(" + getEffectName(effect) + ")";
    }

    private String getEffectName(Effect effect){
        if(effect instanceof Play){
            return "Play";
        } else if(effect instanceof Stop){
            return "Stop";
        } else if(effect instanceof FadeOut){
            return "Fade out";
        } else if(effect instanceof FadeIn){
            return "Fade in";
        }

        return "Unknown";
    }
}
