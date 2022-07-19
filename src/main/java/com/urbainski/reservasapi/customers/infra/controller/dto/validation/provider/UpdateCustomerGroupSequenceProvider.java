package com.urbainski.reservasapi.customers.infra.controller.dto.validation.provider;

import com.urbainski.reservasapi.customers.domain.CustomerType;
import com.urbainski.reservasapi.customers.infra.controller.dto.UpdateCustomerRequestDTO;
import com.urbainski.reservasapi.commons.bean.validation.group.CNPJGroup;
import com.urbainski.reservasapi.commons.bean.validation.group.CPFGroup;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UpdateCustomerGroupSequenceProvider implements DefaultGroupSequenceProvider<UpdateCustomerRequestDTO> {

    @Override
    public List<Class<?>> getValidationGroups(UpdateCustomerRequestDTO updateCustomerRequestDTO) {

        List<Class<?>> groups = new ArrayList<>();
        groups.add(UpdateCustomerRequestDTO.class);

        if (Objects.isNull(updateCustomerRequestDTO)) {
            return groups;
        }

        if (CustomerType.FISICA.equals(updateCustomerRequestDTO.getType())) {
            groups.add(CPFGroup.class);
        }

        if (CustomerType.JURIDICA.equals(updateCustomerRequestDTO.getType())) {
            groups.add(CNPJGroup.class);
        }

        return groups;
    }

}
