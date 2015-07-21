package com.claricetechnologies.database;

import android.content.Context;

import com.claricetechnologies.model.ClAddress;
import com.claricetechnologies.model.ClEmployer;
import com.claricetechnologies.model.ClPerson;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * An ORM based database for the application.
 */
public class ClRealmDatabase {

    private void basicCRUD(Realm realm) {
        // All writes must be wrapped in a transaction to facilitate safe multi threading
        realm.beginTransaction();

        // Add a person
        ClPerson person = realm.createObject(ClPerson.class);
        person.setName("Young Person");
        person.setAge(14);

        // When the write transaction is committed, all changes a synced to disk.
        realm.commitTransaction();

        // Find the first person (no query conditions) and read a field
        person = realm.where(ClPerson.class).findFirst();

        // Update person in a write transaction
        realm.beginTransaction();
        person.setName("Senior Person");
        person.setAge(99);
        realm.commitTransaction();

        // Delete all persons
        realm.beginTransaction();
        realm.allObjects(ClPerson.class).clear();
        realm.commitTransaction();
    }

    private void basicQuery(Realm realm) {
        RealmResults<ClPerson> results = realm.where(ClPerson.class).equalTo("age", 99).findAll();
    }

    private void basicLinkQuery(Realm realm) {
        RealmResults<ClPerson> results = realm.where(ClPerson.class).equalTo("address.city", "Tiger").findAll();
    }

    private String complexReadWrite(Context context) {
        String status = "\nPerforming complex Read/Write operation...";

        // Open the default realm. All threads must use it's own reference to the realm.
        // Those can not be transferred across threads.
        Realm realm = Realm.getInstance(context);

        // Add ten persons in one write transaction
        realm.beginTransaction();
        ClAddress address1 = realm.createObject(ClAddress.class);
        address1.setCity("Pune");
        for (int i = 0; i < 10; i++) {
            ClPerson person = realm.createObject(ClPerson.class);
            person.setId(i);
            person.setName("ClPerson no. " + i);
            person.setAge(i);
            person.setAddress(address1);

            // The field height is annotated with @Ignore.
            // This means setHeight sets the ClPerson height
            // field directly. The height is NOT saved as part of
            // the RealmObject:
            person.setHeight(182);

            ClEmployer employer = realm.createObject(ClEmployer.class);
            employer.setCompanyName("Clarice");
            person.setEmployer(employer);
        }
        realm.commitTransaction();

        // Implicit read transactions allow you to access your objects
        status += "\nNumber of persons: " + realm.allObjects(ClPerson.class).size();

        // Iterate over all objects
        for (ClPerson pers : realm.allObjects(ClPerson.class)) {
            String address;
            if (pers.getAddress() == null) {
                address = "None";
            } else {
                address = pers.getAddress().getHouseNo().concat(" ").concat(pers.getAddress().getStreetNo()).concat(" ").
                        concat(pers.getAddress().getCity()).concat(" ").concat(pers.getAddress().getState()).concat(" ").
                        concat(pers.getAddress().getCountry());
            }
            status += "\n" + pers.getName() + ":" + pers.getAge() + " : " + address + " : " + pers.getAddresses().size();

            // The field height is annotated with @Ignore
            // Though we initially set its value to 182, it has
            // not been saved as part of the ClPerson RealmObject:
            assert (pers.getHeight() == 0);
        }

        // Sorting
        RealmResults<ClPerson> sortedClPersons = realm.allObjects(ClPerson.class);
        sortedClPersons.sort("age", false);
        assert (realm.allObjects(ClPerson.class).last().getName() == sortedClPersons.first().getName());
        status += "\nSorting " + sortedClPersons.last().getName() + " == " + realm.allObjects(ClPerson.class).first().getName();

        realm.close();
        return status;
    }

    private String complexQuery(Context context) {
        String status = "\n\nPerforming complex Query operation...";

        Realm realm = Realm.getInstance(context);
        status += "\nNumber of persons: " + realm.allObjects(ClPerson.class).size();

        // Find all persons where age between 7 and 9 and name begins with "ClClPerson".
        RealmResults<ClPerson> results = realm.where(ClPerson.class)
                .between("age", 7, 9)       // Notice implicit "and" operation
                .beginsWith("name", "ClClPerson").findAll();
        status += "\nSize of result set: " + results.size();

        realm.close();
        return status;
    }
}