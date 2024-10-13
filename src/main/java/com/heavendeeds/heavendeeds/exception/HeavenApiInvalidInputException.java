package com.heavendeeds.heavendeeds.exception;

import java.util.List;

public class HeavenApiInvalidInputException extends HeavenApiException {

    private static final long serialVersionUID = 1L;

    public HeavenApiInvalidInputException(HeavenApiExceptionCode exceptionCode, Object[] parameters){
        super(exceptionCode, parameters);
    }

    public HeavenApiInvalidInputException(List<HeavenErrors> errors){
        super(errors);
    }
}
