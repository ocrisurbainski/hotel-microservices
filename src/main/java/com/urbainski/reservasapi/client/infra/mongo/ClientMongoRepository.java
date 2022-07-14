package com.urbainski.reservasapi.client.infra.mongo;

import com.urbainski.reservasapi.client.Client;
import com.urbainski.reservasapi.client.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientMongoRepository implements ClientRepository {

    private final ClientSpringRepository clientSpringRepository;

    private final ClientMongoMapper mapper;

    @Autowired
    public ClientMongoRepository(ClientSpringRepository clientSpringRepository, ClientMongoMapper mapper) {
        this.clientSpringRepository = clientSpringRepository;
        this.mapper = mapper;
    }

    @Override
    public Client save(Client client) {
        var document = clientSpringRepository.save(mapper.toClientDocument(client));
        return mapper.toClient(document);
    }

}
