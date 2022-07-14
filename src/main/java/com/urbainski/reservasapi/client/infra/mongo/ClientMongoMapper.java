package com.urbainski.reservasapi.client.infra.mongo;

import com.urbainski.reservasapi.client.Client;

@org.mapstruct.Mapper(componentModel = "spring")
public interface ClientMongoMapper {

    ClientDocument toClientDocument(Client client);

    Client toClient(ClientDocument clientDocument);

}
