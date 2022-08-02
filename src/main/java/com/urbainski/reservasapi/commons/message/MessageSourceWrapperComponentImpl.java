package com.urbainski.reservasapi.commons.message;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageSourceWrapperComponentImpl implements MessageSourceWrapperComponent {

    private final MessageSource messageSource;


    public MessageSourceWrapperComponentImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage(SystemMessages systemMessages) {
        return getMessage(systemMessages, new Object[]{});
    }

    @Override
    public String getMessage(SystemMessages systemMessages, Object[] args) {
        return messageSource.getMessage(systemMessages.getKey(), args, Locale.getDefault());
    }

}
