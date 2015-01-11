package com.gdx.musicevents.tool;

import java.io.File;
import java.io.FileFilter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gdx.musicevents.MusicEventManager;
import com.gdx.musicevents.tool.file.LoadDialog;
import com.gdx.musicevents.tool.file.SaveDialog;
import com.gdx.musicevents.tool.file.SaveDialog.Type;

public class MenuDialog extends Dialog {

    public MenuDialog(final Skin skin, final Stage stage,
            final MusicEventManager manager) {
        super("Menu", skin);

        Table content = getContentTable();
        content.defaults().expandX().fillX().minWidth(175);
        
        Button newProject = new TextButton("New", skin);
        newProject.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SaveDialog newProject = new SaveDialog("New project", skin,  Gdx.files.internal("./"), Type.PICK) {
                    protected void result(Object object) {
                        boolean success = (Boolean) object;
                        if (success) {
                            manager.create(getResult());
                        }
                    }
                };
                newProject.setFilter(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        return file.isDirectory();
                    }
                } );
                newProject.setOkButtonText("Create");
                newProject.show(stage);
                
                hide();
            }
        });
        
        content.add(newProject).row();
        
        /* Save button. */
        Button save = new TextButton("Save", skin);
        save.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SaveDialog save = new SaveDialog("Save project", skin,  Gdx.files.internal("./"), Type.SAVE) {
                    protected void result(Object object) {
                        boolean success = (Boolean) object;
                        if (success) {
                            manager.save(getResult());
                        }
                    }
                };
                save.setOkButtonText("Save");
                save.show(stage);
            }
        });
        content.add(save).row();

        /* Load button. */
        Button load = new TextButton("Load", skin);
        load.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                LoadDialog load = new LoadDialog("Load project", skin, Gdx.files.internal("./")) {
                    protected void result(Object object) {
                        boolean success = (Boolean) object;
                        if (success) {
                            manager.load(result);
                        }
                    };
                };

                load.show(stage);
            }
        });
        content.add(load).row();

        /* Exit button. */
        Button exit = new TextButton("Exit", skin);
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        content.add(exit).row();

        /* Cancel button. */
        Button cancel = new TextButton("Cancel", skin);
        cancel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();
            }
        });
        content.add(cancel).row();
        

        key(Keys.ESCAPE, true);
    }

}
