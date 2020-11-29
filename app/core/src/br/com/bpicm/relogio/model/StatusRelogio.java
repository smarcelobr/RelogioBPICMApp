package br.com.bpicm.relogio.model;

import br.com.bpicm.relogio.model.StatusConexaoEnum;

import java.util.ArrayList;

/**
 * Contem o status do relógio para ser usado durante a renderização.
 *
 * É atualizado pela thread que faz a conexão com a rede.
 *
 * Os métodos são thread-safe.
 */
public class StatusRelogio {

    private StatusConexaoEnum statusConexao = StatusConexaoEnum.NAO_CONECTADO;
    private Long ultimaAtualizacaoMillis = System.currentTimeMillis();
    private String horaPonteiro = "--:--";
    private String horaRtc = "SNTP connecting...";
    private String mensagem;
    private ArrayList<String> aps;

    public synchronized StatusConexaoEnum getStatusConexao() {
        return statusConexao;
    }

    public synchronized void setStatusConexao(StatusConexaoEnum statusConexao) {
        this.statusConexao = statusConexao;
    }

    public synchronized Long getUltimaAtualizacaoMillis() {
        return ultimaAtualizacaoMillis;
    }

    public synchronized void setUltimaAtualizacaoMillis(Long ultimaAtualizacaoMillis) {
        this.ultimaAtualizacaoMillis = ultimaAtualizacaoMillis;
    }

    public synchronized String getHoraPonteiro() {
        return horaPonteiro;
    }

    public synchronized void setHoraPonteiro(String horaPonteiro) {
        this.horaPonteiro = horaPonteiro;
    }

    public synchronized String getHoraRtc() {
        return horaRtc;
    }

    public synchronized void setHoraRtc(String rtc) {
         this.horaRtc = rtc;
    }

    public synchronized void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public synchronized String getMensagem() {
        return mensagem;
    }

    public synchronized void setAps(ArrayList<String> aps) {
        this.aps = aps;
    }

    public synchronized ArrayList<String> getAps() {
        return aps;
    }
}
