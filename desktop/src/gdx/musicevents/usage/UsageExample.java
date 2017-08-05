package gdx.musicevents.usage;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gdx.musicevents.MusicEventListener;
import com.gdx.musicevents.MusicEventManager;
import com.gdx.musicevents.MusicState;

public class UsageExample extends ApplicationAdapter {
    
    private final MusicEventManager eventManager = new MusicEventManager();
    private Skin skin;
    private Stage stage;

    public enum GameEvent{
        RISING,
        CALM
    }
    @Override
    public void create() {

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage(new ScreenViewport());
        
        final Dialog dialog = new Dialog("Events",skin);
        
        Table contentTable = dialog.getContentTable();

        TextButton rising = new TextButton("RISING", skin);
        contentTable.add(rising).fill().expand().row();
        rising.addListener(new ChangeListener() {
            
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                eventManager.play(GameEvent.RISING);
            }
        });
        
        TextButton calm = new TextButton("CALM", skin);
        contentTable.add(calm).fill().expand().row();
        calm.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                eventManager.play(GameEvent.CALM);
            }
        });
        
        TextButton action = new TextButton("COMBAT", skin);
        action.addListener(new ChangeListener() {
            
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                eventManager.play("COMBAT");
            }
        });
        
        contentTable.add(action).fill().expand().row();
        
        Gdx.input.setInputProcessor(stage);
        
        dialog.show(stage);
        
        eventManager.load(Gdx.files.internal("music/music-events.json"));

        eventManager.addListener(new MusicEventListener() {
            @Override
            public void stateAdded(MusicState state) {

            }

            @Override
            public void stateRemoved(MusicState state) {

            }

            @Override
            public void stateChanged(MusicState nextState, MusicState previewsState) {
                if(nextState != null) {
                    Gdx.app.log("stateChanged:next", nextState.getName());
                }

                if(previewsState != null) {
                    Gdx.app.log("stateChanged:previews", previewsState.getName());
                }
            }
        });
    }
    
    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {

        eventManager.update(Gdx.graphics.getRawDeltaTime());

        Gdx.gl.glClearColor(0.35f, 0.35f, 0.45f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

}
