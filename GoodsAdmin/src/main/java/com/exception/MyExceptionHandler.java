package com.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice      //开启全局异常捕获
public class MyExceptionHandler {

    @ExceptionHandler(value=Exception.class)
    public String exceptionHandler(Exception e){
        System.out.println("发生了一个异常"+e);
        return e.getMessage();
    }




}