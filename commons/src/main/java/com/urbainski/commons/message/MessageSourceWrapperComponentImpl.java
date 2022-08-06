package com.urbainski.commons.message;

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
    public String getMessage(String messageKey) {
        return getMessage(messageKey, new Object[]{});
    }

    @Override
    public String getMessage(String messageKey, Object[] args) {
        return messageSource.getMessage(messageKey, args, Locale.getDefault());
    }

}
