package br.com.bpicm.relogio.events;

public interface IRelogioEventHandler<T extends IStatusRelogioEventListener> {

    void handle(T listener);
}
