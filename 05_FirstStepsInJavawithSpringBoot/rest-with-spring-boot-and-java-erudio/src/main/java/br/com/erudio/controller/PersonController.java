package br.com.erudio.controller;

import br.com.erudio.model.Person;
import br.com.erudio.model.PersonDTO;
import br.com.erudio.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<Person>> findAll(@RequestParam(required = false) String format) {
        List<Person> persons = service.findAll();

        // Se o formato for xml, retorna XML
        if ("xml".equalsIgnoreCase(format)) {
            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_XML)
                .body(persons);
        }

        // Se o formato for yaml, retorna YAML
        if ("yaml".equalsIgnoreCase(format)) {
            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/x-yaml"))
                .body(persons);
        }

        // Caso contrário, retorna JSON
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(persons);
    }

    @GetMapping(value = "/{id}", produces = {
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE,
        "application/x-yaml"
    })
    public ResponseEntity<Person> findById(@PathVariable Long id, @RequestParam(required = false) String format) {
        Person person = service.findById(id);
        if (person == null) {
            return ResponseEntity.notFound().build();
        }

        // Se o formato for yaml, retorna YAML
        if ("yaml".equalsIgnoreCase(format)) {
            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/x-yaml"))
                .body(person);
        }

        return ResponseEntity.ok(person); // Simplificado para JSON
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
