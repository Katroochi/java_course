package ru.stqa.pft.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.List;

public class RegistrationTests extends TestBase {

    @BeforeMethod
    public void startMailServer() throws MalformedURLException, ServiceException, RemoteException {
        skipIfNotFixed(0000001);
        app.mail().start();
    }

    private static final int number = (int) (Math.random() * 1000);

    public static String userName = String.format("user%s", number);
    public static String userPass = String.format("password%s", number);
    public static String userEmail = String.format("email%s@email.email", number);;

    @Test
    public void testRegistration() throws MessagingException, IOException{
        app.registration().start(userName, userEmail);
        List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
        String confirmationLink = app.registration().findConfirmationLink(mailMessages, userEmail);
        app.registration().finish(confirmationLink, userName, userPass);
        Assert.assertTrue(app.newSession().login(userName, userPass));
    }


    @AfterMethod(alwaysRun = true)
    public void stopMailServer(){
        app.mail().stop();
    }
}
