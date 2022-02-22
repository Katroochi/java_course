package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Collections;
import java.util.List;

public class ContactHelper extends HelperBase{

    public ContactHelper(WebDriver driver) {
        super(driver);
    }

    public void submitContactCreation() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("middlename"), contactData.getMiddlename());
        type(By.name("nickname"), contactData.getNickname());
        type(By.name("address"), contactData.getAddress());
        type(By.name("mobile"), contactData.getMobile());
        type(By.name("email"), contactData.getEmail());
        type(By.name("address2"), contactData.getAddress());
        List<WebElement> groups = new Select (driver.findElement(By.name("new_group"))).getOptions();
        if (isElementPresent(By.name("new_group"))){
            if(groups.size()>1){
                new Select (driver.findElement(By.name("new_group"))).selectByIndex(1); }
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void selectContact() { click(By.name("selected[]")); }

    public void initContactModification() { click(By.xpath("//img[@alt='Edit']")); }

    public void submitContactModification() { click(By.xpath("//div[@id='content']/form/input[22]")); }

    public void submitContactDeletion() { click(By.xpath("//input[@value='Delete']"));}

    public void acceptDialogBox() { driver.switchTo().alert().accept();}

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public void createNewContact(ContactData contact) {
        initContactCreation();
        fillContactForm(contact, true);
        submitContactCreation();
    }
}
