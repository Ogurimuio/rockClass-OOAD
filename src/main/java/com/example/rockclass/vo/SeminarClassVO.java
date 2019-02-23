package com.example.rockclass.vo;

/*讨论课的班级信息*/

public class SeminarClassVO {
    private Long id;
    private String name;

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

    @Override
    public String toString() {
        return "SeminarClassVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
