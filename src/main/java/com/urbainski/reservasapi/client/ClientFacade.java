package com.urbainski.reservasapi.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientFacade implements ClientOperation {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientFacade(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

}
