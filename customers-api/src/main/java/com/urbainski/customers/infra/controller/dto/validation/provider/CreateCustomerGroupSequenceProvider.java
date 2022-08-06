package com.urbainski.customers.infra.controller.dto.validation.provider;

import com.urbainski.commons.bean.validation.group.CNPJGroup;
import com.urbainski.commons.bean.validation.group.CPFGroup;
import com.urbainski.customers.domain.CustomerType;
import com.urbainski.customers.infra.controller.dto.CreateCustomerRequestDTO;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateCustomerGroupSequenceProvider implements DefaultGroupSequenceProvider<CreateCustomerRequestDTO> {

    @Override
    public List<Class<?>> getValidationGroups(CreateCustomerRequestDTO createCustomerRequestDTO) {

        List<Class<?>> groups = new ArrayList<>();
        groups.add(CreateCustomerRequestDTO.class);

        if (Objects.isNull(createCustomerRequestDTO)) {
            return groups;
        }

        if (CustomerType.FISICA.equals(createCustomerRequestDTO.getType())) {
            groups.add(CPFGroup.class);
        }

        if (CustomerType.JURIDICA.equals(createCustomerRequestDTO.getType())) {
            groups.add(CNPJGroup.class);
        }

        return groups;
    }

}
