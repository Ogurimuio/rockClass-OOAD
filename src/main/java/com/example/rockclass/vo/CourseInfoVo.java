package com.example.rockclass.vo;

public class CourseInfoVo {
    Long id;
    boolean isShareTeam;
    boolean isShareSeminar;
    String name;
    String className;
    Long classId;
    String teacherName;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isShareTeam() {
        return isShareTeam;
    }

    public void setShareTeam(boolean shareTeam) {
        isShareTeam = shareTeam;
    }

    public boolean isShareSeminar() {
        return isShareSeminar;
    }

    public void setShareSeminar(boolean shareSeminar) {
        isShareSeminar = shareSeminar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }


}
