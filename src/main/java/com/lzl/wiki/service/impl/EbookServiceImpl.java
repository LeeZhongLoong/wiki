package com.lzl.wiki.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzl.wiki.domain.Ebook;
import com.lzl.wiki.domain.EbookExample;
import com.lzl.wiki.mapper.EbookMapper;
import com.lzl.wiki.req.EbookReq;
import com.lzl.wiki.resp.EbookResp;
import com.lzl.wiki.resp.PageResp;
import com.lzl.wiki.service.EbookService;
import com.lzl.wiki.utils.CopyUtil;
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

    @Override
    public PageResp<EbookResp> list(EbookReq req) {
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
}
