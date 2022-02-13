package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation() {
    app.getContactHelper().initContactCreation();
    app.getContactHelper().fillContactForm(new ContactData("Test1", "Testing1", "Test11", "Saint-Petersburg", "8-999-123-45-67", "test1@test.digital"));
    app.getContactHelper().submitContactCreation();
    app.getNavigationHelper().returnToHomePage();
    app.getNavigationHelper().logout();
  }



}
