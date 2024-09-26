package br.com.erudio.mapper;

import br.com.erudio.model.Person;
import br.com.erudio.model.PersonDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PersonMapperTests {

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    @Test
    public void testPersonToPersonDTO() {
        // Criação de um objeto Person
        Person person = new Person();
        person.setId(1L);
        person.setName("John Doe");
        person.setPhone("123456789");
        person.setEmail("john.doe@example.com");

        // Conversão para PersonDTO
        PersonDTO personDTO = personMapper.personToPersonDTO(person);

        // Verificações
        Assertions.assertEquals(person.getId(), personDTO.getId());
        Assertions.assertEquals(person.getName(), personDTO.getName());
        Assertions.assertEquals(person.getPhone(), personDTO.getPhone());
        Assertions.assertEquals(person.getEmail(), personDTO.getEmail());
    }

    @Test
    public void testPersonDTOToPerson() {
        // Criação de um objeto PersonDTO
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(1L);
        personDTO.setName("Jane Doe");
        personDTO.setPhone("987654321");
        personDTO.setEmail("jane.doe@example.com");

        // Conversão para Person
        Person person = personMapper.personDTOToPerson(personDTO);

        // Verificações
        Assertions.assertEquals(personDTO.getId(), person.getId());
        Assertions.assertEquals(personDTO.getName(), person.getName());
        Assertions.assertEquals(personDTO.getPhone(), person.getPhone());
        Assertions.assertEquals(personDTO.getEmail(), person.getEmail());
    }
}
