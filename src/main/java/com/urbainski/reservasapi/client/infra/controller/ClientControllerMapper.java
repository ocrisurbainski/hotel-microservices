package com.urbainski.reservasapi.client.infra.controller;

import com.urbainski.reservasapi.client.Client;
import com.urbainski.reservasapi.client.infra.controller.dto.CreateClientRequestDTO;
import com.urbainski.reservasapi.client.infra.controller.dto.CreateClientResponseDTO;
import com.urbainski.reservasapi.client.infra.controller.dto.GetAllClientResponseDTO;
import com.urbainski.reservasapi.client.infra.controller.dto.GetClientByIdResponseDTO;

@org.mapstruct.Mapper(componentModel = "spring")
public interface ClientControllerMapper {

    Client toClient(CreateClientRequestDTO dto);

    CreateClientResponseDTO toCreateClientResponseDTO(Client client);

    GetClientByIdResponseDTO toGetClientByIdResponseDTO(Client client);

    GetAllClientResponseDTO toGetAllClientResponseDTO(Client client);

}
