package com.my.work.model;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * 通用的结果数据.
 * 一个 code, 一个 msg, 一个 data
 *
 * @param code 响应码（200=成功，400=参数错误，500=服务器内部错误）
 * @param msg  响应消息
 * @param data 业务数据（JSON 字符串或 null）
 */
public record CommonResult(
        @Schema(description = "响应码（200=成功，400=参数错误，500=服务器内部错误）", example = "200") int code,
        @Schema(description = "响应消息", example = "success") String msg,
        @Schema(description = "业务数据（JSON 字符串或 null）") String data) {

    /**
     * 构造结果（无业务数据）.
     *
     * @param code 响应码
     * @param msg  响应消息
     */
    public CommonResult(int code, String msg) {
        this(code, msg, null);
    }
}
