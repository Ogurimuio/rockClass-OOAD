package com.example.rockclass.vo;

import java.util.List;

public class TeamVo {

    private Long id;
    private String name;

    private MemberVo leader;
    private List<MemberVo> members;

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

    public MemberVo getLeader() {
        return leader;
    }

    public void setLeader(MemberVo leader) {
        this.leader = leader;
    }

    public List<MemberVo> getMembers() {
        return members;
    }

    public void setMembers(List<MemberVo> members) {
        this.members = members;
    }
}
