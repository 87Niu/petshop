package com.petshop.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice(annotations = {Controller.class, RestController.class})
@ResponseBody
public class GlobalException {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result exceptionHandler(SQLIntegrityConstraintViolationException e) {
        if (e.getMessage().contains("Duplicate entry")) {
            String[] split = e.getMessage().split(" ");
            String  msg = split[2] + "已存在";
            return Result.error(msg);
        }
        return Result.error("未知错误");
    }

    @ExceptionHandler(CustomException.class)
    public Result exceptionHandler(CustomException ex){
        return Result.error(ex.getMessage());
    }
}
