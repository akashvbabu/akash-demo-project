package com.example.akashdemo.entity;

import com.example.akashdemo.entity.PhoneNumber;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Response entity containing total hits and the combinations
 */
public class PhoneNumberCombinationResponseEntity {

    private long totalHits;

    private List<PhoneNumber> phoneNumberCombinations;

    public PhoneNumberCombinationResponseEntity(long totalHits, @NonNull List<PhoneNumber> phoneNumberCombinations) {
        this.totalHits = totalHits;
        this.phoneNumberCombinations = phoneNumberCombinations;
    }

    public void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }

    public void setPhoneNumberCombinations(List<PhoneNumber> phoneNumberCombinations) {
        this.phoneNumberCombinations = phoneNumberCombinations;
    }

    public long getTotalHits() {
        return totalHits;
    }

    public List<PhoneNumber> getPhoneNumberCombinations() {
        return phoneNumberCombinations;
    }
}
