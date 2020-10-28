package br.com.bpicm.relogio.dto;

public class RtcStatusDTO {
    private String time;
    private Long difTimezone;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getDifTimezone() {
        return difTimezone;
    }

    public void setDifTimezone(Long difTimezone) {
        this.difTimezone = difTimezone;
    }
}
