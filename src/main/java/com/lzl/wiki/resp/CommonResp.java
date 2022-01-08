package com.lzl.wiki.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h3>wiki</h3>
 * <p>通用的返回类统一返回的格式</p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 * @date : 2022-01-08 21:55
 **/

@NoArgsConstructor
@Data
@AllArgsConstructor
public class CommonResp<T> {

    /**
     * 业务上的成功
     */
    private boolean success=true;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回泛型数据，自定义数据
     */
    private T content;

}
