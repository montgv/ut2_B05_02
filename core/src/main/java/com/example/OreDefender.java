package com.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class OreDefender extends Game {

	BitmapFont font;
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	public Skin gameSkin;


	@Override
	public void create() {
		font = new BitmapFont();
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		gameSkin = new Skin(Gdx.files.internal("comic/skin/comic-ui.json"));
		setScreen(new MenuScreen(this));
	}

	@Override
	public void dispose() {
		font.dispose();
		batch.dispose();
	}
}