package contacts.data.repository;

import contacts.data.model.ContactEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "contacts", path = "contacts")
public interface ContactRepository extends CrudRepository<ContactEntity, UUID> {

  List<ContactEntity> findByFirstNameContains(@Param("firstName") String firstName);

  List<ContactEntity> findByLastNameContains(@Param("lastName") String lastName);

  List<ContactEntity> findByAddressContains(@Param("address") String address);

  List<ContactEntity> findByFirstNameContainsAndLastNameContains(@Param("firstName") String firstName, @Param("lastName") String lastName);

  List<ContactEntity> findByFirstNameContainsAndAddressContains(@Param("firstName") String firstName, @Param("address") String address);

  List<ContactEntity> findByLastNameContainsAndAddressContains(@Param("lastName") String lastName, @Param("address") String address);

  List<ContactEntity> findByFirstNameContainsAndLastNameContainsAndAddressContains(
      @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("address") String address);
}
