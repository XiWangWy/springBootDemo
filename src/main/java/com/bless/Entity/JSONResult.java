package com.bless.Entity;

import lombok.Data;

/**
 * Created by wangxi on 18/8/7.
 * 统一错误处理实体类
 */
@Data
public class JSONResult<T> {

    private Integer code;

    private String msg;

    private T data;
}
