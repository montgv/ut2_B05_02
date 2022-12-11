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

public class TheEndScreen extends ScreenAdapter {
    OreDefender game;
    int score;
    private Stage stage;
    public TheEndScreen(OreDefender game, int score) {
        stage = new Stage();
        this.score = score;
        this.game = game;
        Label over = new Label("GAME OVER", game.gameSkin, "big");
        over.setColor(Color.BLACK);
        over.setAlignment(Align.center);
        over.setY(Gdx.graphics.getHeight() * 4 / 5);
        over.setWidth(Gdx.graphics.getWidth());
        stage.addActor(over);

        Label points = new Label("Score: " + score, game.gameSkin, "big");
        points.setColor(Color.BLACK);
        points.setAlignment(Align.center);
        points.setY(Gdx.graphics.getHeight() * 3 / 5);
        points.setWidth(Gdx.graphics.getWidth());
        stage.addActor(points);

        TextButton facilButton = new TextButton("Menu Principal", game.gameSkin, "default");
        facilButton.setWidth(Gdx.graphics.getWidth()/1.5f);
        facilButton.setPosition(
                Gdx.graphics.getWidth() / 2 - facilButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 3.05f - facilButton.getHeight() / 2
        );
        facilButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MenuScreen(game));
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(facilButton);
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

}
