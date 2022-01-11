package com.lzl.wiki.resp;

import java.util.List;

/**
 * <h3>wiki</h3>
 * <p>返回值的分页</p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 * @date : 2022-01-11 17:03
 **/
public class PageResp<T> {
    private long total;

    private List<T> list;


    @Override
    public String toString() {
        return "PageResp{" +
                "total=" + total +
                ", list=" + list +
                '}';
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
