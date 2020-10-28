package br.com.bpicm.relogio.dto;

public class EncoderStatusDTO {
    private String time;
    private Long difMinutos;
    private Boolean savePendent;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getDifMinutos() {
        return difMinutos;
    }

    public void setDifMinutos(Long difMinutos) {
        this.difMinutos = difMinutos;
    }

    public Boolean getSavePendent() {
        return savePendent;
    }

    public void setSavePendent(Boolean savePendent) {
        this.savePendent = savePendent;
    }
}
