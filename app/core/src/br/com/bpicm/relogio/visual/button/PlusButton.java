package br.com.bpicm.relogio.visual.button;

import br.com.bpicm.relogio.RelogioApp;
import br.com.bpicm.relogio.visual.Desenhador;
import br.com.bpicm.relogio.visual.OnAction;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;

public class PlusButton extends BaseButton {

    protected final RelogioApp app;

    public PlusButton(RelogioApp app, float x, float y, Camera camera) {
        super(app.atlas.findRegion("buttons/plus_button"), camera);
        this.app = app;
        this.setPosition(x, y);
    }

    @Override
    public void onAction() {
        this.app.service.incrementaDifMinutos();
    }

}
