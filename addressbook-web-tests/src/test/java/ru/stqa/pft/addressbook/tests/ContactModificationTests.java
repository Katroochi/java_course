package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ContactModificationTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().whithFirstname("Test1343").whithLastname("Testing1433"));
        }
    }

    @Test
    public void testContactModification() {
        app.goTo().homePage();
        Contacts before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData()
                .whithId(modifiedContact.getId())
                .whithFirstname("Test1343")
                .whithLastname("Testing1433");
        app.contact().modify(modifiedContact, contact);
        app.goTo().homePage();
        Contacts after = app.contact().all();
        assertThat(after.size(), equalTo(before.size()));

        assertThat(after, equalTo(before
                .whithout(modifiedContact)
                .whithAdded(contact)));

        app.goTo().logout();
    }


}
