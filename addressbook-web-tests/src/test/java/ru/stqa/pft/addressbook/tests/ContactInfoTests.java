package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactInfoTests extends TestBase{
    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.goTo().homePage();
            app.contact().create(new ContactData()
                    .withFirstname("Test1343")
                    .withLastname("Testing1433")
                    .withWorkPhone("+2938493")
                    .withHomePhone("2-3-4-5-6-7")
                    .withMobilePhone("22 23 34 45")
                    .withHomePhone2("(22) 23 34 45")
                    .withEmail("test@email.com")
                    .withEmail2("test2@email.com")
                    .withEmail3("test3@email.com").withAddress("Saint-Petersburg, str. Morskaia, d.7, lit.A, fl.7")
                    .withAddressSecondary("12345 Siverskii, 123 Divizii, 4-43"));
        }
    }

    @Test
    public void testContactInfo(){
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
        assertThat(cleaned(contact.getAddress()), equalTo(cleaned(contactInfoFromEditForm.getAddress())));
    }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((s) -> !s.equals(""))
                .map(ContactInfoTests::cleanedEmails)
                .collect(Collectors.joining("\n"));
    }

    private String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
                .stream().filter((s) -> !s.equals(""))
                .map(ContactInfoTests::cleanedPhones)
                .collect(Collectors.joining("\n"));
    }

    public static String cleanedPhones(String phone){

        return phone.replaceAll("\\s","").replaceAll("[-()]","");
    }

    public static String cleanedEmails(String email){
        return email.replaceAll("\\s","");
    }

    public static String cleaned(String address){
        return address.replaceAll("[-().,]","");}
}

