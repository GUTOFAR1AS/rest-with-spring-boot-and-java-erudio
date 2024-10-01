package br.com.erudio.controller;

import br.com.erudio.model.Person;
import br.com.erudio.model.PersonDTO;
import br.com.erudio.mapper.PersonMapper;
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

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<EntityModel<Person>>> findAll(@RequestParam(required = false) String format) {
        List<Person> persons = service.findAll();

        List<EntityModel<Person>> entityModels = persons.stream()
            .map(person -> EntityModel.of(person,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).findById(person.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).findAll(null)).withRel("persons"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).save(person)).withRel("create")))
            .collect(Collectors.toList());

        return ResponseEntity.ok(entityModels);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EntityModel<Person>> findById(@PathVariable Long id) {
        Person person = service.findById(id);
        if (person == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Person> entityModel = EntityModel.of(person,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).findById(id)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).findAll(null)).withRel("persons"),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).update(id, person)).withRel("update"),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).delete(id)).withRel("delete"));

        return ResponseEntity.ok(entityModel);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Person>> save(@RequestBody Person person) {
        Person personSaved = service.save(person);
        EntityModel<Person> entityModel = EntityModel.of(personSaved,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).findById(personSaved.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).findAll(null)).withRel("persons"));

        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Person>> update(@PathVariable Long id, @RequestBody Person person) {
        Person updatedPerson = service.update(id, person);
        if (updatedPerson == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Person> entityModel = EntityModel.of(updatedPerson,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).findById(updatedPerson.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).findAll(null)).withRel("persons"));

        return ResponseEntity.ok(entityModel);
    }

    @PostMapping("/convert")
    public ResponseEntity<PersonDTO> convertPerson(@RequestBody PersonDTO personDTO) {
        Person person = personMapper.personDTOToPerson(personDTO);
        return ResponseEntity.ok(personMapper.personToPersonDTO(person));
    }
}
