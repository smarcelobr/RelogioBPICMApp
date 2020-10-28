package br.com.bpicm.relogio.screen.settings;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class SettingsInputProcessor extends InputAdapter {

    private final SettingsScreen screen;

    public SettingsInputProcessor(SettingsScreen screen) {
        this.screen = screen;
    }

    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }

}
