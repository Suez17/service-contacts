package contacts;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import contacts.data.model.ContactEntity;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@ActiveProfiles("test")
@Sql("/sql/insert-contacts.sql")
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class ApplicationIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void findAllContacts() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/contacts"))
        .andExpectAll(
            status().isOk(),
            jsonPath("$._embedded.contacts", hasSize(2)),
            jsonPath("$._embedded.contacts[0].firstName").value("firstName1"),
            jsonPath("$._embedded.contacts[1].firstName").value("firstName2"));
  }

  @Test
  void createContact() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/contacts")
            .content(objectMapper.writeValueAsString(contactEntity())))
        .andExpect(status().isCreated());
  }

  @Test
  void createContact_firstNameEmpty_badRequest() throws Exception {
    // Given
    ContactEntity contactEntity = contactEntity();
    contactEntity.setFirstName("");

    // When + Then
    mockMvc.perform(MockMvcRequestBuilders.post("/contacts")
            .content(objectMapper.writeValueAsString(contactEntity)))
        .andExpect(status().isBadRequest());
  }

  private static ContactEntity contactEntity() {
    ContactEntity contact = new ContactEntity();
    contact.setFirstName("firstName3");
    contact.setLastName("lastName3");
    contact.setBirthDate(LocalDate.of(1995, 1, 1));
    contact.setAddress("address3");
    contact.setEmail("test3@mail.com");
    contact.setPhoneNumber("0300000000");
    return contact;
  }
}
