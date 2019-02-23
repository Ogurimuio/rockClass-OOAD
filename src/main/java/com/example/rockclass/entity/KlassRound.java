package com.example.rockclass.entity;

public class KlassRound {
    private Klass klass;

    private Round round;

    private Byte enrollNumber;

    public Klass getKlass() {
        return klass;
    }

    public void setKlass(Klass klass) {
        this.klass = klass;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public Byte getEnrollNumber() {
        return enrollNumber;
    }

    public void setEnrollNumber(Byte enrollNumber) {
        this.enrollNumber = enrollNumber;
    }
}