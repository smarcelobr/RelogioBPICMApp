package br.com.bpicm.relogio.screen.settings;

import br.com.bpicm.relogio.RelogioApp;
import br.com.bpicm.relogio.model.StatusRelogio;
import br.com.bpicm.relogio.screen.painel.PainelScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SettingsScreen extends ScreenAdapter {

    public static final float VIEWPORT_WIDTH = 600.0f;
    public static final float WORLD_WIDTH = 600;
    public static final float WORLD_HEIGHT = WORLD_WIDTH * 800 / 600;

    private final Stage stage;

    public final RelogioApp app;
    private final Label ptrLabel;
    private final Sprite img;

    public SettingsScreen(final RelogioApp app) {
        this.app = app;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Skin skin = this.app.skin;

        img = new Sprite(new Texture("mainBackground.jpg"));
        img.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        img.setPosition(0, 0);
        Image background = new Image(img);
        background.setPosition(0,0);
        background.setZIndex(0);
        stage.addActor(background);

        Table table = new Table();
        /* linha da tabela */

        ptrLabel = new Label("Ponteiros", skin, "hora-font", "white");
        TextButton plusPtrButton = new TextButton("+", skin);
        TextButton minusPtrButton = new TextButton("-", skin);

        table.add(ptrLabel);
        table.add(plusPtrButton).width(50);
        table.add(minusPtrButton).width(50);

        /* linha da tabela */
        table.row();

        Label addressLabel = new Label("Address:", skin);
        TextField addressText = new TextField("", skin);

        table.add(addressLabel);
        table.add(addressText).width(100);

        /* linha da tabela */
        table.row();

        TextButton salvarButton = new TextButton("Salvar",skin);
        table.add(salvarButton);

        /* encerrando a tabela */
        table.setFillParent(true);
        table.setZIndex(1);
        stage.addActor(table);

        // events handlers
        EventListener plusPtrButtonOnClick = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SettingsScreen.this.app.service.incrementaDifMinutos();
            }
        };

        plusPtrButton.addListener(plusPtrButtonOnClick);

        EventListener minusPtrButtonOnClick = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SettingsScreen.this.app.service.decrementDifMinutos();
            }
        };

        minusPtrButton.addListener(minusPtrButtonOnClick);

        EventListener salvarButtonOnClick = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SettingsScreen.this.app.service.saveDifMinutos();
                SettingsScreen.this.app.setScreen(new PainelScreen(app));
                SettingsScreen.this.dispose();
            }
        };

        salvarButton.addListener(salvarButtonOnClick);


    }

    @Override
    public void render(float delta) {
        StatusRelogio statusRelogio = this.app.service.statusRelogio;

        ptrLabel.setText(statusRelogio.getHoraPonteiro());

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        img.getTexture().dispose();
        super.dispose();
    }
}
