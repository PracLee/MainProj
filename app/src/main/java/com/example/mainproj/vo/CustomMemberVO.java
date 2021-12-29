package com.example.mainproj.vo;

public class CustomMemberVO {
    private String name;
    private String age;

    // Constructor
    public CustomMemberVO(String name, String age) {
        this.name = name;
        this.age = age;
    }



    // Getter Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
