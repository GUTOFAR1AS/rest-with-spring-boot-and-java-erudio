package br.com.erudio;

import br.com.erudio.model.Person;
import br.com.erudio.model.PersonDTO;
import br.com.erudio.mapper.PersonMapper;
import br.com.erudio.service.PersonServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonServiceTests {

    @Autowired
    private PersonServices personServices;

    @Test
    void contextLoads() {
    }

    @Test
    void testConvertPersonToDTO() {
        Person person = new Person();
        person.setId(1L);
        person.setName("John Doe");
        person.setPhone("123456789");
        person.setEmail("john@example.com");

        PersonDTO dto = PersonMapper.INSTANCE.personToPersonDTOWithLinks(person);

        assertNotNull(dto);
        assertEquals(person.getId(), dto.getId());
        assertNotNull(dto.getLinks()); // Verifica se o link foi adicionado
        assertFalse(dto.getLinks().isEmpty()); // Verifica se há links
    }
}
