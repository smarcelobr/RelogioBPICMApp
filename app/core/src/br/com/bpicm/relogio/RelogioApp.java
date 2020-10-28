package br.com.bpicm.relogio;

import br.com.bpicm.relogio.screen.painel.PainelScreen;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class RelogioApp extends Game {
	public SpriteBatch batch;
	public RelogioService service;
	public TextureAtlas atlas;

	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		this.atlas = new TextureAtlas(Gdx.files.internal("packed/atlas.atlas"));
		this.service = new RelogioService();

		batch = new SpriteBatch();
		this.setScreen(new PainelScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose () {
		this.screen.dispose();
		this.batch.dispose();
		this.service.dispose();
		this.atlas.dispose();
	}

}
