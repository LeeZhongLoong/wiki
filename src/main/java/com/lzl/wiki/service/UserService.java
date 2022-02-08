package com.lzl.wiki.service;

import com.lzl.wiki.domain.User;
import com.lzl.wiki.req.UserQueryReq;
import com.lzl.wiki.req.UserSaveReq;
import com.lzl.wiki.resp.PageResp;
import com.lzl.wiki.resp.UserQueryResp;

/**
 * <h3>wiki</h3>
 * <p>测试类的Service</p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 **/
public interface UserService {
//    查询的方法
    public PageResp<UserQueryResp> list(UserQueryReq req);
//    保存的方法
    public void save(UserSaveReq req);
//    删除的方法
    public void del(Long id);
//    根据用户名查找
    public User selectByLoginName(String loginName);
}
