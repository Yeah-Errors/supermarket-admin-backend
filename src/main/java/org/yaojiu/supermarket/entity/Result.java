package org.yaojiu.supermarket.entity;

import lombok.Getter;


@Getter
public class Result {

    public static final int SUCCESS = 200;
    public static final int FAIL_NEED_REDIRECT = 302;
    public static final int FAIL_BAD_REQUEST = 400;
    public static final int FAIL_PERMISSION_EXCEPTION = 401;
    public static final int FAIL_DATA_INVALID = 402;
    public static final int FAIL_NEED_LOGIN = 403;
    public static final int ERROR_SERVER_ERROR = 500;

    private Integer code;
    private String msg;
    private Object data;

    public static Result success() {
        Result result = new Result();
        return result.resetCode(SUCCESS).resetMsg("操作成功");
    }
    public static Result fail(){
        Result result = new Result();
        return result.resetCode(FAIL_BAD_REQUEST).resetMsg("操作失败");
    }
    public Result resetCode(int code) {
        this.code = code;
        return this;
    }
    public Result resetMsg(String msg) {
        this.msg = msg;
        return this;
    }
    public Result resetData(Object data) {
        this.data = data;
        return this;
    }

}
