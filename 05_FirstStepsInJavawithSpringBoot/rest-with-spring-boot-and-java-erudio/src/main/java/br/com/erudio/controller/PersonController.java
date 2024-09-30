package br.com.erudio.controller;

import br.com.erudio.model.Person;
import br.com.erudio.model.PersonDTO;
import br.com.erudio.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private br.com.erudio.service.PersonServices service;

    @Autowired
    private PersonMapper personMapper;

    @GetMapping(produces = {
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE,
        "application/x-yaml"
    })
    public ResponseEntity<List<PersonDTO>> findAll(@RequestParam(required = false) String format) {
        List<Person> persons = service.findAll();
        List<PersonDTO> personDTOs = persons.stream()
            .map(personMapper::personToPersonDTO)
            .toList();

        // Adiciona links a cada DTO
        for (PersonDTO personDTO : personDTOs) {
            addLinks(personDTO);
        }

        // Retorna a lista de DTOs com links
        return ResponseEntity.ok(personDTOs);
    }

    @GetMapping(value = "/{id}", produces = {
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE,
        "application/x-yaml"
    })
    public ResponseEntity<PersonDTO> findById(@PathVariable Long id, @RequestParam(required = false) String format) {
        Person person = service.findById(id);
        if (person == null) {
            return ResponseEntity.notFound().build();
        }

        PersonDTO personDTO = personMapper.personToPersonDTO(person);
        addLinks(personDTO); // Adiciona links

        return ResponseEntity.ok(personDTO);
    }

    // Método para adicionar links HATEOAS ao PersonDTO
    private void addLinks(PersonDTO personDTO) {
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).findById(personDTO.getId(), null)).withSelfRel();
        Link updateLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).update(personDTO.getId(), null)).withRel("update");
        Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).delete(personDTO.getId())).withRel("delete");
        personDTO.add(selfLink, updateLink, deleteLink);
    }

    @PostMapping
    public ResponseEntity<Person> save(@RequestBody Person person) {
        Person personSaved = service.save(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(personSaved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> update(@PathVariable Long id, @RequestBody Person person) {
        Person updatedPerson = service.update(id, person);
        return updatedPerson != null ? ResponseEntity.ok(updatedPerson) : ResponseEntity.notFound().build();
    }

    @PostMapping("/convert")
    public ResponseEntity<PersonDTO> convertPerson(@RequestBody PersonDTO personDTO) {
        Person person = personMapper.personDTOToPerson(personDTO);
        return ResponseEntity.ok(personMapper.personToPersonDTO(person)); // Simplificado
    }
}
