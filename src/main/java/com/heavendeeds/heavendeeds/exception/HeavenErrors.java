package com.heavendeeds.heavendeeds.exception;

import lombok.Data;

import java.util.Arrays;

@Data
public class HeavenErrors {

    private String errorCode;
    private Object[] parameters;
    private String message;

    public HeavenErrors(String errorCode, String message, Object[] parameters){
        this.errorCode = errorCode;
        this.message = message;
        this.parameters = parameters;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString(){
        return errorCode + "-" + Arrays.toString(parameters);
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
