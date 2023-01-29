package contacts.data.repository;

import contacts.data.model.Contact;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "contacts", path = "contacts")
public interface ContactRepository extends CrudRepository<Contact, UUID> {

  @RestResource(path = "firstName")
  List<Contact> findByFirstNameContains(@Param("firstName") String firstName);

  @RestResource(path = "lastName")
  List<Contact> findByLastNameContains(@Param("lastName") String lastName);

  @RestResource(path = "firstNameAndLastName")
  List<Contact> findByFirstNameContainsAndLastNameContains(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
