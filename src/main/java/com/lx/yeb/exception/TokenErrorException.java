package com.lx.yeb.exception;

import lombok.Data;

/**
 * @ClassName TokenErrorException
 * @Description token解析异常
 * @Author lipan
 * @Date 2021/3/18 15:31
 * @Version 1.0
 */
@Data
public class TokenErrorException extends RuntimeException{
    private Integer errorCode;

    public TokenErrorException(Integer errorCode, String errorMsg){
        super(errorMsg);
        this.errorCode = errorCode;
    }

}
