package br.com.bpicm.relogio.visual.button;

import br.com.bpicm.relogio.RelogioApp;
import com.badlogic.gdx.graphics.Camera;

public class MinusButton extends BaseButton {

    protected final RelogioApp app;

    public MinusButton(RelogioApp app, float x, float y, Camera camera) {
        super(app.atlas.findRegion("buttons/minus_button"), camera);
        this.app = app;
        this.setPosition(x, y);
    }

    @Override
    public void onAction() {
        this.app.service.decrementDifMinutos();
    }

}
