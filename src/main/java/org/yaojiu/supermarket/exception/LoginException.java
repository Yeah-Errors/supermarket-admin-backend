package org.yaojiu.supermarket.exception;

import lombok.Getter;
import org.yaojiu.supermarket.entity.Result;

@Getter
public class LoginException extends BaseException{
    public LoginException(String message) {
        super(Result.FAIL_DATA_INVALID,message);
    }
}
