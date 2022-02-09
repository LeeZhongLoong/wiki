package com.lzl.wiki.controller;

import com.lzl.wiki.req.UserLoginReq;
import com.lzl.wiki.req.UserQueryReq;
import com.lzl.wiki.req.UserResetPasswordReq;
import com.lzl.wiki.req.UserSaveReq;
import com.lzl.wiki.resp.CommonResp;
import com.lzl.wiki.resp.PageResp;
import com.lzl.wiki.resp.UserLoginResp;
import com.lzl.wiki.resp.UserQueryResp;
import com.lzl.wiki.service.impl.UserServiceImpl;
import com.lzl.wiki.utils.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

/**
 * <h3>wiki</h3>
 * <p>HelloWorld</p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 * @date : 2022-01-08 08:37
 **/
//返回的是一个字符串
//@Controller
//    返回json格式字符串
@RestController
@RequestMapping("/user")
public class UserController {

    //    日志放入类
        private static final Logger LOG= LoggerFactory.getLogger(UserController.class);
//    引入service
    @Resource
    private UserServiceImpl userService;

    @Resource
    private RedisTemplate<Long, UserLoginResp> redisTemplate;

    //    添加时间戳
    @Resource
    private SnowFlake snowFlake;


    /**
     * 根据名字模糊查询
     * @param req
     * @return
     */
    @GetMapping("/list")
//    开启参数校验 @Valid
    public CommonResp<PageResp<UserQueryResp>> list(@Valid UserQueryReq req){
//        创建一个统一返回值的类型
        CommonResp<PageResp<UserQueryResp>> resp = new CommonResp<>();
//        获取数据库汇总的User的记录
        PageResp<UserQueryResp> list=userService.list(req);
//        放入到泛型类中
        resp.setContent(list);
        return resp;
    }

//    保存使用post方法

    /**
     * 修改值并保存的方法
     * @param req
     * @return
     */
    @PostMapping("/save")
//    @RequestBody 请求参数是json格式，@ResponseBody是返回参数是json格式
//    @Valid参数校验
    public CommonResp save(@Valid @RequestBody UserSaveReq req){
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp resp=new CommonResp();
        userService.save(req);
        return resp;
    }



    /**
     * 删除的方法
     * @param id 根据id删除
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public CommonResp del(@PathVariable Long id){
        CommonResp resp=new CommonResp();
        userService.del(id);
        return resp;
    }

    /**
     *重置密码的方法
     * @param req
     * @return
     */
    @PostMapping("/reset-password")
//    @RequestBody 请求参数是json格式，@ResponseBody是返回参数是json格式
//    @Valid参数校验
    public CommonResp resetPassword(@Valid @RequestBody UserResetPasswordReq req){
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp resp=new CommonResp();
        userService.resetPassword(req);
        return resp;
    }

    /**
     *重置密码的方法
     * @param req
     * @return
     */
    @PostMapping("/login")
//    @RequestBody 请求参数是json格式，@ResponseBody是返回参数是json格式
//    @Valid参数校验
    public CommonResp<UserLoginResp> login(@Valid @RequestBody UserLoginReq req){
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp<UserLoginResp> resp=new CommonResp<>();
        UserLoginResp userLoginResp=userService.login(req);
//        通过雪花算法添加token,单点登录
        Long token = snowFlake.nextId();
        LOG.info("生成单点登录token{}，并放入到redis中",token);
//        将token放入返回对象中
        userLoginResp.setToken(token.toString());
//        将token放入到redis中3600ms=一小时
//        将一个类放入到redis中要序列化1、在类上implements Serializable 2、如下转为json格式JSONObject.toJSONString(userLoginResp)
        redisTemplate.opsForValue().set(token, userLoginResp,3600*24, TimeUnit.SECONDS);
        resp.setContent(userLoginResp);
        return resp;
    }
}
