package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressesTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.goTo().homePage();
            app.contact().create(new ContactData()
                    .withFirstname("Test1343")
                    .withLastname("Testing1433")
                    .withAddress("Saint-Petersburg, str. Morskaia, d.7, lit.A, fl.7")
                    .withAddressSecondary("12345 Siverskii, 123 Divizii, 4-43"));
        }
    }

    @Test
    public void testContactAddress(){
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(cleaned(contact.getAddress()), equalTo(cleaned(contactInfoFromEditForm.getAddress())));
    }

    public static String cleaned(String address){
        return address.replaceAll("\\s","").replaceAll("[-().,]","");
    }
}

