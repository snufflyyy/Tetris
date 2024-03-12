package com.snuffly.tetris;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.snuffly.tetris.Screens.MainMenu;

public class Tetris extends Game {
	public SpriteBatch batch;

	public OrthographicCamera camera;
	public ScalingViewport viewport;

	public BitmapFont classicFont;

	public TextureAtlas blockTextures;

	public static final int tileSize = 32;

	public int initScreenWidth;
	public int initScreenHeight;

	public void create () {
		batch = new SpriteBatch();

		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		viewport = new ScalingViewport(Scaling.fit, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

		classicFont = new BitmapFont(Gdx.files.internal("Fonts/ArcadeClassic.fnt"));
		classicFont.getData().setScale(0.5f, 0.5f);

		blockTextures = new TextureAtlas(Gdx.files.internal("Blocks.atlas"));

		initScreenWidth = Gdx.graphics.getWidth();
		initScreenHeight = Gdx.graphics.getHeight();

		this.setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		// camera doesn't get disposed
		// viewport doesn't get disposed
		blockTextures.dispose();
	}
}
