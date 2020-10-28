package br.com.bpicm.relogio.visual.button;

import br.com.bpicm.relogio.RelogioApp;
import br.com.bpicm.relogio.visual.Desenhador;
import br.com.bpicm.relogio.visual.OnAction;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public abstract class BaseButton extends Sprite implements Desenhador, OnAction {
    protected final PlusButtonInputProcessor inputProcessor;
    public boolean disabled = false;

    public BaseButton(TextureRegion region, Camera camera) {
        super(region);
        this.inputProcessor = new PlusButtonInputProcessor(camera);
    }

    @Override
    public void draw(Batch batch) {
        if (disabled) {
            this.setColor(0.3f, 0.3f, 0.3f, 1);
        } else {
            this.setColor(0f, 0f, 0f, 1);
        }
        super.draw(batch);
    }

    public InputProcessor getInputProcessor() {
        return this.inputProcessor;
    }

    protected class PlusButtonInputProcessor extends InputAdapter {
        private final Vector3 touchPoint = new Vector3();
        private final Camera camera;

        public PlusButtonInputProcessor(Camera camera) {
            this.camera = camera;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            this.camera.unproject(this.touchPoint.set(screenX, screenY, 0));
            if (BaseButton.this.getBoundingRectangle().contains(this.touchPoint.x, this.touchPoint.y)) {
                BaseButton.this.onAction();
                return true;
            }
            return super.touchDown(screenX, screenY, pointer, button);
        }
    }
}
