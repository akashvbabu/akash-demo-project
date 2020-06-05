package com.example.akashdemo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Phone number entity to store in the persistence layer
 */
@Entity
public class PhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String phoneNumber;

    private String alphaNumericCombination;

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAlphaNumericCombination(String alphaNumericCombination) {
        this.alphaNumericCombination = alphaNumericCombination;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAlphaNumericCombination() {
        return alphaNumericCombination;
    }
}
