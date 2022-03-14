package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation() {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstname("Test43")
            .withLastname("Testing3")
            .withNickname("Test13")
            .withAddress("Saint-Petersburg, str. Morskaia, d.7, lit.A, fl.7")
            .withAddressSecondary("12345 Siverskii, 123 Divizii, 4-43")
            .withMobilePhone("8-999-123-45-63")
            .withHomePhone("8-(63)")
            .withWorkPhone("8-999 45-63")
            .withEmail("test3@test.digital")
            .withGroup("1");
    app.contact().create(contact);
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before
            .whithAdded(contact
                    .withId(after.stream().mapToInt((g)-> g.getId())
                            .max()
                            .getAsInt()))));

    app.goTo().logout();
  }

}
