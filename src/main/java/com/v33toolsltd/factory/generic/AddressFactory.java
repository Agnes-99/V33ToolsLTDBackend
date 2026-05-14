package com.v33toolsltd.factory.generic;


import com.v33toolsltd.domain.generic.Address;
import com.v33toolsltd.domain.users.Customer;
import com.v33toolsltd.util.Helper;

public class AddressFactory {

    public static Address createAddress(
            Customer customer,
            String street,
            String city,
            String province,
            String postalCode,
            String country) {

        if (customer == null || customer.getId() == null)
            throw new IllegalArgumentException("A valid customer must be provided");
        if (Helper.isNullOrEmpty(street))
            throw new IllegalArgumentException("Please provide a street");
        if (Helper.isNullOrEmpty(city))
            throw new IllegalArgumentException("Please provide a city");
        if (Helper.isNullOrEmpty(province))
            throw new IllegalArgumentException("Please provide a province");
        if (Helper.isNullOrEmpty(postalCode))
            throw new IllegalArgumentException("Please provide a postal code");
        if (Helper.isNullOrEmpty(country))
            throw new IllegalArgumentException("Please provide a country");

        return new Address.Builder()
                .setCustomer(customer)
                .setStreet(street)
                .setCity(city)
                .setProvince(province)
                .setPostalCode(postalCode)
                .setCountry(country)
                .build();
    }
}
