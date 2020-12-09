package br.com.bpicm.relogio;

import br.com.bpicm.relogio.model.StatusRelogio;
import com.badlogic.gdx.utils.Disposable;

public class RelogioService implements Disposable  {

    private final Thread atualizaStatusRelogioThread;
    private final AtualizaStatusRelogioNetwork atualizaStatusRelogioNetwork;
    public final br.com.bpicm.relogio.model.StatusRelogio statusRelogio;

    public RelogioService() {
        this.statusRelogio = new StatusRelogio();

        atualizaStatusRelogioNetwork = new AtualizaStatusRelogioNetwork(this.statusRelogio, "192.168.0.111");
        atualizaStatusRelogioThread = new Thread(
                atualizaStatusRelogioNetwork);
        atualizaStatusRelogioThread.start();

    }

    @Override
    public void dispose() {
        atualizaStatusRelogioThread.interrupt();
        atualizaStatusRelogioNetwork.dispose();
    }

    public void acendeLed() {
        this.atualizaStatusRelogioNetwork.acendeLed();
    }

    public void apagaLed() {
        this.atualizaStatusRelogioNetwork.apagaLed();
    }

    public void atualizaStatus() {
        this.atualizaStatusRelogioNetwork.status();
    }

    public void incrementaDifMinutos() {
        this.atualizaStatusRelogioNetwork.incrementDifMinutos(1);
    }

    public void decrementDifMinutos() {
        this.atualizaStatusRelogioNetwork.incrementDifMinutos(-1);
    }

    public void saveDifMinutos() {
        this.atualizaStatusRelogioNetwork.saveDifMinutos();
    }

    public void updateWifiList() {
        this.atualizaStatusRelogioNetwork.listAPs();
    }
}
