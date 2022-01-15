package com.lzl.wiki.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//集成页面属性
public class EbookQueryReq extends PageReq {
    private Long id;

    private String name;
}