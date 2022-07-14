package com.urbainski.reservasapi.client.infra.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.urbainski.reservasapi.client.ClientType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class CreateClientRequestDTO {

    @NotNull
    @Size(min = 10, max = 500)
    private String name;
    @NotBlank
    private String document;
    private String telephone;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    @NotNull
    private ClientType type;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
