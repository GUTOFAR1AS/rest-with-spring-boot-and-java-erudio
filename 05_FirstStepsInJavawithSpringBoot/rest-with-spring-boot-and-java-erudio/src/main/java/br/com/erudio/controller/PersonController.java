package br.com.erudio.controller;

import br.com.erudio.model.Person;
import br.com.erudio.model.PersonDTO;
import br.com.erudio.mapper.PersonMapper; // Certifique-se de importar o mapper
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private br.com.erudio.service.PersonServices service;

    @Autowired
    private PersonMapper personMapper; // Adicione a injeção do mapper

    @GetMapping
    public List<Person> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable Long id) {
        Person person = service.findById(id);
        return person != null ? ResponseEntity.ok(person) : ResponseEntity.notFound().build();
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
        // Aqui você pode salvar o person no banco de dados, se necessário
        // Exemplo: personSaved = service.save(person);

        // Retorna o PersonDTO convertido de volta
        return new ResponseEntity<>(personMapper.personToPersonDTO(person), HttpStatus.OK);
    }
}
