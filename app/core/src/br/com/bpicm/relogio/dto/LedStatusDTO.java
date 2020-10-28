package br.com.bpicm.relogio.dto;

public class LedStatusDTO {
    private String modo;
    private Integer idx;

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    @Override
    public String toString() {
        return "LedStatusDTO{" +
                "modo='" + modo + '\'' +
                ", idx=" + idx +
                '}';
    }
}
