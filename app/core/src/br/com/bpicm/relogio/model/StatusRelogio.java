package br.com.bpicm.relogio.model;

import br.com.bpicm.relogio.model.StatusConexaoEnum;

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

    public StatusConexaoEnum getStatusConexao() {
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

    public String getHoraRtc() {
        return horaRtc;
    }

    public synchronized void setHoraRtc(String rtc) {
         this.horaRtc = rtc;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
