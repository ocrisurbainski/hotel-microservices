package com.urbainski.commons.message;

public interface MessageSourceWrapperComponent {

    String getMessage(String messageKey);

    String getMessage(String messageKey, Object[] args);


}
