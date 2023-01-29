package contacts.data.repository;

import contacts.data.model.Contact;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "contacts", path = "contacts")
public interface ContactRepository extends CrudRepository<Contact, UUID> {

  List<Contact> findByFirstNameContains(@Param("firstName") String firstName);

  List<Contact> findByLastNameContains(@Param("lastName") String lastName);

  List<Contact> findByAddressContains(@Param("address") String address);

  List<Contact> findByFirstNameContainsAndLastNameContains(@Param("firstName") String firstName, @Param("lastName") String lastName);

  List<Contact> findByFirstNameContainsAndAddressContains(@Param("firstName") String firstName, @Param("address") String address);

  List<Contact> findByLastNameContainsAndAddressContains(@Param("lastName") String lastName, @Param("address") String address);

  List<Contact> findByFirstNameContainsAndLastNameContainsAndAddressContains(
      @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("address") String address);
}
