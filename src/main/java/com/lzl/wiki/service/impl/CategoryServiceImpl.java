package com.lzl.wiki.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzl.wiki.domain.Category;
import com.lzl.wiki.domain.CategoryExample;
import com.lzl.wiki.mapper.CategoryMapper;
import com.lzl.wiki.req.CategoryQueryReq;
import com.lzl.wiki.req.CategorySaveReq;
import com.lzl.wiki.resp.CategoryQueryResp;
import com.lzl.wiki.resp.PageResp;
import com.lzl.wiki.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {
//    日志
    private static final Logger LOG= LoggerFactory.getLogger(CategoryServiceImpl.class);
    //jdk自带
    @Resource
    private CategoryMapper categoryMapper;
//    @Autowired spring自带

//    添加时间戳
    @Resource
    private SnowFlake snowFlake;

    @Override
    public PageResp<CategoryQueryResp> list(CategoryQueryReq req) {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
//        支持分页第一个参数：页码，第二个参数：每一页的条数
//        说明：从1开始，只对第一个sql有作用
        PageHelper.startPage(req.getPage(),req.getSize());
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        PageInfo<Category> pageInfo=new PageInfo<>(categoryList);

//        写日志使用占位符写法("{}",xxxx)
        LOG.info("总行数:{}",pageInfo.getTotal());
        LOG.info("总页数:{}",pageInfo.getPages());
//        将categoryList转化为CategoryQueryResp
//        List<CategoryQueryResp> respList=new ArrayList<>();
//        for (Category category : categoryList) {
//            1、为使用工具当个赋值
//            CategoryQueryResp categoryResp = new CategoryQueryResp();
//            手工拷贝太慢
//            categoryResp.setId(category.getId());
//            使用Bean拷贝工具 第一个变量是要赋给另一个对象的值，第二个变量是需要得到值的变量,category赋值给categoryResp
//            BeanUtils.copyProperties(category,categoryResp);

//            2、使用CopyUtil自定义类，单个赋值
//            CategoryQueryResp categoryResp = CopyUtil.copy(category, CategoryQueryResp.class);
//            赋值完后放入到集合中
//            respList.add(categoryResp);
//        }
//        使用自定义工具类List赋值
        List<CategoryQueryResp> list = CopyUtil.copyList(categoryList, CategoryQueryResp.class);
//        放入到分页中
        PageResp<CategoryQueryResp> pageResp = new PageResp<>();
//        设置总页数
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
//        返回对象
        return pageResp;
    }

    @Override
    public List<CategoryQueryResp> all() {
        CategoryExample categoryExample = new CategoryExample();
//        asc升序，desc降序
        categoryExample.setOrderByClause("sort asc");
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        List<CategoryQueryResp> list = CopyUtil.copyList(categoryList, CategoryQueryResp.class);
//        返回对象
        return list;
    }

    /**
     * 保存、修改的方法
     * 有Id是更新，无Id是新增
     * @param req
     */
    @Override
    public void save(CategorySaveReq req) {
//        给req转型
        Category category=CopyUtil.copy(req,Category.class);
//        判断是更新还是新增、有Id是更新无Id是新增
        if (ObjectUtils.isEmpty(req.getId())){
//            新增
//            利用雪花算法设置新增的Id,新增id有三种算法(自增，uuid，雪花算法）
            category.setId(snowFlake.nextId());
            categoryMapper.insert(category);
        }else {
//            更新
//        insert是新增保存修改使用update保存
            categoryMapper.updateByPrimaryKey(category);
        }

    }

    /**
     * 删除的方法
     * @param id
     */
    @Override
    public void del(Long id) {
        categoryMapper.deleteByPrimaryKey(id);
    }
}
