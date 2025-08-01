package org.yaojiu.supermarket.exception;

import lombok.Getter;
import org.yaojiu.supermarket.entity.Result;

@Getter
public class BaseException extends RuntimeException {
    private final int code;
    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }
    public BaseException(String message) {
        super(message);
        this.code= Result.FAIL_BAD_REQUEST;
    }

}
