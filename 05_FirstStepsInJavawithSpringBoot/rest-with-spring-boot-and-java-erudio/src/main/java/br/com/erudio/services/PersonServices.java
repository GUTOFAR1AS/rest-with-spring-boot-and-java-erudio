package br.com.erudio.services;

import br.com.erudio.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private static AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonServices.class.getName());
    private List<Person> persons = new ArrayList<>(); // Armazenamento temporário

    public Person findById(Long id) {
        logger.info("Finding one person with ID: " + id);
        return persons.stream()
            .filter(person -> person.getId().equals(id))
            .findFirst()
            .orElse(null); // Retorna null se não encontrado
    }

    public List<Person> findAll() {
        return persons; // Retorna a lista de pessoas
    }

    public void addPerson(Person person) {
        person.setId(counter.incrementAndGet()); // Gera um ID único
        persons.add(person); // Adiciona a pessoa à lista
        logger.info("Added person: " + person);
    }
}
