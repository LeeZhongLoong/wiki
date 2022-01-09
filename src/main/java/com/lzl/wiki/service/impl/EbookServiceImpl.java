package com.lzl.wiki.service.impl;

import com.lzl.wiki.domain.Ebook;
import com.lzl.wiki.domain.EbookExample;
import com.lzl.wiki.mapper.EbookMapper;
import com.lzl.wiki.req.EbookReq;
import com.lzl.wiki.resp.EbookResp;
import com.lzl.wiki.service.EbookService;
import com.lzl.wiki.utils.CopyUtil;
import org.springframework.stereotype.Service;

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
    //jdk自带
    @Resource
    private EbookMapper ebookMapper;
//    @Autowired spring自带

    @Override
    public List<EbookResp> list(EbookReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        criteria.andNameLike("%"+req.getName()+"%");
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);
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

//        返回对象
        return list;
    }
}
