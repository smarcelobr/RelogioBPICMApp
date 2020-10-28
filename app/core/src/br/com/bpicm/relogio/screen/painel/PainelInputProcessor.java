package br.com.bpicm.relogio.screen.painel;

import br.com.bpicm.relogio.screen.settings.SettingsScreen;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;

public class PainelInputProcessor extends InputAdapter {

    private final PainelScreen screen;
    private final Vector3 touchPoint;

    public PainelInputProcessor(PainelScreen app) {
        this.screen = app;
        touchPoint = new Vector3();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode==Input.Keys.L) {
            this.screen.app.service.acendeLed();
            return true;
        } else
        if (keycode==Input.Keys.O) {
            this.screen.app.service.apagaLed();
            return true;
        } else
        if (keycode==Input.Keys.PLUS) {
            this.screen.app.service.incrementaDifMinutos();
            return true;
        } else
        if (keycode==Input.Keys.MINUS) {
            this.screen.app.service.decrementDifMinutos();
            return true;
        } else
        if (keycode==Input.Keys.S) {
            this.screen.app.service.saveDifMinutos();
            return true;
        } else
        if (keycode==Input.Keys.C) {
            this.screen.app.setScreen(new SettingsScreen(this.screen.app));
            this.screen.dispose();
            return true;
        } else {
            return super.keyDown(keycode);
        }

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        this.screen.cam.unproject(touchPoint.set(screenX, screenY, 0));
        if (this.screen.settingsButton.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)) {
            // settings button Clicked
            this.screen.app.setScreen(new SettingsScreen(this.screen.app));
            this.screen.dispose();
            return true;
        }
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
