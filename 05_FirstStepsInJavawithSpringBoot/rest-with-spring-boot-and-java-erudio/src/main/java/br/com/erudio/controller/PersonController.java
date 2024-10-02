package br.com.erudio.controller;

import br.com.erudio.model.Person;
import br.com.erudio.model.PersonDTO;
import br.com.erudio.mapper.PersonMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private br.com.erudio.service.PersonServices service;

    @Autowired
    private PersonMapper personMapper;

    @Operation(summary = "Listar todos os clientes",
        description = "Retorna uma lista de todos os clientes cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso.")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<EntityModel<PersonDTO>>> findAll(@RequestParam(required = false) String format) {
        List<Person> persons = service.findAll();

        List<EntityModel<PersonDTO>> entityModels = persons.stream()
            .map(person -> EntityModel.of(personMapper.personToPersonDTOWithLinks(person)))
            .collect(Collectors.toList());

        return ResponseEntity.ok(entityModels);
    }

    @Operation(summary = "Buscar cliente pelo ID",
        description = "Retorna os detalhes de um cliente específico pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Cliente encontrado.")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EntityModel<PersonDTO>> findById(@PathVariable Long id) {
        Person person = service.findById(id);
        if (person == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<PersonDTO> entityModel = EntityModel.of(personMapper.personToPersonDTOWithLinks(person));
        return ResponseEntity.ok(entityModel);
    }

    @Operation(summary = "Adicionar novo cliente",
        description = "Cadastra um novo cliente na base de dados.")
    @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso.")
    @PostMapping
    public ResponseEntity<EntityModel<PersonDTO>> save(@RequestBody Person person) {
        Person personSaved = service.save(person);
        EntityModel<PersonDTO> entityModel = EntityModel.of(personMapper.personToPersonDTOWithLinks(personSaved));
        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
    }

    @Operation(summary = "Atualizar cliente",
        description = "Atualiza os dados de um cliente existente.")
    @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<PersonDTO>> update(@PathVariable Long id, @RequestBody Person person) {
        Person updatedPerson = service.update(id, person);
        if (updatedPerson == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<PersonDTO> entityModel = EntityModel.of(personMapper.personToPersonDTOWithLinks(updatedPerson));
        return ResponseEntity.ok(entityModel);
    }

    @Operation(summary = "Remover cliente",
        description = "Remove um cliente da base de dados pelo ID.")
    @ApiResponse(responseCode = "204", description = "Cliente removido com sucesso.")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Converter cliente",
        description = "Converte um PersonDTO em Person e retorna o PersonDTO correspondente.")
    @ApiResponse(responseCode = "200", description = "Conversão realizada com sucesso.")
    @PostMapping("/convert")
    public ResponseEntity<PersonDTO> convertPerson(@RequestBody PersonDTO personDTO) {
        Person person = personMapper.personDTOToPerson(personDTO);
        return ResponseEntity.ok(personMapper.personToPersonDTO(person));
    }
}
