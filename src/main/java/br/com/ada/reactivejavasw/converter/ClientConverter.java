package br.com.ada.reactivejavasw.converter;

import br.com.ada.reactivejavasw.dto.ClientDto;
import br.com.ada.reactivejavasw.model.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientConverter {

    public ClientDto toClientDto(Client client) {
        return new ClientDto(client.getName(), client.getAge(), client.getEmail());
    }

    public Client toClient(ClientDto clientDto) {
        return new Client(clientDto.getName(), clientDto.getAge(), clientDto.getEmail());
    }
}
