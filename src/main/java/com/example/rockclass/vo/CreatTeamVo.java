package com.example.rockclass.vo;

import java.util.List;

public class CreatTeamVo {
    private String name;
    private Long courseId;
    private Long classId;
    private MemberVo leader;
    private List<Long> members;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public MemberVo getLeader() {
        return leader;
    }

    public void setLeader(MemberVo leader) {
        this.leader = leader;
    }

    public List<Long> getMembers() {
        return members;
    }

    public void setMembers(List<Long> members) {
        this.members = members;
    }
}
