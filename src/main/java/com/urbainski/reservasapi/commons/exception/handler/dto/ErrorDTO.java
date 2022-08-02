package com.urbainski.reservasapi.commons.exception.handler.dto;

import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
public class ErrorDTO {

    private final String message;

    public ErrorDTO(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
