package com.example.rockclass.vo;

import java.math.BigInteger;

public class LoginSuccessVO {

    private Long id;

    private String account;

    private String role;

    private String name;

    private Byte isActived;

    private String jwt;

    public LoginSuccessVO() {
    }

    public LoginSuccessVO(Long id, String account, String role, String name, Byte isActived, String jwt) {
        this.id = id;
        this.account = account;
        this.role = role;
        this.name = name;
        this.isActived = isActived;
        this.jwt = jwt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public void setJwt(String header, String payload, String signature) {
        this.jwt = header + '.' + payload + '.' + signature;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getIsActived() {
        return isActived;
    }

    public void setIsActived(Byte isActived) {
        this.isActived = isActived;
    }

    @Override
    public String toString() {
        return "LoginSuccessVO{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", role='" + role + '\'' +
                ", name='" + name + '\'' +
                ", isActived=" + isActived +
                ", jwt='" + jwt + '\'' +
                '}';
    }
}
