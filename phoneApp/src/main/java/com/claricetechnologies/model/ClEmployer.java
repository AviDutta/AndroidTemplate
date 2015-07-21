package com.claricetechnologies.model;

import io.realm.RealmObject;

public class ClEmployer extends RealmObject {
    private String companyName;
    private ClAddress companyAddress;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public ClAddress getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(ClAddress companyAddress) {
        this.companyAddress = companyAddress;
    }
}
