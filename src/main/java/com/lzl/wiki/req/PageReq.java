package com.lzl.wiki.req;

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

    //    页码
    private int page;
//    条数
    private int size;
}
