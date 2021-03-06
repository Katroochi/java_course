package ru.stqa.pft.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SoapHelper {
    private ApplicationManager app;

    public SoapHelper(ApplicationManager app) {
        this.app = app;
    }

    public Set<Project> getProjects() throws MalformedURLException, RemoteException, ServiceException {
        MantisConnectPortType mc = app.soap().getMantisConnect();
        ProjectData[] projects = mc.mc_projects_get_user_accessible("administrator", "root");
       return Arrays.asList(projects).stream().map((p) -> new Project()
                .withId(p.getId().intValue())
                .withName(p.getName()))
                .collect(Collectors.toSet());
    }

    public MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException {
        return new MantisConnectLocator()
                .getMantisConnectPort(new URL(app.getProperty("mantis.SoapConnectUrl")));
    }

    public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = getMantisConnect();
        String[] categories = mc.mc_project_get_categories(app.getProperty("mantis.adminLogin"), app.getProperty("mantis.adminPassword"), BigInteger.valueOf(issue.getProject().getId()));
        IssueData issueData = new IssueData();
        issueData.setSummary(issue.getSummary());
        issueData.setCategory(categories[0]);
        issueData.setDescription(issue.getDesription());
        issueData.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
        BigInteger issueId = mc.mc_issue_add(app.getProperty("mantis.adminLogin"), app.getProperty("mantis.adminPassword"), issueData);
        IssueData newIssueData = mc.mc_issue_get(app.getProperty("mantis.adminLogin"), app.getProperty("mantis.adminPassword"), issueId);
        return new Issue()
                .withId(newIssueData.getId().intValue())
                .withSummary(newIssueData.getSummary())
                .withDesription(newIssueData.getDescription())
                .withProject(new Project()
                        .withId(newIssueData.getProject().getId().intValue())
                        .withName(newIssueData.getProject().getName()));
    }
}
