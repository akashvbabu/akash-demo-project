package com.example.akashdemo.controller;

import com.example.akashdemo.entity.MessageResponseEntity;
import com.example.akashdemo.entity.PhoneNumber;
import com.example.akashdemo.entity.PhoneNumberCombinationResponseEntity;
import com.example.akashdemo.service.PhoneVerificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

/**
 * Controller for the /phoneNumber paths
 */
@RestController
@Validated
public class PhoneVerificationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    // REGEX for phone validation of 7 or 10 numbers
    private static final String VALID_PHONE_NUMBER = "^([0-9]{4}[\\s-][0-9]{3})|([0-9]{3}[\\s-][0-9]{3}[\\s-][0-9]{4})$";

    @Autowired
    PhoneVerificationService phoneVerificationService;


    /**
     * Get the phoneNumber and it's generated alphanumeric combination
     * @param phoneNumber
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/phoneNumber/combinations")
    public PhoneNumberCombinationResponseEntity verifyPhoneNumber(@Valid @Pattern(regexp = VALID_PHONE_NUMBER)
                                                   @RequestParam("phone") String phoneNumber,
                                                                  @Min(0) @RequestParam("pageNumber") int pageNumber,
                                                                  @Min(1) @RequestParam("pageSize") int pageSize) {
        LOGGER.debug("phoneNumber is: {}, pageNo: {}, pageSize: {}", phoneNumber, pageNumber, pageSize);
        Pair<Long, List<PhoneNumber>> phoneNumberCombinationResponse =
                phoneVerificationService.getPhoneNumber(phoneNumber, pageNumber, pageSize);
        return new PhoneNumberCombinationResponseEntity(phoneNumberCombinationResponse.getFirst(), phoneNumberCombinationResponse.getSecond());
    }

    /**
     * generate alphanumeric phone number combinations from the provided phone number
     * if it's valid. valid phone numbers are of the format 111-111-1111 or 111 111 1111
     * or 111 1111 or 111-1111
     * @param phoneNumber
     * @return
     */
    @CrossOrigin(origins = "*")
    @PostMapping("/phoneNumber/process")
    public ResponseEntity verifyPhoneAndGenerateCombinations(@Valid
                                         @Pattern(regexp = VALID_PHONE_NUMBER)
                                         @RequestParam("phone") String phoneNumber) {
        LOGGER.debug("phoneNumber to verify and generate combinations is: {}", phoneNumber);
        // if the phone number doesn't exist then create it
        if (!phoneVerificationService.phoneNumberExists(phoneNumber)) {
            phoneVerificationService.generateAndSavePhoneNumberCombinations(phoneNumber);
        }
        return new ResponseEntity(new MessageResponseEntity("generated combinations"), HttpStatus.CREATED);
    }
}
