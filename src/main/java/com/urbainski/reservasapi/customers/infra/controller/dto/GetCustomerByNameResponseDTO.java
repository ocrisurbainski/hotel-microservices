package com.urbainski.reservasapi.customers.infra.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.urbainski.reservasapi.customers.CustomerType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDate;

@Getter
@Setter
public class GetCustomerByNameResponseDTO {
    private String id;
    private String name;
    private String document;
    private String telephone;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private CustomerType type;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
