package com.example.akashdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * End to end tests without http server
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PhoneNumberResourceMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void combinationCreatedSuccessfully() throws Exception {
        this.mockMvc.perform(post("/phoneNumber/process").param("phone", "111-111-1111"))
                .andExpect(status().is(201))
                .andExpect(content().string(containsString("generated combinations")));
    }

    @Test
    public void combinationCreatedDoesNotCreateDuplicates() throws Exception {
        this.mockMvc.perform(post("/phoneNumber/process").param("phone", "333-333-3333"))
                .andExpect(status().is(201))
                .andExpect(content().string(containsString("generated combinations")));
        this.mockMvc.perform(get("/phoneNumber/combinations")
                .param("phone", "333-333-3333")
                .param("pageNumber", "0")
                .param("pageSize", "5"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.totalHits", is(3)));
        // Make a duplicate request for the same number
        this.mockMvc.perform(post("/phoneNumber/process").param("phone", "333-333-3333"))
                .andExpect(status().is(201))
                .andExpect(content().string(containsString("generated combinations")));
        this.mockMvc.perform(get("/phoneNumber/combinations")
                .param("phone", "333-333-3333")
                .param("pageNumber", "0")
                .param("pageSize", "5"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.totalHits", is(3)));

    }

    @Test
    public void invalidPhoneNumberReturns400() throws Exception {
        this.mockMvc.perform(post("/phoneNumber/process").param("phone", "112"))
                .andExpect(status().is(400))
                .andExpect(content().string(containsString("Invalid Input Parameter")));
    }

    @Test
    public void getPhoneNumberCombinationReturns400OnInvalidNumber() throws Exception {
        this.mockMvc.perform(get("/phoneNumber/combinations")
                .param("phone", "112")
                .param("pageNumber", "0")
                .param("pageSize", "10"))
                .andExpect(status().is(400))
                .andExpect(content().string(containsString("Invalid Input Parameter")));
    }

    @Test
    public void getPhoneNumberCombinationReturns400OnInvalidPageNumber() throws Exception {
        this.mockMvc.perform(get("/phoneNumber/combinations")
                .param("phone", "222-222-2222")
                .param("pageNumber", "-1")
                .param("pageSize", "10"))
                .andExpect(status().is(400))
                .andExpect(content().string(containsString("Invalid Input Parameter")));
    }

    @Test
    public void getPhoneNumberCombinationReturns400OnInvalidPageSize() throws Exception {
        this.mockMvc.perform(get("/phoneNumber/combinations")
                .param("phone", "222-222-2222")
                .param("pageNumber", "1")
                .param("pageSize", "-10"))
                .andExpect(status().is(400))
                .andExpect(content().string(containsString("Invalid Input Parameter")));
    }

    @Test
    public void getPhoneNumberCombinationReturnsResults() throws Exception {
        // send a process request to prime the database
        this.mockMvc.perform(post("/phoneNumber/process").param("phone", "222-222-2222"));
        this.mockMvc.perform(get("/phoneNumber/combinations")
                .param("phone", "222-222-2222")
                .param("pageNumber", "0")
                .param("pageSize", "2"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.totalHits", is(3)))
                .andExpect(jsonPath("$.phoneNumberCombinations", hasSize(2)))
                .andExpect(jsonPath("$.phoneNumberCombinations[0].alphaNumericCombination", is("222-222-2222 A")));
    }

    @Test
    public void getPhoneNumberCombinationReturnsEmptyResult() throws Exception {
        // send a process request to prime the database
        this.mockMvc.perform(get("/phoneNumber/combinations")
                .param("phone", "333-333-3333")
                .param("pageNumber", "0")
                .param("pageSize", "2"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.totalHits", is(0)))
                .andExpect(jsonPath("$.phoneNumberCombinations", hasSize(0)));
    }
}
