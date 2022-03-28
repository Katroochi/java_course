package ru.stqa.pft.mantis.tests;

import biz.futureware.mantis.rpc.soap.client.*;
import org.hamcrest.core.Is;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.sound.midi.Soundbank;
import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Set;

public class SoapTests extends TestBase{

    @Test
    public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {
        Set<Project> projects = app.soap().getProjects();
        System.out.println(projects.size());
        for (Project project : projects){
            System.out.println(project.getName());
        }
    }

    @Test
    public void testCreateIssue() throws MalformedURLException, ServiceException, RemoteException {
        Set<Project> projects = app.soap().getProjects();
        Issue issue = new Issue()
                .withSummary("Test issue")
                .withDesription("Test issue desscription")
                .withProject(projects.iterator().next());
        Issue createIssue = app.soap().addIssue(issue);
        Assert.assertEquals(issue.getSummary(), createIssue.getSummary());
    }

    @Test
    public void testCheckState() throws MalformedURLException, ServiceException, RemoteException {
        int issueId = 1;
            MantisConnectPortType mc = new MantisConnectLocator()
                    .getMantisConnectPort(new URL("http://localhost/mantisbt-2.25.2/api/soap/mantisconnect.php"));
            IssueData newIssueData = mc.mc_issue_get("administrator", "root", BigInteger.valueOf(issueId));
        System.out.println(newIssueData.getStatus().getName());
        }
    }

