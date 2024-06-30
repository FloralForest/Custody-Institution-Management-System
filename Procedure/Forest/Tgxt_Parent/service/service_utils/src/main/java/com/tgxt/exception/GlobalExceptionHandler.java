package com.tgxt.exception;


import com.tgxt.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 统一异常处理
 * 优先寻找特定最后全局
 */
@ControllerAdvice //aop
public class GlobalExceptionHandler {

    /**
     * 全局异常处理
     */
    @ExceptionHandler(Exception.class) //异常必定经过
    @ResponseBody //返回json数据
    public Result<Object> error(Exception e){
        e.printStackTrace(); //在命令行打印异常信息，于程序中出错的位置及原因
        return Result.fail(null).message("全局异常处理");
    }

    /**
     * 特定异常处理
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result<Object> error(ArithmeticException e){
        e.printStackTrace();
        return Result.fail(null).message("执行了特定异常处理");
    }
}
