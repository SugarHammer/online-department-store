package com.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;


/**
 *     自定义全局异常处理类，不用自己对每个异常都使用try，catch的方式来实现
 */

@ControllerAdvice
public class GlobalExceptionHandler {


    //处理自定义异常
    @ExceptionHandler(value = DefinitionException.class)
    @ResponseBody
    public Result bizExceptionHandler(DefinitionException e) {
        return Result.defineError(e);
    }


    //处理其他异常
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result exceptionHandler( Exception e) {
        if (e instanceof NoHandlerFoundException) {
            return Result.otherError(ErrorEnum.NOT_FOUND);
        }
      else  return Result.otherError(ErrorEnum.INTERNAL_SERVER_ERROR);
    }
}