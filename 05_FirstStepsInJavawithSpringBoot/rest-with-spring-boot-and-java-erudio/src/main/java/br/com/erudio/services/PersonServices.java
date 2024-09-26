package br.com.erudio.service;

import br.com.erudio.model.Person;
import br.com.erudio.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServices {

    @Autowired
    private PersonRepository repository;

    // Método para encontrar todas as pessoas
    public List<Person> findAll() {
        return repository.findAll();
    }

    // Método para encontrar uma pessoa pelo ID
    public Person findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // Método para salvar uma nova pessoa (CREATE)
    public Person save(Person person) {
        return repository.save(person);
    }

    // Método para atualizar uma pessoa existente (UPDATE)
    public Person update(Long id, Person person) {
        if (!repository.existsById(id)) {
            return null; // Ou lançar uma exceção se a pessoa não existir
        }
        person.setId(id); // Define o ID para o objeto que está sendo atualizado
        return repository.save(person); // Salva a pessoa atualizada
    }

    // Método para deletar uma pessoa pelo ID (DELETE)
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
