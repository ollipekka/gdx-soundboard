package com.soundboard;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class AddEventDialog extends Dialog {
    FileHandle file;
    final TextField eventName;

    final Label fileName;
    private final EventListPanel eventList;


    public AddEventDialog(final Skin skin, final EventListPanel eventList) {

        super("Add Event", skin);
        this.eventList = eventList;

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
                dialog.show(eventList.planner.getStage());
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

        if(file == null){
            cancel();
            Scene2dUtils.showAlert("Error", "You must select a file.", eventList.skin, eventList.planner.getStage());
            return;
        }

        if(!file.exists()){
            cancel();

            Scene2dUtils.showAlert("Error", "The file that you selecte doesn't exist.", eventList.skin, eventList.planner.getStage());
            return;
        }

        if(eventName.getText() == null || eventName.getText().length() == 0){
            cancel();

            Scene2dUtils.showAlert("Error", "You must provide a name.", eventList.skin, eventList.planner.getStage());
            return;
        }

        if(result){
            eventList.addEvent(new Event(eventName.getText(), file));
        }
    }
}
