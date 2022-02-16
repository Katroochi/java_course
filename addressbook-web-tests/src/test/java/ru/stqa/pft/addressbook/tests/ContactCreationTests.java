package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation() {
    app.getContactHelper().createNewContact(new ContactData("Test3", "Testing3", "Test13", "Saint-Petersburg", "8-999-123-45-63", "test3@test.digital",null), true);
    app.getNavigationHelper().returnToHomePage();
    app.getNavigationHelper().logout();
  }



}
