package com.example.rockclass.config;


public class JwtPayload {

    private String account;
    private String role;
    private Long exp;

    public JwtPayload() {
    }

    public JwtPayload(String account, String role, Long exp) {
        this.account = account;
        this.role = role;
        this.exp = exp;
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "JwtPayload{" +
                "account='" + account + '\'' +
                ", role='" + role + '\'' +
                ", exp=" + exp +
                '}';
    }
}
