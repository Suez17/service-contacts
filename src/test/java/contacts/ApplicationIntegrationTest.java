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
            .content(objectMapper.writeValueAsString(contactEntity(
                "firstName3",
                "lastName3",
                LocalDate.of(1995, 1, 1),
                "address3",
                "test3@mail.com",
                "0300000000"))))
        .andExpect(status().isCreated());
  }

  @Test
  void createContact_firstNameEmpty_badRequest() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/contacts")
            .content(objectMapper.writeValueAsString(contactEntity(
                "",
                "lastName3",
                LocalDate.of(1995, 1, 1),
                "address3",
                "test3@mail.com",
                "0300000000"))))
        .andExpect(status().isBadRequest());
  }

  private static ContactEntity contactEntity(
      String firstName, String lastName, LocalDate birthDate, String address, String email, String phoneNumber) {
    ContactEntity contact = new ContactEntity();
    contact.setFirstName(firstName);
    contact.setLastName(lastName);
    contact.setBirthDate(birthDate);
    contact.setAddress(address);
    contact.setEmail(email);
    contact.setPhoneNumber(phoneNumber);
    return contact;
  }
}
