package br.com.bpicm.relogio.screen.settings;

import br.com.bpicm.relogio.RelogioApp;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SettingsScreen extends ScreenAdapter {

    public static final float VIEWPORT_WIDTH = 600.0f;
    public static final float WORLD_WIDTH = 600;
    public static final float WORLD_HEIGHT = WORLD_WIDTH * 800 / 600;

    private final OrthographicCamera cam;
    public final RelogioApp app;
    private final SettingsInputProcessor inputProcessor;

    public SettingsScreen(RelogioApp app) {
        this.app = app;
        this.inputProcessor = new SettingsInputProcessor(this);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        this.cam = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_WIDTH * (h / w));
        this.cam.zoom = 1f;
        this.cam.position.set(300, 400, 0);
        this.cam.update();


    }

    @Override
    public void render(float delta) {
        cam.update();
        app.batch.setProjectionMatrix(cam.combined);

        Gdx.gl.glClearColor(0f, 1f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render(delta);
    }

    @Override
    public void dispose() {

        super.dispose();
    }
}
