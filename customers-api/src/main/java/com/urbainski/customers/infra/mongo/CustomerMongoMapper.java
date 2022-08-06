package com.urbainski.customers.infra.mongo;

import com.urbainski.customers.domain.Customer;

@org.mapstruct.Mapper(componentModel = "spring")
public interface CustomerMongoMapper {

    CustomerDocument toCustomerDocument(Customer customer);

    Customer toCustomer(CustomerDocument customerDocument);

}
