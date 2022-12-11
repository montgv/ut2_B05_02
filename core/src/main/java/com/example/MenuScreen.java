package com.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuScreen extends ScreenAdapter {
    private final OreDefender game;
    private Stage stage;
    public MenuScreen(OreDefender game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Label title = new Label("Ore Defender", game.gameSkin, "title");
        title.setColor(Color.BLACK);
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight() * 4 / 5);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);

        TextButton facilButton = new TextButton("Facil", game.gameSkin);
        facilButton.setWidth(Gdx.graphics.getWidth() / 2);
        facilButton.setPosition(
                Gdx.graphics.getWidth() / 2 - facilButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 1.5f - facilButton.getHeight() / 2
        );
        facilButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game, 0));
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(facilButton);

        TextButton normalButton = new TextButton("Normal", game.gameSkin);
        normalButton.setWidth(Gdx.graphics.getWidth() / 2);
        normalButton.setPosition(
                Gdx.graphics.getWidth() / 2 - normalButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 2.05f - normalButton.getHeight() / 4
        );
        normalButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game, 1));
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(normalButton);


        TextButton dificilButton = new TextButton("Difacil", game.gameSkin);
        dificilButton.setWidth(Gdx.graphics.getWidth() / 2);
        dificilButton.setPosition(
                Gdx.graphics.getWidth() / 2 - dificilButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 3.05f - dificilButton.getHeight() / 6
        );
        dificilButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game, 2));
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(dificilButton);

        TextButton exitButton = new TextButton("Salir", game.gameSkin);
        exitButton.setWidth(Gdx.graphics.getWidth() / 2);
        exitButton.setPosition(
                Gdx.graphics.getWidth() / 2 - exitButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 6.05f - exitButton.getHeight() / 8
        );
        exitButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(exitButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}