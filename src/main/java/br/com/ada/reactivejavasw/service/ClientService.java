package br.com.ada.reactivejavasw.service;

import br.com.ada.reactivejavasw.converter.ClientConverter;
import br.com.ada.reactivejavasw.dto.ClientDto;
import br.com.ada.reactivejavasw.dto.ResponseDto;
import br.com.ada.reactivejavasw.model.Client;
import br.com.ada.reactivejavasw.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ClientService {

    @Autowired
    private ClientConverter clientConverter;

    @Autowired
    private ClientRepository clientRepository;

    public Mono<ResponseDto> create(ClientDto clientDto) {
        Client client = clientConverter.toClient(clientDto);

        Mono<Client> clientMono = clientRepository.save(client);

        return clientMono
                .map((clientDocument) -> new ResponseDto("Cliente cadastrado com sucesso",
                        clientConverter.toClientDto(clientDocument),
                        LocalDateTime.now()))
                .onErrorReturn(new ResponseDto("Erro ao tentar cadastrar o cliente", new ClientDto(),
                        LocalDateTime.now()));
    }

    public Flux<ResponseDto<ClientDto>> getAll() {
        Flux<Client> clientFlux = clientRepository.findAll();
        return clientFlux
                .map(client -> new ResponseDto("Listagem de clientes retornada com sucesso!",
                        clientConverter.toClientDto(client),
                        LocalDateTime.now()));
    }

    public Mono<ResponseDto<ClientDto>> findByEmail(String email){
        Mono<Client> clientMono = clientRepository.findByEmail(email);
        return clientMono
                .map(client -> new ResponseDto("Busca por email retornado com sucesso",
                        clientConverter.toClientDto(client),
                        LocalDateTime.now()));
    }

    public Mono<ResponseDto> update(ClientDto clientDto){
        Mono<Client> clientMono = clientRepository.findByEmail(clientDto.getEmail());

        return clientMono.flatMap((existingProduct) -> {
            existingProduct.setEmail(clientDto.getEmail());
            existingProduct.setName(clientDto.getName());
            existingProduct.setAge(clientDto.getAge());
            return clientRepository.save(existingProduct);
        }).map(client -> new ResponseDto<>("Cliente alterado com sucesso!",
                clientConverter.toClientDto(client),
                LocalDateTime.now()));
    }

    public Mono<ResponseDto> delete(String id){
        return this.clientRepository
                .deleteById(id)
                .map((client)-> new ResponseDto<>("Cliente removido com sucesso!",
                null,
                LocalDateTime.now()));
    }
}
