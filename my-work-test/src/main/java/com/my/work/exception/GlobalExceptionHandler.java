package com.my.work.exception;

import com.my.work.model.CommonResult;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 全局异常处理器（SEC-P0-4 修复，M-P1-2 同步统一为 {@link CommonResult}）。
 *
 * <p>统一兜底处理 Controller 层未捕获的异常：
 * <ul>
 *   <li>完整堆栈仅记录到服务端日志（{@code log.error}）</li>
 *   <li>客户端只收到通用错误消息与 HTTP 状态码，不暴露内部实现细节、堆栈、SQL 等</li>
 *   <li>响应体统一为 {@link CommonResult}，与正常接口响应格式一致</li>
 *   <li>{@link IllegalArgumentException} 按参数错误返回 400；其余异常统一返回 500</li>
 * </ul>
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** 参数错误响应码. */
    private static final int CODE_BAD_REQUEST = 400;
    /** 服务器内部错误响应码. */
    private static final int CODE_INTERNAL_ERROR = 500;
    /** 参数错误消息. */
    private static final String MSG_BAD_REQUEST = "参数错误，请检查请求内容";
    /** 服务器内部错误消息. */
    private static final String MSG_INTERNAL_ERROR = "服务器内部错误，请稍后重试";

    /**
     * 参数绑定/校验异常（SEC-P1-3）：{@code @Valid}/{@code @NotBlank} 校验失败、参数缺失或类型不匹配、
     * 请求体不可读等，统一返回 400 + 通用参数错误提示，具体错误详情仅记录服务端日志。
     *
     * @param e 参数绑定/校验异常
     * @return 400 响应（{@link CommonResult} 包装，含通用参数错误提示）
     */
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            BindException.class,
            ConstraintViolationException.class,
            HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<CommonResult> handleValidationException(Exception e) {
        log.warn("parameter validation failed: {}", e.getMessage());
        return ResponseEntity.badRequest()
                .body(new CommonResult(CODE_BAD_REQUEST, MSG_BAD_REQUEST));
    }

    /**
     * 参数/业务类异常：返回 400，提示信息不包含内部细节。
     *
     * @param e 参数/业务异常
     * @return 400 响应（{@link CommonResult} 包装，含通用参数错误提示）
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CommonResult> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("illegal argument: {}", e.getMessage());
        return ResponseEntity.badRequest()
                .body(new CommonResult(CODE_BAD_REQUEST, MSG_BAD_REQUEST));
    }

    /**
     * 兜底异常：完整堆栈记录服务端日志，客户端仅返回通用错误消息（500）。
     *
     * @param e 未捕获的异常
     * @return 500 响应（{@link CommonResult} 包装，含通用内部错误提示）
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResult> handleException(Exception e) {
        log.error("unhandled exception", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new CommonResult(CODE_INTERNAL_ERROR, MSG_INTERNAL_ERROR));
    }
}
