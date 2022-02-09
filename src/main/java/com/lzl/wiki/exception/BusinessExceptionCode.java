package com.lzl.wiki.exception;

/**
 * <h3>wiki</h3>
 * <p>异常枚举</p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 * @date : 2022-02-08 18:59
 **/
public enum BusinessExceptionCode {
    USER_LOGIN_NAME_EXIST("登录名已存在"),
    LOGIN_USER_ERROR("用户名不存在或密码错误"),
    ;
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    BusinessExceptionCode(String desc) {
        this.desc = desc;
    }
}
