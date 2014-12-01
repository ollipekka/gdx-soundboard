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

    private final List<FileHandle> dirList;
    private final AddEventDialog parentDialog;

    public BrowseFilesDialog(Skin skin, final AddEventDialog parentDialog) {
        super("Browse file", skin);

        this.parentDialog = parentDialog;

        Table content = this.getContentTable();

        dirList = new List<FileHandle>(skin);

        FileHandle dir = Gdx.files.internal("music/");


        FileHandle[] files = dir.list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.matches(".*.(ogg|wav|mp3)");
            }
        });

        dirList.setItems(files);
        ScrollPane scrollPane = new ScrollPane(dirList, skin);


        content.add(scrollPane).maxSize(300, 500).fill().expandX();

        button("Ok", true);
        button("Cancel", false);


    }


    @Override
    protected void result(Object object) {
        boolean result = (Boolean)object;

        if(result && dirList.getSelectedIndex() > -1){
            parentDialog.setClipFile(dirList.getSelected());
        }
    }
}
