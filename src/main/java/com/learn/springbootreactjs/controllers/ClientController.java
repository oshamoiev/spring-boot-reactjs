package com.learn.springbootreactjs.controllers;

import com.learn.springbootreactjs.data.ClientRepository;
import com.learn.springbootreactjs.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    @GetMapping("/{id}")
    public Client getClient(@PathVariable Long id) {
        return clientRepository.findById(id).orElseThrow();
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) throws URISyntaxException {
        Client savedClient = clientRepository.save(client);
        return ResponseEntity.created(new URI("/clients" + savedClient.getId())).body(savedClient);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@RequestBody Client client, @PathVariable Long id) {
        Client currentClient = clientRepository.findById(id).orElseThrow();
        currentClient.setName(client.getName());
        currentClient.setEmail(client.getEmail());
        Client savedClient = clientRepository.save(currentClient);

        return ResponseEntity.ok(savedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Client> deleteClient(@PathVariable Long id) {
        clientRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
