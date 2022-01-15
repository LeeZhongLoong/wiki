package com.lzl.wiki.req;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * <h3>wiki</h3>
 * <p>请求的页属性</p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 * @date : 2022-01-11 16:50
 **/
//定义父类分页类型
public class PageReq {
    //    页码
    @NotNull(message = "【页码】不能为空")
    private int page;


    //    条数
    @NotNull(message = "【每页条数】不能为空")
    @Max(value = 1000,message = "【每页条数】不能超过1000")
    private int size;

    @Override
    public String toString() {
        return "PageReq{" +
                "page=" + page +
                ", size=" + size +
                '}';
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


}
