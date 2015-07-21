package com.claricetechnologies.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

// Your model just have to extend RealmObject.
// This will inherit an annotation which produces proxy getters and setters for all fields.
public class ClPerson extends RealmObject {

    // All fields are by default persisted.
    private int id;
    private String name;
    private int age;
    private ClAddress address;
    // Other objects in a one-to-one relation must also subclass RealmObject
    private ClEmployer employer;

    // One-to-many relations is simply a RealmList of the objects which also subclass RealmObject
    private RealmList<ClAddress> addresses;

    // You can instruct Realm to ignore a field and not persist it.
    @Ignore
    private int height;

    // The standard getters and setters your IDE generates are fine.
    // Realm will overload them and code inside them is ignored.
    // So if you prefer you can also just have empty abstract methods.


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ClAddress getAddress() {
        return address;
    }

    public void setAddress(ClAddress address) {
        this.address = address;
    }

    public com.claricetechnologies.model.ClEmployer getEmployer() {
        return employer;
    }

    public void setEmployer(com.claricetechnologies.model.ClEmployer employer) {
        this.employer = employer;
    }

    public RealmList<ClAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(RealmList<ClAddress> addresses) {
        this.addresses = addresses;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
