package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

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
        attach(By.name("photo"), contactData.getPhoto());
        type(By.name("address"), contactData.getAddress());
        type(By.name("address2"), contactData.getAddressSecondary());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("phone2"), contactData.getHomePhone2());
        type(By.name("work"), contactData.getWorkPhone());
        type(By.name("email"), contactData.getEmail());
        type(By.name("address2"), contactData.getAddress());
        if (creation) {
            if (contactData.getGroups().size() > 0 ){
                Assert.assertTrue(contactData.getGroups().size() == 1);
                new Select(driver.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
            }
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void selectContactById(int id) {
        driver.findElement(By.cssSelector("input[id = '" + id + "']"));
    }

    public void initContactModificationById(int id) {
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

    public int count() {
        return driver.findElements(By.name("selected[]")).size();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public void selectGroupToRemove(int id) {
        driver.findElement(By.xpath("//select[@name='group']//option[@value='" + id + "']")).click();
    }

    public void removeFromGroup(ContactData contact, GroupData group) {
        selectGroupToRemove(group.getId());
        selectById(contact.getId());
        submitRemoveContactFromGroup();
    }

    public void submitRemoveContactFromGroup(){click(By.xpath("//input[@name='remove']"));}

    public void addToGroup(ContactData contact, GroupData group) {
        selectById(contact.getId());
        selectGroupToAddition(group.getId());
        addTo();
    }

    public ContactData selectById(int id) {
        driver.findElement(By.cssSelector("input[id='" + id + "']")).click();
        return null;
    }

    private void addTo() {
        driver.findElement(By.name("add")).click();
    }

    private void selectGroupToAddition(int id) {
        driver.findElement(By.xpath("//select[@name='to_group']//option[@value='" + id + "']")).click();

    }

    public ContactData infoFromEditForm(ContactData contact) {
        initContactModificationById(contact.getId());
        String firstname = driver.findElement(By.name("firstname")).getAttribute("value");
        String lastname = driver.findElement(By.name("lastname")).getAttribute("value");
        String address = driver.findElement(By.name("address")).getAttribute("value");
        String addressSecondary = driver.findElement(By.name("address2")).getAttribute("value");
        String email = driver.findElement(By.name("email")).getAttribute("value");
        String email2 = driver.findElement(By.name("email2")).getAttribute("value");
        String email3 = driver.findElement(By.name("email3")).getAttribute("value");
        String home = driver.findElement(By.name("home")).getAttribute("value");
        String home2 = driver.findElement(By.name("phone2")).getAttribute("value");
        String mobile = driver.findElement(By.name("mobile")).getAttribute("value");
        String work = driver.findElement(By.name("work")).getAttribute("value");
        driver.navigate().back();
        return new ContactData()
                .withId(contact.getId())
                .withFirstname(firstname)
                .withLastname(lastname)
                .withAddress(address)
                .withAddressSecondary(addressSecondary)
                .withEmail(email)
                .withEmail2(email2)
                .withEmail3(email3)
                .withHomePhone(home)
                .withHomePhone2(home2)
                .withMobilePhone(mobile)
                .withWorkPhone(work);
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
        initContactModificationById(modifiedContact.getId());
        fillContactForm(contact, false);
        submitContactModification();
    }

    private Contacts contactCache = null;

    public Contacts all() {
        if(contactCache != null){
            return new Contacts(contactCache);
        }
        Contacts contactCache = new Contacts();
        List<WebElement> elements = driver.findElements(By.name("entry"));
        for (WebElement element : elements){
            List<WebElement> cells = element.findElements(By.tagName("td"));
            int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
            String lastname = cells.get(1).getText();
            String firstname = cells.get(2).getText();
            String address = cells.get(3).getText();
            String allEmails = cells.get(4).getText();
            String allPhones = cells.get(5).getText();
            contactCache.add(new ContactData().withId(id)
                    .withFirstname(firstname)
                    .withLastname(lastname)
                    .withAddress(address)
                    .withAllEmails(allEmails)
                    .withAllPhones(allPhones));
        }
        return new Contacts(contactCache);
    }
}
