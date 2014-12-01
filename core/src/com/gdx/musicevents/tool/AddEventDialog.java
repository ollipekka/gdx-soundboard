package com.gdx.musicevents.tool;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gdx.musicevents.MusicEvent;
import com.gdx.musicevents.MusicEventManager;

public class AddEventDialog extends Dialog {
    FileHandle file;


    MusicEventManager eventManager;
    final Skin skin;
    final Stage stage;

    final TextField eventName;

    final Label fileName;


    public AddEventDialog(final Stage stage, final Skin skin, final MusicEventManager eventManager) {

        super("Add Event", skin);
        this.stage = stage;
        this.skin = skin;
        this.eventManager = eventManager;


        Table content = this.getContentTable();

        eventName = Scene2dUtils.addTextField("Event name", content, skin);

        fileName = Scene2dUtils.addLabel("Clip:", content, skin);
        fileName.setText("No file selected.");

        content.add();
        final Button browse = new TextButton("Browse", skin);
        browse.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                BrowseFilesDialog dialog = new BrowseFilesDialog(skin, AddEventDialog.this);
                dialog.show(stage);
            }
        });

        content.add(browse).row();

        this.button("Ok", true);
        this.button("Cancel", false);
    }

    void setClipFile(FileHandle handle){
        file = handle;
        fileName.setText(handle.toString());

    }

    @Override
    protected void result(Object object) {
        boolean result = (Boolean)object;

        if(result) {
            if (file == null) {
                cancel();
                Scene2dUtils.showAlert("Error", "You must select a file.", skin, stage);
                return;
            }

            if (!file.exists()) {
                cancel();

                Scene2dUtils.showAlert("Error", "The file that you selecte doesn't exist.", skin, stage);
                return;
            }

            if (eventName.getText() == null || eventName.getText().length() == 0) {
                cancel();

                Scene2dUtils.showAlert("Error", "You must provide a name.", skin, stage);
                return;
            }

            eventManager.add(new MusicEvent(eventName.getText(), file));
        }
    }
}
