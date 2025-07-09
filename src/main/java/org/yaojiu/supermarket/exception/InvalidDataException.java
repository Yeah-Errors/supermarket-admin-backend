package org.yaojiu.supermarket.exception;

import org.yaojiu.supermarket.entity.Result;

public class InvalidDataException extends BaseException {
    public InvalidDataException(String message) {
        super(Result.FAIL_DATA_INVALID, message);
    }
}
