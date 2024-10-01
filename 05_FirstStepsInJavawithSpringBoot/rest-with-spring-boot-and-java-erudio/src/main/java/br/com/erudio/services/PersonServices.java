package br.com.erudio.service;

import br.com.erudio.model.Person;
import br.com.erudio.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServices {

    @Autowired
    private PersonRepository repository;

    public List<Person> findAll() {
        return repository.findAll();
    }

    public Person findById(Long id) {
        Optional<Person> person = repository.findById(id);
        return person.orElse(null); // Retorna null se não encontrado
    }

    public Person save(Person person) {
        return repository.save(person);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Person update(Long id, Person person) {
        // Verifica se existe um registro antes de atualizar
        if (!repository.existsById(id)) {
            return null; // Retorna null se não encontrado
        }
        person.setId(id); // Define o ID para a atualização
        return repository.save(person);
    }
}
