package com.my.work.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 项目版本信息响应体.
 *
 * @param version     版本号（读取失败时为 "unknown"）
 * @param projectName 项目名称（读取失败时为 "test-service"）
 */
public record VersionResponse(
        @Schema(description = "版本号（读取失败时为 unknown）", example = "1.1-SNAPSHOT") String version,
        @Schema(description = "项目名称（读取失败时为 test-service）", example = "test-service") String projectName) {
}
