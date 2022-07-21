package com.urbainski.reservasapi.commons.message;

public interface MessageSourceWrapperComponent {

    String getMessage(SystemMessages systemMessages);

    String getMessage(SystemMessages systemMessages, Object[] args);


}
