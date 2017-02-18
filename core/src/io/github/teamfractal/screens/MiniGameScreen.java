package io.github.teamfractal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.actors.MinigameScreenActors;

public class MiniGameScreen implements Screen {
    private final RoboticonQuest game;
    private final Stage stage;
    private final Table table;
    private final MinigameScreenActors actors;

    private final ResourceMarketScreen RMS;

    public MiniGameScreen(final RoboticonQuest game, ResourceMarketScreen RMS) {
        this.game = game;
        this.RMS = RMS;
        this.stage = new Stage(new ScreenViewport());
        this.table = new Table();
        table.setFillParent(true);

        actors = new MinigameScreenActors(game, this);
        table.center().add(actors); // positions actors

        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        actors.screenResize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {


    }

    @Override
    public void hide() {


    }

    @Override
    public void dispose() {
        stage.dispose();

    }

    public Stage getStage() {
        return this.stage;
    }

    public ResourceMarketScreen getRMS() {
        return RMS;
    }
}
