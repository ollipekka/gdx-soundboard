package com.gdx.musicevents.tool;

import java.io.File;
import java.io.FileFilter;
import java.text.Collator;
import java.util.Comparator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gdx.musicevents.MusicState;
import com.gdx.musicevents.Track;
import com.gdx.musicevents.tool.file.FileChooser;
import com.gdx.musicevents.tool.file.FileChooser.ResultListener;

public class TrackListPanel extends Table {
    

    
    private final Comparator<Track> comparator = new Comparator<Track>() {
        @Override
        public int compare(Track o1, Track o2) {

            Collator comp = java.text.Collator.getInstance();
            return comp.compare(o1.toString(), o2.toString());
        }
    };
    
    private MusicState state;

    private Button removeTrack;

    private Button addTrack;

    private List<Track> trackList;
    
    public TrackListPanel(final Skin skin, final Stage stage, final MusicStatePanel eventDetailsPanel) {
        super(skin);

        setBackground(skin.getDrawable("panel-background"));
        
        this.top().left();
        this.defaults().top().left().pad(2);
        
        final Label tracksLabel = new Label("Tracks", skin, "title");
        this.add(tracksLabel).fillX().expandX().colspan(2).row();

        trackList = new List<Track>(skin);
        this.add(new ScrollPane(trackList)).fill().expand().colspan(2).row();
        
        addTrack = new TextButton("Add", skin);
        addTrack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!addTrack.isChecked()){
                    return;
                }
                
                final String basePath = eventDetailsPanel.getManager().getBasePath();
                
                FileChooser dialog = FileChooser.createPickDialog("Add track", skin, Gdx.files.internal(basePath));
                dialog.setResultListener(new ResultListener() {
                    @Override
                    public boolean result(boolean success, FileHandle result) {
                        if(success){
                            Track track = new Track(result.name());
                            track.init(basePath, state);
                            state.addTrack(track);
                        }
                        eventDetailsPanel.show(state);
                        return true;
                    }
                });
                dialog.disableDirectoryBrowsing();
                dialog.setOkButtonText("Add");
                dialog.setFilter(new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        String path = pathname.getPath();
                        return path.matches(".*(?:ogg|mp3|wav)");
                    }
                });
                dialog.show(stage);
                
                addTrack.setChecked(false);
            }
        });
        
        this.add(addTrack).fillX().expandX();
        
        removeTrack = new TextButton("Remove", skin);
        removeTrack.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!removeTrack.isChecked()){
                    return;
                }
                
                Track track = trackList.getSelected();
                state.removeTrack(track);
                eventDetailsPanel.show(state);
                
                removeTrack.setChecked(false);
            }
        });
        
        this.add(removeTrack).fillX().expandX();
        
        trackList.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                enableDisable(trackList, addTrack, removeTrack);
            }
        });
    }
    
    private void updateTrackList(List<Track> trackList, final MusicState state) {
        trackList.getItems().clear();
        trackList.setItems(state.getTracks());
        trackList.getItems().sort(comparator);
    }
    
    private void enableDisable(List<Track> trackList, Button add, Button remove){
        add.setDisabled(state == null);
        remove.setDisabled(trackList.getSelected() == null || trackList.getItems().size == 0);
    }

    public void setMusicState(MusicState state) {
        this.state = state;
        updateTrackList(trackList, state);
        enableDisable(trackList, addTrack, removeTrack);
    }
}
