package com.urbainski.reservasapi.customers.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDate;

@Getter
@Setter
public class Customer {

    private String id;
    private String name;
    private String document;
    private String telephone;
    private LocalDate birthDate;
    private CustomerType type;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
