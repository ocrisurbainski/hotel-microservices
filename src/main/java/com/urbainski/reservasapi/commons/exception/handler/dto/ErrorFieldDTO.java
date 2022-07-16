package com.urbainski.reservasapi.commons.exception.handler.dto;

import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
public class ErrorFieldDTO extends ErrorDTO {

    private final String field;

    public ErrorFieldDTO(String field, String message) {
        super(message);
        this.field = field;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
