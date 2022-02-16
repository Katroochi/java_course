package ru.stqa.pft.addressbook.model;

public class ContactData {
    private final String firstname;
    private final String middlename;
    private final String nickname;
    private final String address;
    private final String mobile;
    private final String email;
    private String group;

    public ContactData(String firstname, String middlename, String nickname, String address, String mobile, String email, String group) {
        this.firstname = firstname;
        this.middlename = middlename;
        this.nickname = nickname;
        this.address = address;
        this.mobile = mobile;
        this.email = email;
        this.group = group;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAddress() {
        return address;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getGroup() { return group; }
}
