package com.gdx.musicevents.tool.file;

import java.io.File;
import java.io.FileFilter;
import java.util.Comparator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Array;

public class FileChooser extends Dialog {

    public interface FileChooserResult {
        public boolean result(boolean success, FileHandle result);
    }

    final Skin skin;
    boolean fileNameEnabled;
    boolean newFolderEnabled;
    final TextField fileNameInput;
    final Label fileNameLabel;
    final TextButton newDirButton;
    final FileHandle baseDir;
    final Label dirLabel;
    final List<FileListItem> dirList;

    FileHandle currentDir;
    protected String result;

    
    
    Stage stage;

    private TextButton ok;

    private static final Comparator<FileListItem> dirListComparator = new Comparator<FileListItem>() {
        @Override
        public int compare(FileListItem file1, FileListItem file2) {
            if (file1.file.isDirectory() && !file2.file.isDirectory())
                return -1;
            if (file1.file.isDirectory() && file2.file.isDirectory()) {
                return 0;
            }
            if (!file1.file.isDirectory() && !file2.file.isDirectory()) {
                return 0;
            }
            return 1;
        }
    };
    private FileFilter filter = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            return true;
        }
    };
    private TextButton cancel;

    public FileChooser(String title, final Skin skin, FileHandle baseDir) {
        super(title, skin);
        this.skin = skin;
        this.baseDir = baseDir;

        final Table content = getContentTable();
        content.top().left();

        dirLabel = new Label("", skin);
        dirLabel.setAlignment(Align.left);

        dirList = new List<FileListItem>(skin);
        dirList.getSelection().setProgrammaticChangeEvents(false);


        fileNameInput = new TextField("", skin);
        fileNameLabel = new Label("File name:", skin);
        fileNameInput.setTextFieldListener(new TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                result = textField.getText();
            }
        });

        newDirButton = new TextButton("New Folder", skin);

        newDirButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if (newDirButton.isChecked()) {
                    newDirButton.setChecked(false);
                    new NewFileDialog("New Folder", skin) {
                        protected void result(Object object) {
                            boolean success = (Boolean) object;
                            if (success) {
                                FileHandle newFolder = new FileHandle(
                                        currentDir.path() + "/" + getResult());
                                newFolder.mkdirs();
                                changeDirectory(currentDir);
                            }
                        };
                    }.show(stage);
                }
            }
        });

        ok = new TextButton("Ok", skin);
        button(ok, true);

        cancel = new TextButton("Cancel", skin);
        button(cancel, false);
        key(Keys.ENTER, true);
        key(Keys.ESCAPE, false);

        dirList.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                FileListItem selected = dirList.getSelected();
                if (!selected.file.isDirectory()) {
                    result = selected.file.name();
                    fileNameInput.setText(result);
                }
            }
        });

        dirList.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                FileListItem selected = dirList.getSelected();
                if (selected.file.isDirectory()) {
                    changeDirectory(selected.file);
                }
            }
        });
    }
    @Override
    public float getPrefWidth() {
        return 300;
    }
    @Override
    public float getPrefHeight() {
        return 350;
    }
    
    @Override
    public Dialog show(Stage stage, Action action) {
        Table content = getContentTable();
        content.add(dirLabel).top().left().expandX().fillX().row();
        content.add(new ScrollPane(dirList, skin)).fill().expand().row();

        if(fileNameEnabled){
            content.add(fileNameLabel).fillX().expandX().row();
            content.add(fileNameInput).fillX().expandX().row();
        }

        if(newFolderEnabled){
            content.add(newDirButton).fillX().expandX().row();
        }
        
        this.stage = stage;
        changeDirectory(baseDir);
        return super.show(stage, action);
    }

    private void changeDirectory(FileHandle directory) {

        currentDir = directory;
        dirLabel.setText(currentDir.path());

        Array<FileListItem> items = new Array<FileListItem>();

        FileHandle[] list = directory.list(filter);
        for (FileHandle handle : list) {
            items.add(new FileListItem(handle));
        }

        items.sort(dirListComparator);

        if (directory.file().getParentFile() != null) {
            items.insert(0, new FileListItem("..", directory.parent()));
        }

        dirList.setSelected(null);
        dirList.setItems(items);
    }

    public FileHandle getResult() {
        String path = currentDir.path() + "/";
        if (result != null && result.length() > 0) {
            path += result;
        }
        return new FileHandle(path);
    }

    public FileChooser setFilter(FileFilter filter) {
        this.filter = filter;
        return this;
    }

    public FileChooser setOkButtonText(String text) {
        this.ok.setText(text);
        return this;

    }

    public static FileChooser createSaveDialog(String title, final Skin skin,
            final FileHandle path, final FileChooserResult callback) {
        FileChooser save = new FileChooser(title, skin, path) {
            protected void result(Object object) {
                boolean success = (Boolean) object;
                if (!callback.result(success, getResult())) {
                    this.cancel();
                }
            }
        }.setFileNameEnabled(true).setNewFolderEnabled(true)
                .setOkButtonText("Save");

        return save;

    }

    public static FileChooser createLoadDialog(String title, final Skin skin,
            final FileHandle path, final FileChooserResult callback) {
        FileChooser load = new FileChooser(title, skin, path) {
            protected void result(Object object) {
                boolean success = (Boolean) object;
                callback.result(success, getResult());
            }
        }.setNewFolderEnabled(false).setFileNameEnabled(false).setOkButtonText("Load");

        return load;

    }

    public static FileChooser createPickDialog(String title, final Skin skin,
            final FileHandle path, final FileChooserResult callback) {
        FileChooser pick = new FileChooser(title, skin, path) {
            protected void result(Object object) {
                boolean success = (Boolean) object;
                callback.result(success, getResult());
            }
        }.setOkButtonText("Select");

        return pick;

    }

    public FileChooser setFileNameEnabled(boolean fileNameEnabled) {
        this.fileNameEnabled = fileNameEnabled;
        return this;
    }

    public FileChooser setNewFolderEnabled(boolean newFolderEnabled) {
        this.newFolderEnabled = newFolderEnabled;
        return this;
    }

}
