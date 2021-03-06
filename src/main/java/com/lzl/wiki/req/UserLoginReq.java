package com.lzl.wiki.req;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UserLoginReq {
    @NotEmpty(message = "【用户名】不能为空")
    private String loginName;

    @NotEmpty(message = "【密码】不能为空")
    @Length(min=6,max = 32,message = "【密码】6-32位")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,32}$",message
            = "【密码】规则不正确")
    private String password;

    public String getLoginName() {
        return loginName;
    }

    @Override
    public String toString() {
        return "UserLoginReq{" +
                "loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
}