package com.v33toolsltd.domain.users;

import jakarta.persistence.*;

@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;
    private String password;

    // FIX: consistent role system
    private String role = "ROLE_ADMIN";

    protected Admin() {}

    private Admin(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.phoneNumber = builder.phoneNumber;
        this.emailAddress = builder.emailAddress;
        this.password = builder.password;
        this.role = builder.role;
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmailAddress() { return emailAddress; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public static class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String emailAddress;
        private String password;

        // FIX
        private String role = "ROLE_ADMIN";

        public Builder setId(Long id) { this.id = id; return this; }
        public Builder setFirstName(String firstName) { this.firstName = firstName; return this; }
        public Builder setLastName(String lastName) { this.lastName = lastName; return this; }
        public Builder setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; return this; }
        public Builder setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; return this; }
        public Builder setPassword(String password) { this.password = password; return this; }
        public Builder setRole(String role) { this.role = role; return this; }

        public Builder copy(Admin admin) {
            this.id = admin.getId();
            this.firstName = admin.getFirstName();
            this.lastName = admin.getLastName();
            this.phoneNumber = admin.getPhoneNumber();
            this.emailAddress = admin.getEmailAddress();
            this.password = admin.getPassword();
            this.role = admin.getRole();
            return this;
        }

        public Admin build() {
            return new Admin(this);
        }
    }
}