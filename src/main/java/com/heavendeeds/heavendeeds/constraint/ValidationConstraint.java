package com.heavendeeds.heavendeeds.constraint;

public class ValidationConstraint {

    private ValidationConstraint() {

    }

    public static final String LOGIN_EMAIL_NOTEXIST = "Email ID is not registered";
    public static final String EMAIL_NOTEXIST = "Entered email id is invalid";
    public static final String EMAIL_INVALID = "Invalid email ID format";
    public static final String EMAIL_NULL = "Email should not be null";
    public static final String EMAIL_EMPTY = "Email should not be empty";
    public static final String P_W_D_NULL = "Password should not be null";
    public static final String P_W_D_EMPTY = "Password should not be empty";
    public static final String P_W_D_MISMATCH = "Please enter a valid password or user is inactive";
}