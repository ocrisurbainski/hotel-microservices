package com.urbainski.reservasapi.client.infra.controller.dto.validation.provider;

import com.urbainski.reservasapi.client.ClientType;
import com.urbainski.reservasapi.client.infra.controller.dto.CreateClientRequestDTO;
import com.urbainski.reservasapi.commons.bean.validation.group.CNPJGroup;
import com.urbainski.reservasapi.commons.bean.validation.group.CPFGroup;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateClientGroupSequenceProvider implements DefaultGroupSequenceProvider<CreateClientRequestDTO> {

    @Override
    public List<Class<?>> getValidationGroups(CreateClientRequestDTO createClientRequestDTO) {

        List<Class<?>> groups = new ArrayList<>();
        groups.add(CreateClientRequestDTO.class);

        if (Objects.isNull(createClientRequestDTO)) {
            return groups;
        }

        if (ClientType.FISICA.equals(createClientRequestDTO.getType())) {
            groups.add(CPFGroup.class);
        }

        if (ClientType.JURIDICA.equals(createClientRequestDTO.getType())) {
            groups.add(CNPJGroup.class);
        }

        return groups;
    }

}
