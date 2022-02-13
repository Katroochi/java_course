package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase{

    @Test
    public void testContactModification() {
        app.getNavigationHelper().returnToHomePage();
        app.getContactHelper().selectContact();
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("Test3", "Testing3", "Test13", "Saint-Petersburg", "8-999-123-45-63", "test3@test.digital"));
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().returnToHomePage();
        app.getNavigationHelper().logout();
    }
}
