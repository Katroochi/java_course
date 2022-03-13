package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase{

    @Test
    public void testContactModification() {
        app.goTo().returnToHomePage();
        if (!app.getContactHelper().isThereAContact()){
            app.getContactHelper().createNewContact(new ContactData("Test1", "Testing1", null,null,null,null,null));
            app.goTo().returnToHomePage();
        }
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact(before.size() - 1);
        int contactId = before.get(before.size() - 1).getId();
        app.getContactHelper().initContactModification(contactId);
        ContactData contact = new ContactData(contactId,"Test1343", "Testing1433", "Test113", "Saint-Petersburg", "8-999-123-45-63", "test3@test.digital",null);
        app.getContactHelper().fillContactForm(contact, false);
        app.getContactHelper().submitContactModification();
        app.goTo().returnToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());

        before.remove(before.size() - 1);
        before.add(contact);
        Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);

        app.goTo().logout();
    }
}
