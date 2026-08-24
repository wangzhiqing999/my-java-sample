package com.my.work.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 保存配置请求参数（SEC-P1-3 修复：原 Controller 松散参数 {@code (int code, String msg)} 封装为 DTO + Bean Validation）。
 *
 * <p>字段校验规则：
 * <ul>
 *   <li>{@code code}：必填，且不能为负数</li>
 *   <li>{@code msg}：必填，且不能为空白字符串</li>
 * </ul>
 *
 * <p>绑定方式：{@code POST /test/save-config?code=1&msg=hello}（query 参数经 {@code @ModelAttribute} 绑定，
 * 校验失败由 {@code GlobalExceptionHandler} 统一返回 400）。
 */
@Schema(description = "保存配置请求参数（query string 绑定）")
@Data
public class SaveConfigRequest {

    /** 配置编码，必填且不能为负数. */
    @Schema(description = "配置编码，必填且不能为负数", example = "1")
    @NotNull(message = "code 不能为空")
    @Min(value = 0, message = "code 不能为负数")
    private Integer code;

    /** 配置消息，必填且不能为空白. */
    @Schema(description = "配置消息，必填且不能为空白", example = "hello")
    @NotBlank(message = "msg 不能为空")
    private String msg;
}
