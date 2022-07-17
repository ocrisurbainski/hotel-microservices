package com.urbainski.reservasapi.client.infra.controller;

import com.urbainski.reservasapi.client.Client;
import com.urbainski.reservasapi.client.infra.controller.dto.*;

@org.mapstruct.Mapper(componentModel = "spring")
public interface ClientControllerMapper {

    Client toClient(CreateClientRequestDTO dto);

    CreateClientResponseDTO toCreateClientResponseDTO(Client client);

    GetClientByIdResponseDTO toGetClientByIdResponseDTO(Client client);

    GetClientByDocumentResponseDTO toGetClientByDocumentResponseDTO(Client client);

    GetClientByNameResponseDTO toGetClientByNameResponseDTO(Client client);

    GetAllClientResponseDTO toGetAllClientResponseDTO(Client client);

}
