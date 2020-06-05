package com.example.akashdemo.service;

import com.example.akashdemo.entity.PhoneNumber;
import com.example.akashdemo.repository.PhoneNumberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.*;

/**
 * Service Class to interact with the DAO (repository) layer and additional business logic
 * around phone number combination generation
 */
@Component
public class PhoneVerificationService {
    public static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final Map<Character, String> NUMBER_LETTERS_MAP =
            Map.of('2', "ABC", '3', "DEF", '4', "GHI", '5', "JKL", '6', "MNO",
                   '7', "PQRS", '8', "TUV", '9', "WXYZ");

    @Autowired
    PhoneNumberRepository phoneNumberRepository;

    @Transactional
    public Pair<Long, List<PhoneNumber>> getPhoneNumber(String phoneNumber, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<PhoneNumber> phoneNumberPage = phoneNumberRepository.findByPhoneNumber(phoneNumber, pageable);
        LOGGER.debug("Total Hits: {}", phoneNumberPage.getTotalElements());
        return Pair.of(phoneNumberPage.getTotalElements(), phoneNumberPage.toList());
    }

    @Transactional
    public boolean phoneNumberExists(String phoneNumber) {
        Pageable pageable = PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
        return phoneNumberRepository.findByPhoneNumber(phoneNumber, pageable).getTotalElements() > 0 ? true: false;
    }


    @Transactional
    public void generateAndSavePhoneNumberCombinations(String phoneNumber) {
        List<String> combinations = generatePhoneNumberCombinations(phoneNumber);

        // If there are combinations, save it to the database
        if (!combinations.isEmpty()) {
            List<PhoneNumber> phoneNumberList = new ArrayList<>();
            combinations.forEach(x -> {
                PhoneNumber phoneNumber1 = new PhoneNumber();
                phoneNumber1.setPhoneNumber(phoneNumber);
                phoneNumber1.setAlphaNumericCombination(x);
                phoneNumberList.add(phoneNumber1);
            });
            phoneNumberRepository.saveAll(phoneNumberList);
        }
    }

    /**
     * Generate a list of alphanumeric combinations for the phone number provided
     * @param phoneNumber
     * @return
     */
    public List<String> generatePhoneNumberCombinations(String phoneNumber) {
        Set<Character> seenDigits = new HashSet<>();
        List<String> alphaNumericPhoneCombinations = new ArrayList<>();

        // iterate through and find out the unique digits
        for (char character: phoneNumber.toCharArray()) {
            // Skip the space and - if it's encountered
            if (character != ' ' && character != '-' && !seenDigits.contains(character)) {
                seenDigits.add(character);
            }
        }

        // generate the combinations of the phonenumber and the alpha characters
        for (char character: seenDigits) {
            if (NUMBER_LETTERS_MAP.containsKey(character)) {
                // generate the combination
                String alphabetsForDigit = NUMBER_LETTERS_MAP.get(character);
                for (char alphabet: alphabetsForDigit.toCharArray()) {
                    alphaNumericPhoneCombinations.add(phoneNumber + " " + alphabet);
                }
            }
        }

        return alphaNumericPhoneCombinations;
    }
}
