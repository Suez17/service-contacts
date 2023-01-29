package contacts;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import contacts.data.model.Contact;
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
class ContactsIntegrationTest {

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
  void findContactByFirstName() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/contacts/search/firstName?firstName=firstName2"))
        .andExpectAll(
            status().isOk(),
            jsonPath("$._embedded.contacts", hasSize(1)),
            jsonPath("$._embedded.contacts[0].firstName").value("firstName2"));
  }

  @Test
  void findContactByLastName() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/contacts/search/lastName?lastName=lastName2"))
        .andExpectAll(
            status().isOk(),
            jsonPath("$._embedded.contacts", hasSize(1)),
            jsonPath("$._embedded.contacts[0].firstName").value("firstName2"));
  }

  @Test
  void findContactByFirstNameAndLastName() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/contacts/search/firstNameAndLastName?firstName=firstName2&lastName=lastName2"))
        .andExpectAll(
            status().isOk(),
            jsonPath("$._embedded.contacts", hasSize(1)),
            jsonPath("$._embedded.contacts[0].firstName").value("firstName2"));
  }

  @Test
  void createContact() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/contacts")
            .content(objectMapper.writeValueAsString(contact())))
        .andExpect(status().isCreated());
  }

  @Test
  void createContact_firstNameEmpty_badRequest() throws Exception {
    // Given
    Contact contact = contact();
    contact.setFirstName("");

    // When + Then
    mockMvc.perform(MockMvcRequestBuilders.post("/contacts")
            .content(objectMapper.writeValueAsString(contact)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void deleteContact() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/contacts/bfdf4b3d-ede1-472b-94ad-96845a5820da"))
        .andExpect(status().isNoContent());
  }

  @Test
  void updateContact() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.put("/contacts/bfdf4b3d-ede1-472b-94ad-96845a5820da")
            .content(objectMapper.writeValueAsString(contact())))
        .andExpect(status().isNoContent());
  }

  private static Contact contact() {
    Contact contact = new Contact();
    contact.setFirstName("firstName3");
    contact.setLastName("lastName3");
    contact.setBirthDate(LocalDate.of(1995, 1, 1));
    contact.setAddress("address3");
    contact.setEmail("test3@mail.com");
    contact.setPhoneNumber("0300000000");
    return contact;
  }
}
