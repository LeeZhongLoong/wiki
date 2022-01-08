package com.lzl.wiki.service.impl;

import com.lzl.wiki.domain.Test;
import com.lzl.wiki.mapper.TestMapper;
import com.lzl.wiki.service.TestService;
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
public class TestServiceImpl  implements TestService {
    //jdk自带
    @Resource
    private TestMapper testMapper;
//    @Autowired spring自带

    @Override
    public List<Test> list() {
        return testMapper.list();
    }
}
