package br.com.bpicm.relogio.dto;

import java.util.Objects;

public class RelogioStatusDTO {
    private RtcStatusDTO rtc;
    private Boolean falhaMotor;
    private EncoderStatusDTO ptr;
    private LedStatusDTO led;

    public RtcStatusDTO getRtc() {
        return rtc;
    }

    public void setRtc(RtcStatusDTO rtc) {
        this.rtc = rtc;
    }

    public Boolean getFalhaMotor() {
        return falhaMotor;
    }

    public void setFalhaMotor(Boolean falhaMotor) {
        this.falhaMotor = falhaMotor;
    }

    public EncoderStatusDTO getPtr() {
        return ptr;
    }

    public void setPtr(EncoderStatusDTO ptr) {
        this.ptr = ptr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelogioStatusDTO that = (RelogioStatusDTO) o;
        return Objects.equals(rtc, that.rtc) &&
                Objects.equals(falhaMotor, that.falhaMotor) &&
                Objects.equals(ptr, that.ptr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rtc, falhaMotor, ptr);
    }

    @Override
    public String toString() {
        return "RelogioStatusDTO{" +
                "rtc='" + rtc + '\'' +
                ", falhaMotor=" + falhaMotor +
                ", ptr='" + ptr + '\'' +
                '}';
    }
}
