package com.gdx.musicevents.tool;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gdx.musicevents.MusicEvent;
import com.gdx.musicevents.MusicEventManager;

public class MusicEventTool implements ApplicationListener {

    MusicEvent shownMusicEvent;

    Skin skin;

    private Stage stage;
    private Table content;

    private boolean debug = false;

    InfoPanel infoPanel;

    EventListPanel eventListPanel;
    EventDetailsPanel eventDetailsPanel;


    MusicEventManager eventManager = new MusicEventManager();


    public MusicEventTool() {
    }

    @Override
    public void create() {
        skin = new Skin(Gdx.files.classpath("uiskin.json"));

        TextButton.TextButtonStyle buttonStyle = skin.get(TextButton.TextButtonStyle.class);
        buttonStyle.disabled = skin.newDrawable("default-round", Color.DARK_GRAY);
        buttonStyle.disabledFontColor = Color.GRAY;


        stage = new Stage(new ScreenViewport());

        content = new Table(skin);
        content.pad(10);

        stage.addActor(content);
        content.setFillParent(true);
        content.defaults().top().left();

        infoPanel = new InfoPanel(skin);

        content.add(infoPanel).colspan(2).center().fillX().expandX().row();

        Table actionsPanel = new ActionsPanel(skin, eventManager);
        content.add(actionsPanel).colspan(2).expandX().fillX().row();

        eventListPanel = new EventListPanel(skin, this);
        content.add(eventListPanel).minWidth(240).expandY().fillY();

        eventDetailsPanel = new EventDetailsPanel(skin, this);
        content.add(eventDetailsPanel).minWidth(540).fill().expand();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {

        eventManager.update(Gdx.graphics.getRawDeltaTime());

        infoPanel.show(eventManager.getCurrentEvent());

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
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

    public void showEvent(MusicEvent musicEvent) {
        this.shownMusicEvent = musicEvent;
        eventDetailsPanel.show(musicEvent);
    }

}
