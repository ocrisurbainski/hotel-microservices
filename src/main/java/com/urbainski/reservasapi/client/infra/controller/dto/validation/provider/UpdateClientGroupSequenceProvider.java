package com.urbainski.reservasapi.client.infra.controller.dto.validation.provider;

import com.urbainski.reservasapi.client.ClientType;
import com.urbainski.reservasapi.client.infra.controller.dto.UpdateClientRequestDTO;
import com.urbainski.reservasapi.commons.bean.validation.group.CNPJGroup;
import com.urbainski.reservasapi.commons.bean.validation.group.CPFGroup;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UpdateClientGroupSequenceProvider implements DefaultGroupSequenceProvider<UpdateClientRequestDTO> {

    @Override
    public List<Class<?>> getValidationGroups(UpdateClientRequestDTO updateClientRequestDTO) {

        List<Class<?>> groups = new ArrayList<>();
        groups.add(UpdateClientRequestDTO.class);

        if (Objects.isNull(updateClientRequestDTO)) {
            return groups;
        }

        if (ClientType.FISICA.equals(updateClientRequestDTO.getType())) {
            groups.add(CPFGroup.class);
        }

        if (ClientType.JURIDICA.equals(updateClientRequestDTO.getType())) {
            groups.add(CNPJGroup.class);
        }

        return groups;
    }

}
