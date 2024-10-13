package com.heavendeeds.heavendeeds.exception;

import java.util.ArrayList;
import java.util.List;

public class HeavenApiException extends Exception {

    private static final long serialVersionUID = 2360019225910863661L;

    protected List<HeavenErrors> errorList;

    protected HeavenApiException() {
        errorList = new ArrayList<>();
    }

    public HeavenApiException(HeavenApiExceptionCode exceptionCode, Object[] parameters) {
        errorList = new ArrayList<>();
        errorList.add(new HeavenErrors(exceptionCode.name(), null, parameters));
    }

    public HeavenApiException(List<HeavenErrors> errorList) {
        this.errorList = errorList;
    }

    public HeavenApiException(String userNotFound) {
    }

    public HeavenApiException(String errorProcessingUserData, Exception e) {
    }

    public List<HeavenErrors> getErrorList() {
        return errorList;
    }
}