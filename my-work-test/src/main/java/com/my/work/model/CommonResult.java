package com.my.work.model;


import lombok.Data;

/**
 * 通用的结果数据.
 * 一个 code, 一个 msg, 一个 data
 */
@Data
public class CommonResult {

    /** 响应码（200=成功，400=参数错误，500=服务器内部错误）. */
    private int code;

    /** 响应消息. */
    private String msg;

    /** 业务数据（JSON 字符串或 null）. */
    private String data;

    /** 无参构造. */
    public CommonResult() {
    }

    /**
     * 构造结果（无业务数据）.
     *
     * @param code 响应码
     * @param msg  响应消息
     */
    public CommonResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 构造结果（含业务数据）.
     *
     * @param code 响应码
     * @param msg  响应消息
     * @param data 业务数据（JSON 字符串或 null）
     */
    public CommonResult(int code, String msg, String data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


}
