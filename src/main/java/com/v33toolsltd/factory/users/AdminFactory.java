package com.v33toolsltd.factory.users;

import com.v33toolsltd.domain.users.Admin;
import com.v33toolsltd.util.Helper;

public class AdminFactory {

    public static Admin createAdmin(String firstName, String lastName, String phoneNumber, String emailAddress, String password) {

        if (Helper.isNullOrEmpty(firstName) || Helper.isNullOrEmpty(lastName) || Helper.isNullOrEmpty(phoneNumber)) {
            return null;
        }

        if (!Helper.isValidEmail(emailAddress)) {
            return null;
        }

        if (!Helper.isValidPassword(password)) {
            return null;
        }

        return new Admin.Builder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPhoneNumber(phoneNumber)
                .setEmailAddress(emailAddress)
                .setPassword(password)
                .setRole("ROLE_ADMIN")
                .build();
    }
}
