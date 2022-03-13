package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver driver) {
        super(driver);
    }

    public void submitContactCreation() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("nickname"), contactData.getNickname());
        type(By.name("address"), contactData.getAddress());
        type(By.name("mobile"), contactData.getMobile());
        type(By.name("email"), contactData.getEmail());
        type(By.name("address2"), contactData.getAddress());
        if (creation) {
            List<WebElement> groups = new Select(driver.findElement(By.name("new_group"))).getOptions();
            if (groups.size() > 1) {
                new Select(driver.findElement(By.name("new_group"))).selectByIndex(1);
            }
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void selectContactById(int id) {
        driver.findElement(By.cssSelector("input[value = '" + id + "']"));
    }

    public void initContactModification(int id) {
        click(By.xpath("//a[@href = 'edit.php?id=" + id + "']"));
    }

    public void submitContactModification() {
        click(By.xpath("//div[@id='content']/form/input[22]"));
    }

    public void submitContactDeletion() {
        click(By.xpath("//input[@value='Delete']"));
    }

    public void acceptDialogBox() {
        driver.switchTo().alert().accept();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public void create(ContactData contact) {
        initContactCreation();
        fillContactForm(contact, true);
        submitContactCreation();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        submitContactDeletion();
        acceptDialogBox();
    }

    public void modify(ContactData modifiedContact, ContactData contact) {
        selectContactById(contact.getId());
        initContactModification(modifiedContact.getId());
        fillContactForm(contact, false);
        submitContactModification();
    }

    public Contacts all() {
        Contacts contacts = new Contacts();
        List<WebElement> elements = driver.findElements(By.name("entry"));
        for (WebElement element : elements){
            List<WebElement> cells = element.findElements(By.tagName("td"));
            int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
            String lastname = cells.get(1).getText();
            String firstname = cells.get(2).getText();
            contacts.add(new ContactData().whithId(id)
                    .whithFirstname(firstname)
                    .whithLastname(lastname));
        }
        return contacts;
    }
}
