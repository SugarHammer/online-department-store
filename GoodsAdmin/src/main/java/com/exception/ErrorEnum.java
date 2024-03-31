package com.exception;


/**
 *   自定义枚举类，定义常见的错误码以及错误信息
 */

public enum ErrorEnum {
    // 数据操作错误定义
    SUCCESS(200, "nice"),
    NO_PERMISSION(403,"您没有权限访问"),
    NO_AUTH(401,"请先登录"),
    NOT_FOUND(404, "未找到该资源!"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    ;

    /** 错误码 */
    private Integer errorCode;

    /** 错误信息 */
    private String errorMsg;

    ErrorEnum(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}