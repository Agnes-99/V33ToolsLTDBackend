package com.v33toolsltd.domain.generic;

import com.v33toolsltd.domain.users.Customer;
import jakarta.persistence.*;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    private String street;
    private String city;
    private String province;
    private String postalCode;
    private String country;

    protected Address (){}

    private Address(Builder builder){
        this.id = builder.id;
        this.customer = builder.customer;
        this.street = builder.street;
        this.city = builder.city;
        this.province = builder.province;
        this.postalCode = builder.postalCode;
        this.country = builder.country;
    }

    public Integer  getId(){
        return id;
    }

    public Customer getCustomer(){
        return customer;
    }

    public String getStreet(){
        return street;
    }

    public String getCity(){
        return city;
    }

    public String getProvince(){
        return province;
    }

    public String getPostalCode(){
        return postalCode;
    }

    public String getCountry(){
        return country;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", customer=" + (customer != null? customer.getId():"null") +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    public static class  Builder{
        private Integer id;
        private Customer customer;
        private String street;
        private String city;
        private String province;
        private String postalCode;
        private String country;

        public Builder setId(Integer id){
            this.id = id;
            return this;
        }

        public Builder setCustomer(Customer customer){
            this.customer = customer ;
            return this;
        }

        public Builder setStreet(String street){
            this.street = street;
            return this;
        }

        public Builder setCity(String city){
            this.city = city;
            return this;
        }

        public Builder setProvince(String province){
            this.province = province;
            return this;
        }

        public Builder setPostalCode(String postalCode){
            this.postalCode = postalCode;
            return this;
        }

        public Builder setCountry(String country){
            this.country = country;
            return this;
        }

        public Builder copy(Address address){
            this.id = address.getId();
            this.customer = address.getCustomer();
            this.street = address.getStreet();
            this.city = address.getCity();
            this.province = address.getProvince();
            this.postalCode = address.getPostalCode();
            this.country = address.getCountry();
            return this;
        }

        public Address build(){
            return new Address(this);
        }
    }

}
