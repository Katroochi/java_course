package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ContactDeletionTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();
        if (!app.contact().isThereAContact()) {
            app.contact().create(new ContactData().whithFirstname("Test1").whithLastname("Testing1"));

        }
    }

    @Test
    public void testContactDeletion () {
        app.goTo().homePage();
        Contacts before = app.contact().all();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);
        app.goTo().homePage();
        Contacts after = app.contact().all();
        assertThat(after.size(),equalTo(before.size() - 1));

        assertThat(after, equalTo(before.whithout(deletedContact)));

        app.goTo().logout();
    }
}
