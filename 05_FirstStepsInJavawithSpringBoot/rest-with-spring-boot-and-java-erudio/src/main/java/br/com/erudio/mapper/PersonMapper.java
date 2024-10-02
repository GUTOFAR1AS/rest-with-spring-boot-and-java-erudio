package br.com.erudio.mapper;

import br.com.erudio.model.Person;
import br.com.erudio.model.PersonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import br.com.erudio.controller.PersonController;

@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    PersonDTO personToPersonDTO(Person person);
    Person personDTOToPerson(PersonDTO personDTO);

    default PersonDTO personToPersonDTOWithLinks(Person person) {
        PersonDTO dto = personToPersonDTO(person);

        // Adiciona links HATEOAS
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).findById(person.getId())).withSelfRel();
        dto.add(selfLink);

        return dto;
    }
}
