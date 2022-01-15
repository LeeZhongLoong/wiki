package com.lzl.wiki.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzl.wiki.domain.Ebook;
import com.lzl.wiki.domain.EbookExample;
import com.lzl.wiki.mapper.EbookMapper;
import com.lzl.wiki.req.EbookQueryReq;
import com.lzl.wiki.req.EbookSaveReq;
import com.lzl.wiki.resp.EbookResp;
import com.lzl.wiki.resp.PageResp;
import com.lzl.wiki.service.EbookService;
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
public class EbookServiceImpl implements EbookService {
//    日志
    private static final Logger LOG= LoggerFactory.getLogger(EbookServiceImpl.class);
    //jdk自带
    @Resource
    private EbookMapper ebookMapper;
//    @Autowired spring自带

//    添加时间戳
    @Resource
    private SnowFlake snowFlake;

    @Override
    public PageResp<EbookResp> list(EbookQueryReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())){
            criteria.andNameLike("%"+req.getName()+"%");
        }
//        支持分页第一个参数：页码，第二个参数：每一页的条数
//        说明：从1开始，只对第一个sql有作用
        PageHelper.startPage(req.getPage(),req.getSize());
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);
        PageInfo<Ebook> pageInfo=new PageInfo<>(ebookList);

//        写日志使用占位符写法("{}",xxxx)
        LOG.info("总行数:{}",pageInfo.getTotal());
        LOG.info("总页数:{}",pageInfo.getPages());
//        将ebookList转化为EbookResp
//        List<EbookResp> respList=new ArrayList<>();
//        for (Ebook ebook : ebookList) {
//            1、为使用工具当个赋值
//            EbookResp ebookResp = new EbookResp();
//            手工拷贝太慢
//            ebookResp.setId(ebook.getId());
//            使用Bean拷贝工具 第一个变量是要赋给另一个对象的值，第二个变量是需要得到值的变量,ebook赋值给ebookResp
//            BeanUtils.copyProperties(ebook,ebookResp);

//            2、使用CopyUtil自定义类，单个赋值
//            EbookResp ebookResp = CopyUtil.copy(ebook, EbookResp.class);
//            赋值完后放入到集合中
//            respList.add(ebookResp);
//        }
//        使用自定义工具类List赋值
        List<EbookResp> list = CopyUtil.copyList(ebookList, EbookResp.class);
//        放入到分页中
        PageResp<EbookResp> pageResp = new PageResp<>();
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
    public void save(EbookSaveReq req) {
//        给req转型
        Ebook ebook=CopyUtil.copy(req,Ebook.class);
//        判断是更新还是新增、有Id是更新无Id是新增
        if (ObjectUtils.isEmpty(req.getId())){
//            新增
//            利用雪花算法设置新增的Id,新增id有三种算法(自增，uuid，雪花算法）
            ebook.setId(snowFlake.nextId());
            ebookMapper.insert(ebook);
        }else {
//            更新
//        insert是新增保存修改使用update保存
            ebookMapper.updateByPrimaryKey(ebook);
        }

    }

    /**
     * 删除的方法
     * @param id
     */
    @Override
    public void del(Long id) {
        ebookMapper.deleteByPrimaryKey(id);
    }
}
