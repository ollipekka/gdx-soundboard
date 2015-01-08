package com.gdx.musicevents.tool;

import java.io.File;
import java.io.FilenameFilter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class BrowseFilesDialog extends Dialog {

    private final List<FileHandle> fileList;
    public BrowseFilesDialog(Skin skin) {
        super("Browse file", skin);

        Table content = this.getContentTable();

        fileList = new List<FileHandle>(skin);

        FileHandle dir = Gdx.files.internal("music/");

        FileHandle[] files = dir.list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.matches(".*.(ogg|wav|mp3)");
            }
        });

        fileList.setItems(files);
        ScrollPane scrollPane = new ScrollPane(fileList, skin);

        content.add(scrollPane).maxSize(300, 500).fill().expandX();
        
        Table buttons = getButtonTable();
        buttons.defaults().fillX().expandX();
        
        button("Ok", true);
        button("Cancel", false);
    }
    
    public String getSelectedPath(){
        return fileList.getSelected().path();
    }
}
