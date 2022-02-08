
package com.lzl.wiki.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzl.wiki.domain.User;
import com.lzl.wiki.domain.UserExample;
import com.lzl.wiki.mapper.UserMapper;
import com.lzl.wiki.req.UserQueryReq;
import com.lzl.wiki.req.UserSaveReq;
import com.lzl.wiki.resp.UserQueryResp;
import com.lzl.wiki.resp.PageResp;
import com.lzl.wiki.service.UserService;
import com.lzl.wiki.utils.CopyUtil;
import com.lzl.wiki.utils.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <h3>wiki</h3>
 * <p>测试的service实现类</p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 * @date : 2022-01-08 13:46
 **/
@Service
public class UserServiceImpl implements UserService {
//    日志
    private static final Logger LOG= LoggerFactory.getLogger(UserServiceImpl.class);
    //jdk自带
    @Resource
    private UserMapper userMapper;
//    @Autowired spring自带

//    添加时间戳
    @Resource
    private SnowFlake snowFlake;

    @Override
    public PageResp<UserQueryResp> list(UserQueryReq req) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getLoginName())){
            criteria.andLoginNameEqualTo(req.getLoginName());
        }
//        支持分页第一个参数：页码，第二个参数：每一页的条数
//        说明：从1开始，只对第一个sql有作用
        PageHelper.startPage(req.getPage(),req.getSize());
        List<User> userList = userMapper.selectByExample(userExample);
        PageInfo<User> pageInfo=new PageInfo<>(userList);

//        写日志使用占位符写法("{}",xxxx)
        LOG.info("总行数:{}",pageInfo.getTotal());
        LOG.info("总页数:{}",pageInfo.getPages());
//        将userList转化为UserResp
//        List<UserResp> respList=new ArrayList<>();
//        for (User user : userList) {
//            1、为使用工具当个赋值
//            UserResp userResp = new UserResp();
//            手工拷贝太慢
//            userResp.setId(user.getId());
//            使用Bean拷贝工具 第一个变量是要赋给另一个对象的值，第二个变量是需要得到值的变量,user赋值给userResp
//            BeanUtils.copyProperties(user,userResp);

//            2、使用CopyUtil自定义类，单个赋值
//            UserResp userResp = CopyUtil.copy(user, UserResp.class);
//            赋值完后放入到集合中
//            respList.add(userResp);
//        }
//        使用自定义工具类List赋值
        List<UserQueryResp> list = CopyUtil.copyList(userList, UserQueryResp.class);
//        放入到分页中
        PageResp<UserQueryResp> pageResp = new PageResp<>();
//        设置总页数
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
//        返回对象
        return pageResp;
    }

    /**
     * 保存、修改的方法
     * 有Id是更新，无Id是新增
     * @param req
     */
    @Override
    public void save(UserSaveReq req) {
//        给req转型
        User user=CopyUtil.copy(req,User.class);
//        判断是更新还是新增、有Id是更新无Id是新增
        if (ObjectUtils.isEmpty(req.getId())){
//            新增
//            利用雪花算法设置新增的Id,新增id有三种算法(自增，uuid，雪花算法）
            user.setId(snowFlake.nextId());
            userMapper.insert(user);
        }else {
//            更新
//        insert是新增保存修改使用update保存
            userMapper.updateByPrimaryKey(user);
        }

    }

    /**
     * 删除的方法
     * @param id
     */
    @Override
    public void del(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }
}
