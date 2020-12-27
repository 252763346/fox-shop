package com.fh.resource.entity;

import lombok.Data;

/**
 * @ClassName searchBean
 * @Deacription TODO
 * @Author 路东生
 * @Date 2020/12/17 12:37
 **/
@Data
public class searchBean extends UmsResource{
    private String nameKeyword;
    private String urlKeyword;
}
