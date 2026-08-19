package com.my.work.model;

import lombok.Data;

/**
 * 项目版本信息响应体.
 */
@Data
public class VersionResponse {

    private String version;

    private String projectName;

    /**
     * 构造函数
     *
     * @param version     版本号
     * @param projectName 项目名称
     */
    public VersionResponse(String version, String projectName) {
        this.version = version;
        this.projectName = projectName;
    }
}
