package com.lifter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class MainClass extends Game implements ApplicationListener {

	private Drawer drawer;
	private SpriteBatch batch;
	private TextureAtlas atlas;
	private Music theme;

	@Override
	public void create() {
		theme = Gdx.audio.newMusic(Gdx.files.internal("Elevator.mp3"));

		theme.setLooping(true);
		theme.play();

		atlas = new TextureAtlas("atlas");
		batch = new SpriteBatch();
		drawer = new Drawer(atlas);
		this.setScreen(new GameScreen(this));
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}