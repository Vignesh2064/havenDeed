package com.heavendeeds.heavendeeds.constant;

public enum Messages {

    USER_CREATED("User created successfully"), USER_DATA_CREATED_SUCCESSFULLY("User Added successfully"),
    USER_DETAILS_UPDATED("User details updated successfully"), USER_DELETED("User Details deleted"),
    USER_DETAILS_RETRIEVED("User details retrieved successfully"), INVALID_USER_DATA_REQUEST_TO_ADD("Given Data is not correct"),
    EMAIL_SEND("Email sent successfully"), EMAIL_IS_NOT_SENT("Email is not send"),PROPERTY_CREATED_SUCCESSFULLY("Property created successfully"),
    PROPERTY_DETAILS_RETRIEVED("User property details retrieved successfully"),USER_PROPERTY_UPDATED("User property updated successfully")
    ,PROPERTY_DELETED("User Property deleted successfully");
    private final String messages;

    Messages(String messages) {
        this.messages = messages;
    }

    public String getMessages() {
        return messages;
    }
}