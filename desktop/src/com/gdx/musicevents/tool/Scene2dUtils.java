package com.gdx.musicevents.tool;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class Scene2dUtils {
    public static Label addLabel(String labelText, Table parent, Skin skin){
        Label label = new Label(labelText, skin);
        label.setAlignment(Align.left);
        parent.add(label).left();

        Label info = new Label("", skin);
        info.setAlignment(Align.right);
        parent.add(info).right().row();

        return info;
    }

    public static TextField addTextField(String labelText, Table parent, Skin skin){
        Label label = new Label(labelText, skin);
        label.setAlignment(Align.left);
        parent.add(label).left();

        TextField info = new TextField("", skin);
        parent.add(info).right().fillX().expandX().row();

        return info;
    }


    public static CheckBox addCheckBox(String labelText, Table parent, Skin skin){

        CheckBox info = new CheckBox(labelText, skin);
        parent.add(info).right().fillX().expandX().row();

        return info;
    }

    public static Dialog showAlert(String title, String text, Skin skin, Stage stage){
        Dialog dialog = new Dialog(title, skin);
        dialog.text(text);
        dialog.button("Ok");
        dialog.show(stage);

        return dialog;
    }
}
