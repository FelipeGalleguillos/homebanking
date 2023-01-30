package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface ClientService {

    List<Client> getAllClients();

    Client getClientById(Long id);

    Client getClientByEmail(String email);

    void saveClient(Client client);
}
