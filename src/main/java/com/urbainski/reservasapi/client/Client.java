package com.urbainski.reservasapi.client;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDate;

@Getter
@Setter
public class Client {

    private String id;
    private String name;
    private String document;
    private String telephone;
    private LocalDate birthDate;
    private ClientType type;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
