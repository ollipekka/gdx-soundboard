package com.soundboard;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SoundBoard implements ApplicationListener {


    Event playingEvent;
    Event shownEvent;


    Skin skin;

    private Stage stage;
    private Table content;

    private boolean debug = false;

    InfoPanel infoPanel;

    EventDetailsPanel eventDetailsPanel;

    public SoundBoard() {
    }

    @Override
    public void create() {
        skin = new Skin(Gdx.files.classpath("uiskin.json"));
        stage = new Stage(new ScreenViewport());

        content = new Table(skin);
        content.pad(10);

        stage.addActor(content);
        content.setFillParent(true);
        content.defaults().top().left();

        infoPanel = new InfoPanel(skin);

        content.add(infoPanel).colspan(2).center().fillX().expandX().row();

        Table actionsPanel = new ActionsPanel(skin);
        content.add(actionsPanel).colspan(2).expandX().fillX().row();


        Table eventListPanel = new EventListPanel(skin, this);
        content.add(eventListPanel).minWidth(240).expandY().fillY();
        Gdx.input.setInputProcessor(stage);

        eventDetailsPanel = new EventDetailsPanel(skin, this);
        content.add(eventDetailsPanel).minWidth(540).fill().expand();


        showEvent(null);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {

        if(playingEvent != null){
            playingEvent.update(Gdx.graphics.getRawDeltaTime());
        }

        infoPanel.show(playingEvent);

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

    public void showEvent(Event event) {
        this.shownEvent = event;
        eventDetailsPanel.show(event);
    }

    public Event getPlayingEvent() {
        return playingEvent;
    }

    public void setPlayingEvent(Event playingEvent) {
        this.playingEvent = playingEvent;
    }
}
