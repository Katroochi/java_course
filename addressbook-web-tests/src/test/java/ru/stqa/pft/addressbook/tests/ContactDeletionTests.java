package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase{

    @Test
    public void testContactDeletion () {
        app.goTo().returnToHomePage();
        if (!app.getContactHelper().isThereAContact()){
            app.getContactHelper().createNewContact(new ContactData("Test1", "Testing1", null,null,null,null,null));
            app.goTo().returnToHomePage();
        }
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact(before.size()-1);
        app.getContactHelper().submitContactDeletion();
        app.getContactHelper().acceptDialogBox();
        app.goTo().returnToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(),before.size() - 1);

        before.remove(before.size() - 1);
        Assert.assertEquals(after, before);

        app.goTo().logout();
    }
}
