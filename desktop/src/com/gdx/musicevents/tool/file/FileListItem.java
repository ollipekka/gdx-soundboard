package com.gdx.musicevents.tool.file;

import com.badlogic.gdx.files.FileHandle;

public class FileListItem {
    final FileHandle file;
    
    final String name;
    
    public FileListItem(FileHandle file) {
        this(file.name(), file);
    }
    
    public FileListItem(String name, FileHandle file){
        if(file.isDirectory()){
            name += "/";
        }
        this.name = name;
        this.file = file;
    }
    
    @Override
    public String toString() {
        return name;
    }
}