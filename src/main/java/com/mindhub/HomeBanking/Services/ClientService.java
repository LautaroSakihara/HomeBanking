package com.mindhub.HomeBanking.Services;

import com.mindhub.HomeBanking.dtos.ClientDTO;
import com.mindhub.HomeBanking.models.Client;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientService {
    List<ClientDTO> getClientsDTO();
    Client getClientCurrent (Authentication authentication);
    ClientDTO getClientDTO(long id);
    void saveClient(Client client);
    Client getClientByEmail(String email);
}
