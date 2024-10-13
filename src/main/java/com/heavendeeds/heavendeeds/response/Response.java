package com.heavendeeds.heavendeeds.response;

import lombok.Data;

@Data
public class Response <T>{

    private Integer status;

    private T data;

    private String message;
}
