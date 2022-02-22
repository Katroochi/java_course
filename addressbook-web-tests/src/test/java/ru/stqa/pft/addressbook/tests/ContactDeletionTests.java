package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase{

    @Test
    public void testContactDeletion () {
        app.getNavigationHelper().returnToHomePage();
        if (!app.getContactHelper().isThereAContact()){
            app.getContactHelper().createNewContact(new ContactData("Test1", "Testing1", null,null,null,null,null));
            app.getNavigationHelper().returnToHomePage();
        }
        app.getContactHelper().selectContact();
        app.getContactHelper().submitContactDeletion();
        app.getContactHelper().acceptDialogBox();
        app.getNavigationHelper().returnToHomePage();
        app.getNavigationHelper().logout();
    }
}
