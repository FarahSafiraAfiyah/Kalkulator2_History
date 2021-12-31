package com.example.kalkulator2_history;

import java.io.Serializable;

public class Nilai implements Serializable {
    private Float Angka1;
    private Float Angka2;
    private String Operator;
    private Float Hasil;

    public Nilai() {
    }

    public Nilai(Float angka1, Float angka2, String operator, Float hasil) {
        this.Angka1 = angka1;
        this.Angka2 = angka2;
        this.Operator = operator;
        this.Hasil = hasil;
    }

    public Float getAngka1() {
        return this.Angka1;
    }

    public Float getAngka2() {
        return this.Angka2;
    }

    public String getOperator() {
        return this.Operator;
    }

    public Float getHasil() {
        return this.Hasil;
    }
}
