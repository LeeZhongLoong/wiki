package com.lzl.wiki.req;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UserResetPasswordReq {
    private Long id;

    @NotEmpty(message = "【密码】不能为空")
    @Length(min=6,max = 32,message = "【密码】6-32位")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,32}$",message
            = "【密码】至少包好数字和英文，长度6-32")
    private String password;

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "UserResetPasswordReq{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
}