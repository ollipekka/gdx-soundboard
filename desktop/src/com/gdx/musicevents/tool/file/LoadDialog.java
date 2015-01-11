package com.gdx.musicevents.tool.file;

import java.util.Comparator;

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
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Array;

public class LoadDialog extends Dialog {

    final FileHandle baseDir;
    final FileInfoPanel fileInfoPanel;
    final Label dir;
    final List<FileListItem> dirList ;
    
    FileHandle currentDir;
    protected FileHandle result;
    
    
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
    
    public LoadDialog(String title, Skin skin, FileHandle baseDir) {
        super(title, skin);
        this.baseDir = baseDir;
        
        final Table content = getContentTable();
        content.top().left();
        Table explorer = new Table(skin);
        
        dir = new Label("", skin);
        dir.setAlignment(Align.left);
        explorer.add(dir).top().left().expandX().fillX().row();
        
        dirList = new List<FileListItem>(skin);
        dirList.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                FileListItem selected = dirList.getSelected();
                if(!selected.file.isDirectory()) {
                    result = selected.file;
                }
            }
        });
        
        dirList.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                FileListItem selected = dirList.getSelected();
                if(selected.file.isDirectory()){
                    changeDirectory(selected.file);
                }
            }
        });
        dirList.getSelection().setProgrammaticChangeEvents(false);
        
        explorer.add(new ScrollPane(dirList, skin)).maxWidth(300).fill().expand().row();

        content.add(explorer).maxWidth(300).left().top().fillY().expandY();
        
        fileInfoPanel = new FileInfoPanel(skin);
        content.add(new ScrollPane(fileInfoPanel)).minWidth(300).left().fill().expand();
        
        button("Ok", true);
        button("Cancel", false);
        key(Keys.ENTER, true);
        key(Keys.ESCAPE, false);
    }
    
    @Override
    public Dialog show(Stage stage, Action action) {
        changeDirectory(baseDir);
        return super.show(stage, action);
    }
    
    
    private void changeDirectory(FileHandle directory){
        
        currentDir = directory;
        dir.setText(currentDir.path());
        
        Array<FileListItem> items = new Array<FileListItem>();

        
        FileHandle[] list = directory.list();
        for(FileHandle handle : list){
            items.add(new FileListItem(handle));
        }

        

        items.sort(dirListComparator);
        if(directory.file().getParentFile() != null){
            items.insert(0, new FileListItem("..", directory.parent()));
        }
        
        dirList.setItems(items);
    }

    public FileHandle getResult() {
        return result;
    }

}
