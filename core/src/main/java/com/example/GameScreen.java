package com.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen extends ScreenAdapter {
    private final OreDefender game;
    float statetime;
    Stage stage;
    OrthographicCamera camera;
    Texture texture;
    TextureRegion[] textureRegions;
    private static BitmapFont font;
    private long lastDropTime;
    static Array<Meteorito> meteoritos;
    private int dificultad;
    private int score;
    Batch batch;
    private float n;
    private long second;

    Nave nave;

    public GameScreen(OreDefender game, int dificultad) {
        this.game = game;
        this.dificultad = dificultad;
        score = 0;

        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("spacetheme.png"));
        stage = new Stage();
        textureRegions = new TextureRegion[]{
                new TextureRegion(texture, 2, 250, 59, 53),
                new TextureRegion(texture, 2, 302, 59, 	53),
                new TextureRegion(texture, 60, 250, 46, 40),
                new TextureRegion(texture, 60, 320, 30, 40),
                new TextureRegion(texture, 100, 250, 25, 31),
                new TextureRegion(texture, 95, 320, 29, 26)
        };

        nave = new Nave();

        meteoritos = new Array<>();

        cargarMeteoritos();
        actualizar();

        switch (this.dificultad) {
            case 0:
                n = 1000000000;
                break;
            case 1:
                n = 199999999.9f;
                break;
            case 2:
                n = 150604510f;
                break;
        }

        stage.addActor(nave);
        Gdx.input.setInputProcessor(stage);
        stage.setKeyboardFocus(nave);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        Viewport viewport = new ScreenViewport(camera);
        stage.setViewport(viewport);
        if (font == null) {
            // Cargar fuente solamente si es la primera vez
            font = new BitmapFont();
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        statetime += delta;

        if(TimeUtils.nanoTime() - lastDropTime > n) cargarMeteoritos();

        for (int i = 0; i < meteoritos.size; i++) {

            if (!nave.fin && Intersector.overlaps(nave.getShape(), meteoritos.get(i).getShape())) {
                nave.clearActions();
                statetime = 0f;
                nave.explosion();

            } else if (!nave.getFin()) {

                if(TimeUtils.nanoTime() - second > 1000000000) {
                    actualizar();
                    score += 100;
                }
            }
        }

        if (nave.getEndGame()) {
            game.setScreen(new TheEndScreen(game, score));
        }

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        batch.begin();
        font.draw(batch, "Puntos: " + score, 20f, 460f);
        batch.end();
    }

    private void actualizar() {
        second = TimeUtils.nanoTime();
    }

    private void cargarMeteoritos() {

        Meteorito meteorito = new Meteorito(textureRegions[MathUtils.random(0, 5)], MathUtils.random(100, 300), MathUtils.random(10f, 800f), 500, texture);
        stage.addActor(meteorito);
        meteoritos.add(meteorito);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

}
