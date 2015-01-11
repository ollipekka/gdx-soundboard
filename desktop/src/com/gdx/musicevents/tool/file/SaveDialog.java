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

public class SaveDialog extends Dialog {

    public enum Type {
        SAVE, LOAD, PICK
    }

    final FileHandle baseDir;
    final Label dir;
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

    public SaveDialog(String title, final Skin skin, FileHandle baseDir,
            Type type) {
        super(title, skin);
        this.baseDir = baseDir;

        final Table content = getContentTable();
        content.top().left();
        Table explorer = new Table(skin);

        dir = new Label("", skin);
        dir.setAlignment(Align.left);
        explorer.add(dir).top().left().expandX().fillX().row();

        dirList = new List<FileListItem>(skin);
        dirList.getSelection().setProgrammaticChangeEvents(false);

        explorer.add(new ScrollPane(dirList, skin)).fill().expand().row();
        content.add(explorer).minSize(300, 300).left().bottom().fillX()
                .expandX();


        final TextField fileName = new TextField("", skin);
        if (type == Type.SAVE) {
            Label label = new Label("File name:", skin);
            explorer.add(label).fillX().fillY().row();
            fileName.setTextFieldListener(new TextFieldListener() {
                @Override
                public void keyTyped(TextField textField, char c) {
                    result = textField.getText();
                }
            });
            explorer.add(fileName).fillX().fillY().row();
        }

        if (type != Type.LOAD) {
            final TextButton button = new TextButton("New Folder", skin);

            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {

                    if (button.isChecked()) {
                        button.setChecked(false);
                        new NewFileDialog("New Folder", skin) {
                            protected void result(Object object) {
                                boolean success = (Boolean) object;
                                if (success) {
                                    FileHandle newFolder = new FileHandle(
                                            currentDir.path() + "/"
                                                    + getResult());
                                    newFolder.mkdirs();
                                    changeDirectory(currentDir);
                                }
                            };
                        }.show(stage);
                    }
                }
            });
            explorer.add(button).fillX().expandX().row();
        }

        ok = new TextButton("Ok", skin);
        button(ok, true);

        cancel = new TextButton("cancel", skin);
        button(cancel, false);
        key(Keys.ENTER, true);
        key(Keys.ESCAPE, false);
        
        
        
        dirList.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                FileListItem selected = dirList.getSelected();
                if (!selected.file.isDirectory()) {
                    result = selected.file.name();
                    fileName.setText(result);
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
    public Dialog show(Stage stage, Action action) {
        this.stage = stage;
        changeDirectory(baseDir);
        return super.show(stage, action);
    }

    private void changeDirectory(FileHandle directory) {

        currentDir = directory;
        dir.setText(currentDir.path());

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
        if(result != null && result.length() > 0){
            path += result;
        }
        return new FileHandle(path);
    }

    public void setFilter(FileFilter filter) {
        this.filter = filter;
    }

    public void setOkButtonText(String text) {
        this.ok.setText(text);
        
    }
}
