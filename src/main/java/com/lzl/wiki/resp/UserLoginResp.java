package com.lzl.wiki.resp;

public class UserLoginResp {
    private Long id;

    private String loginName;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @Override
    public String toString() {
        return "UserLoginResp{" +
                "id=" + id +
                ", loginName='" + loginName + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}