package com.example.rockclass.entity;

public class MemberLimitStrategy {
    private Long id;

    private Byte minMember;

    private Byte maxMember;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getMinMember() {
        return minMember;
    }

    public void setMinMember(Byte minMember) {
        this.minMember = minMember;
    }

    public Byte getMaxMember() {
        return maxMember;
    }

    public void setMaxMember(Byte maxMember) {
        this.maxMember = maxMember;
    }
}