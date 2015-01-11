package com.gdx.musicevents.tool.file;

import java.text.DateFormat;
import java.util.Date;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class FileInfoPanel extends Table {

    final Label path;
    final Label lastModified;
    final Label size;
    
    public FileInfoPanel(Skin skin) {
        this.top().left();
        path = addLabel("File:", this, skin);
        lastModified = addLabel("Modified:", this, skin);
        size = addLabel("Size:", this, skin);
        
    }
    
    public void show(FileHandle file){
        path.setText(file.toString());

        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
        Date date = new Date(file.lastModified());
        lastModified.setText(df.format(date));
        
        size.setText(Long.toString(file.length()));
    }
    
    public static Label addLabel(String labelText, Table parent, Skin skin){
        Label label = new Label(labelText, skin);
        label.setAlignment(Align.left);
        parent.add(label).left();

        Label info = new Label("", skin);
        info.setAlignment(Align.right);
        parent.add(info).right().row();

        return info;
    }
}
