package com.v33toolsltd.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class Helper {

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (isNullOrEmpty(phoneNumber)) return false;
        
        // Now we just check if it contains only numbers and the + sign
        // and is a reasonable length (7 to 15 digits)
        return phoneNumber.matches("^\\+[0-9]{7,15}$");
    }

    public static boolean isValidEmail(String email) {
        if (isNullOrEmpty(email)) return false;
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.compile(regex).matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        if (isNullOrEmpty(password)) return false;
        // At least one letter, one digit, and 8+ characters
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        return Pattern.compile(regex).matcher(password).matches();
    }

    public static boolean isValidDate(LocalDateTime dateTime) {
        return dateTime != null;
    }

    public static boolean isValidDate(LocalDate date) {
        return date != null;
    }

    public static boolean isValidQuantity(int quantity) {
        return quantity > 0;
    }

    public static boolean isValidPrice(Double price) {
        return price != null && price > 0;
    }

    public static boolean isValidAmount(Double amount) {
        return amount != null && amount >= 0;
    }
}