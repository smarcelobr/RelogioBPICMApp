package br.com.bpicm.relogio.events;

import java.util.List;

public interface IWifiAPListEventListener extends IStatusRelogioEventListener {

    void updated(List<String> aps);

}
