package com.mindhub.homebanking.Services.Impl;

import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }
    @Override
    public Client getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }
    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }

}
