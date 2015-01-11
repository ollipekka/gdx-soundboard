package com.gdx.musicevents.tool;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gdx.musicevents.MusicEventManager;
import com.gdx.musicevents.MusicState;

public class MusicEventTool implements ApplicationListener {

    MusicState shownState;

    Skin skin;

    private Stage stage;
    private Table content;

    private boolean debug = false;

    StateInfoPanel stateInfoPanel;
    StateListPanel stateListPanel;
    MusicStatePanel eventDetailsPanel;

    private final MusicEventManager eventManager = new MusicEventManager();

    public MusicEventTool() {
    }

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        stage = new Stage(new ScreenViewport());

        content = new Table(skin);

        stage.addActor(content);
        content.setFillParent(true);
        content.defaults().top().left();

        stateListPanel = new StateListPanel(skin, this);
        content.add(stateListPanel).minWidth(200).expandY().fillY();

        Table main = new Table(skin);
        eventDetailsPanel = new MusicStatePanel(skin, this);
        main.add(eventDetailsPanel).fill().expand().row();
        
        stateInfoPanel = new StateInfoPanel(skin, stage, getEventManager());
        main.add(stateInfoPanel).bottom().fillX().expandX();
        
        content.add(main).fill().expand().minWidth(580);
        

        Gdx.input.setInputProcessor(stage);
        new MenuDialog(skin, stage, eventManager).show(stage);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {

        getEventManager().update(Gdx.graphics.getRawDeltaTime());

        Gdx.gl.glClearColor(0.35f, 0.35f, 0.45f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        stage.setDebugAll(debug);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }


    public Stage getStage() {
        return stage;
    }

    public void showEvent(MusicState musicEvent) {
        this.shownState = musicEvent;
        stateInfoPanel.show(musicEvent);
        eventDetailsPanel.show(musicEvent);
    }

    public MusicEventManager getEventManager() {
        return eventManager;
    }
}
