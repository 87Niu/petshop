package com.petshop.common;

/**
 * 自定义业务全局异常类
 */

public class CustomException extends RuntimeException {
    public CustomException(String message){
        super(message);
    }
}
