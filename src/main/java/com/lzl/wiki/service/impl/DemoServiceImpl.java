package com.lzl.wiki.service.impl;

import com.lzl.wiki.domain.Demo;
import com.lzl.wiki.mapper.DemoMapper;
import com.lzl.wiki.service.DemoService;
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
public class DemoServiceImpl implements DemoService {
    //jdk自带
    @Resource
    private DemoMapper demoMapper;
//    @Autowired spring自带

    @Override
    public List<Demo> list() {
        return demoMapper.selectByExample(null);
    }
}
