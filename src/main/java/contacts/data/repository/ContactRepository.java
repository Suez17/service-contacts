package contacts.data.repository;

import contacts.data.model.ContactEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<ContactEntity, UUID> {

}
