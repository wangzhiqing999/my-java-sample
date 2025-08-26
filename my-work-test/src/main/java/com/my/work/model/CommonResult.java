package com.my.work.model;


import lombok.Data;

/**
 * 通用的结果数据.
 * 一个 code, 一个 msg, 一个 data
 */
@Data
public class CommonResult {

    private int code;
    private String msg;
    private String data;

    public CommonResult() {
    }

    public CommonResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CommonResult(int code, String msg, String data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


}
