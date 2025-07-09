package org.yaojiu.supermarket.handle;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yaojiu.supermarket.entity.Result;
import org.yaojiu.supermarket.exception.BaseException;
import org.yaojiu.supermarket.exception.LoginException;

import java.util.HashMap;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandle {
    @ResponseBody
    @ExceptionHandler({BaseException.class})
    public Result commonExceptionHandle(BaseException e){
        return Result.fail().resetCode(e.getCode()).resetMsg(e.getMessage());
    }
    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Result methodArgumentNotValidHandle(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors();
        HashMap<String,String> errorMap = new HashMap<>();
        fieldErrors.forEach(fieldError -> errorMap.put(fieldError.getField(),fieldError.getDefaultMessage()));
        return Result.fail().resetMsg("参数错误").resetData(errorMap).resetCode(Result.FAIL_DATA_INVALID);
    }
    @ResponseBody
    @ExceptionHandler({Exception.class})
    public Result ExceptionHandle(Exception e){
        System.out.println(e);
        return Result.fail().resetMsg("错误的请求").resetCode(Result.FAIL_BAD_REQUEST);
    }
}
