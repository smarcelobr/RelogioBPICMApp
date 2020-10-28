package br.com.bpicm.relogio.screen.painel;

import br.com.bpicm.relogio.RelogioApp;
import br.com.bpicm.relogio.visual.Desenhador;
import br.com.bpicm.relogio.visual.button.MinusButton;
import br.com.bpicm.relogio.visual.button.PlusButton;
import br.com.bpicm.relogio.model.StatusRelogio;
import br.com.bpicm.relogio.model.StatusConexaoEnum;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import java.util.*;

public class PainelScreen extends ScreenAdapter {

    public static final float VIEWPORT_WIDTH = 600.0f;
    public static final float WORLD_WIDTH = 600;
    public static final float WORLD_HEIGHT = WORLD_WIDTH * 800 / 600;

    final OrthographicCamera cam;
    public final RelogioApp app;
    private final PainelInputProcessor inputProcessor;

    final Sprite img;
    final Sprite settingsButton;
    final List<Sprite> componentesVisuais;

    final private Map<StatusConexaoEnum, Sprite> statusConexaoMap = new HashMap<>();

    private final BitmapFont horaFont;
    private final BitmapFont defaultFont;
    private final float rotationSpeed = 0.5f;

    private float accTime = 0f; // tempo acumulado

    public PainelScreen(RelogioApp app) {
        this.app = app;

        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        cam = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_WIDTH * (h / w));
        cam.zoom = 1f;
        cam.position.set(300, 400, 0);
        cam.update();

        this.inputProcessor = new PainelInputProcessor(this);

        img = new Sprite(new Texture("mainBackground.jpg"));
        img.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        img.setPosition(0, 0);

        componentesVisuais = new ArrayList<>();
        settingsButton = new Sprite(this.app.atlas.findRegion("buttons/settings_button"));
        settingsButton.setPosition(550, 750);

        final PlusButton plusButton = new PlusButton(this.app, 500, 700, this.cam);
        componentesVisuais.add(plusButton);

        final MinusButton minusButton = new MinusButton(this.app, 500, 600, this.cam);
        componentesVisuais.add(minusButton);

        Sprite redLight = new Sprite(new Texture("red_light.png"));
        Sprite yellowLight = new Sprite(new Texture("yellow_light.png"));
        Sprite greenLight = new Sprite(new Texture("green_light.png"));
        statusConexaoMap.put(StatusConexaoEnum.NAO_CONECTADO, redLight);
        statusConexaoMap.put(StatusConexaoEnum.CONECTANDO, yellowLight);
        statusConexaoMap.put(StatusConexaoEnum.CONECTADO, greenLight);

        Gdx.input.setInputProcessor(new InputMultiplexer(this.inputProcessor,
                plusButton.getInputProcessor(),
                minusButton.getInputProcessor()
        ));

        defaultFont = new BitmapFont(); // arial-15.fnt
        horaFont = new BitmapFont(Gdx.files.internal("fonts/hora.fnt"));

        this.app.batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        accTime += delta;
        StatusRelogio statusRelogio = this.app.service.statusRelogio;

        //handleInput();

        cam.update();
        app.batch.setProjectionMatrix(cam.combined);

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
        float effectiveViewportHeight = cam.viewportHeight * cam.zoom;
        float absoluteX = cam.position.x - (effectiveViewportWidth/2f);
        float absoluteY = cam.position.y - (effectiveViewportHeight/2f);

        app.batch.begin();
        img.draw(app.batch);
        settingsButton.draw(app.batch);
        for (Sprite comp: componentesVisuais) {
            if (comp instanceof Desenhador) {
                ((Desenhador) comp).draw(app.batch);
            }
        }

        horaFont.draw(app.batch, statusRelogio.getHoraPonteiro(), absoluteX+10, absoluteY+100);
        Sprite light = statusConexaoMap.get(statusRelogio.getStatusConexao());
        light.setBounds(10, WORLD_HEIGHT / 2f + 32, 40, 40);
        light.draw(app.batch);

        defaultFont.draw(app.batch, statusRelogio.getHoraRtc(), absoluteX + 10, absoluteY + 120);
//                MathUtils.clamp(absoluteX + 10, 0, WORLD_WIDTH),
//                MathUtils.clamp(absoluteY + 120, 0, WORLD_HEIGHT));

        if (Gdx.app.getLogLevel()== Application.LOG_DEBUG) {
            defaultFont.draw(app.batch, String.format(Locale.ENGLISH, "(%.2f,%.2f) z=%.2f",
                               cam.position.x, cam.position.y, cam.zoom), absoluteX + 10, absoluteY + 20);
//                    MathUtils.clamp(absoluteX + 10, 0, WORLD_WIDTH),
//                    MathUtils.clamp(absoluteY + 20, 0, WORLD_HEIGHT));
        }

        app.batch.end();
    }

    @Override
    public void dispose() {
        img.getTexture().dispose();
        for (Sprite sprite : statusConexaoMap.values()) {
            sprite.getTexture().dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = VIEWPORT_WIDTH;
        cam.viewportHeight = VIEWPORT_WIDTH * height / width;
        cam.update();
    }

    private void handleInput() {

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cam.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            cam.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            cam.translate(-3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            cam.translate(3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            cam.translate(0, -3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            cam.translate(0, 3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            cam.rotate(-rotationSpeed, 0, 0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            cam.rotate(rotationSpeed, 0, 0, 1);
        }

        cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, WORLD_WIDTH / cam.viewportWidth);

        float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
        float effectiveViewportHeight = cam.viewportHeight * cam.zoom;

        cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, WORLD_WIDTH - effectiveViewportWidth / 2f);
        cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, WORLD_HEIGHT - effectiveViewportHeight / 2f);
    }

}
