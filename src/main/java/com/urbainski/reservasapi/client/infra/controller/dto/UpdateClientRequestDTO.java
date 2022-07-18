package com.urbainski.reservasapi.client.infra.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.urbainski.reservasapi.client.ClientType;
import com.urbainski.reservasapi.client.infra.controller.dto.validation.provider.UpdateClientGroupSequenceProvider;
import com.urbainski.reservasapi.commons.bean.validation.group.CNPJGroup;
import com.urbainski.reservasapi.commons.bean.validation.group.CPFGroup;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@GroupSequenceProvider(value = UpdateClientGroupSequenceProvider.class)
public class UpdateClientRequestDTO {

    @NotNull
    @Size(min = 10, max = 500)
    private String name;
    @NotBlank
    @CPF(groups = CPFGroup.class)
    @CNPJ(groups = CNPJGroup.class)
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
