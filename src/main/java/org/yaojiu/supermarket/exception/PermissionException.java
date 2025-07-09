package org.yaojiu.supermarket.exception;

import lombok.Getter;
import org.yaojiu.supermarket.entity.Result;

@Getter
public class PermissionException extends BaseException{
    public PermissionException() {
        super(Result.FAIL_PERMISSION_EXCEPTION,"用户权限不足");
    }
    public PermissionException(int code, String message){
        super(code,message);
    }
}
