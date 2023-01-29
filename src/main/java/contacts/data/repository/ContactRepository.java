package contacts.data.repository;

import com.querydsl.core.types.dsl.StringExpression;
import contacts.data.model.Contact;
import contacts.data.model.QContact;
import java.util.UUID;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "contacts", path = "contacts")
public interface ContactRepository
    extends PagingAndSortingRepository<Contact, UUID>, QuerydslPredicateExecutor<Contact>, QuerydslBinderCustomizer<QContact> {

  @Override
  default void customize(QuerydslBindings bindings, QContact contact) {
    bindings.bind(contact.firstName).first(StringExpression::contains);
    bindings.bind(contact.lastName).first(StringExpression::contains);
  }
}
