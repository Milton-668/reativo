package br.com.ada.reactivejavasw.controller;

import br.com.ada.reactivejavasw.dto.ClientDto;
import br.com.ada.reactivejavasw.dto.ResponseDto;
import br.com.ada.reactivejavasw.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.format.DecimalStyle;

@RestController
@RequestMapping("api/products")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(description = "Create a client",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody())
    public Mono<ResponseDto> create(@RequestBody ClientDto clientDto) {
        return clientService.create(clientDto);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(description = "Find all clients")
    public Flux<ResponseDto<ClientDto>> getAll() {
        return clientService.getAll();
    }

    @GetMapping("{email}")
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(description = "Find by email of client")
    public Mono<ResponseDto<ClientDto>> findByEmail(@PathVariable("email") String email) {
        return clientService.findByEmail(email);
    }
    @PutMapping
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(description = "Update a client")
    public Mono<ResponseDto> update(@RequestBody ClientDto clientDto){
        return clientService.update(clientDto);
    }


}
