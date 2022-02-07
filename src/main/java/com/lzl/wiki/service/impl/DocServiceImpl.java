package com.lzl.wiki.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzl.wiki.domain.Doc;
import com.lzl.wiki.domain.DocExample;
import com.lzl.wiki.mapper.ContentMapper;
import com.lzl.wiki.mapper.DocMapper;
import com.lzl.wiki.req.DocQueryReq;
import com.lzl.wiki.req.DocSaveReq;
import com.lzl.wiki.resp.DocQueryResp;
import com.lzl.wiki.resp.PageResp;
import com.lzl.wiki.service.DocService;
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
public class DocServiceImpl implements DocService {
//    日志
    private static final Logger LOG= LoggerFactory.getLogger(DocServiceImpl.class);
    //jdk自带
    @Resource
    private DocMapper docMapper;

    @Resource
    private ContentMapper contentMapper;
//    @Autowired spring自带

//    添加时间戳
    @Resource
    private SnowFlake snowFlake;

    @Override
    public PageResp<DocQueryResp> list(DocQueryReq req) {
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        DocExample.Criteria criteria = docExample.createCriteria();
//        支持分页第一个参数：页码，第二个参数：每一页的条数
//        说明：从1开始，只对第一个sql有作用
        PageHelper.startPage(req.getPage(),req.getSize());
        List<Doc> docList = docMapper.selectByExample(docExample);
        PageInfo<Doc> pageInfo=new PageInfo<>(docList);

//        写日志使用占位符写法("{}",xxxx)
        LOG.info("总行数:{}",pageInfo.getTotal());
        LOG.info("总页数:{}",pageInfo.getPages());
//        将docList转化为DocQueryResp
//        List<DocQueryResp> respList=new ArrayList<>();
//        for (Doc doc : docList) {
//            1、为使用工具当个赋值
//            DocQueryResp docResp = new DocQueryResp();
//            手工拷贝太慢
//            docResp.setId(doc.getId());
//            使用Bean拷贝工具 第一个变量是要赋给另一个对象的值，第二个变量是需要得到值的变量,doc赋值给docResp
//            BeanUtils.copyProperties(doc,docResp);

//            2、使用CopyUtil自定义类，单个赋值
//            DocQueryResp docResp = CopyUtil.copy(doc, DocQueryResp.class);
//            赋值完后放入到集合中
//            respList.add(docResp);
//        }
//        使用自定义工具类List赋值
        List<DocQueryResp> list = CopyUtil.copyList(docList, DocQueryResp.class);
//        放入到分页中
        PageResp<DocQueryResp> pageResp = new PageResp<>();
//        设置总页数
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
//        返回对象
        return pageResp;
    }

    @Override
    public List<DocQueryResp> all(Long ebookId) {
        DocExample docExample = new DocExample();
        docExample.createCriteria().andEbookIdEqualTo(ebookId);
//        asc升序，desc降序
        docExample.setOrderByClause("sort asc");
        List<Doc> docList = docMapper.selectByExample(docExample);

        List<DocQueryResp> list = CopyUtil.copyList(docList, DocQueryResp.class);
//        返回对象
        return list;
    }

    /**
     * 保存、修改的方法
     * 有Id是更新，无Id是新增
     * @param req
     */
    @Override
    public void save(DocSaveReq req) {
//        给req转型
        Doc doc=CopyUtil.copy(req,Doc.class);
//        Content content=CopyUtil.copy(req, Content.class);
//        判断是更新还是新增、有Id是更新无Id是新增
        if (ObjectUtils.isEmpty(req.getId())){
//            新增
//            利用雪花算法设置新增的Id,新增id有三种算法(自增，uuid，雪花算法）
            doc.setId(snowFlake.nextId());
            docMapper.insert(doc);

//            content.setId(doc.getId());
//            contentMapper.insert(content);
        }else {
//            更新
//        insert是新增保存修改使用update保存
            docMapper.updateByPrimaryKey(doc);
//            contentMapper.updateByExampleWithBLOBs(content);
        }

    }

    /**
     * 删除的方法
     * @param id
     */
    @Override
    public void del(Long id) {
        docMapper.deleteByPrimaryKey(id);
    }
    public void del(List<String> ids) {
        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();
        criteria.andIdIn(ids);
        docMapper.deleteByExample(docExample);
    }
}
