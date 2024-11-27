package com.example.demo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
    
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
@Transactional
class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private CustomerDTO testCustomer;

    @BeforeEach
    void setUp() {
        testCustomer = new CustomerDTO("John", "Doe", "john.doe@lhv.com");
    }

    @Test
    void testAddCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@lhv.com"));
    }

    @Test
    void testGetCustomer() throws Exception {
        Customer createdCustomer = objectMapper.readValue(createTestUserInDB(), Customer.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/" + createdCustomer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@lhv.com"));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        Customer createdCustomer = objectMapper.readValue(createTestUserInDB(), Customer.class);

        CustomerDTO updatedCustomer = new CustomerDTO("Jane", "Doe", "jane.doe@lhv.com");

        mockMvc.perform(MockMvcRequestBuilders.put("/updateCustomer/" + createdCustomer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCustomer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("jane.doe@lhv.com"));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        Customer createdCustomer = objectMapper.readValue(createTestUserInDB(), Customer.class);

        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/" + createdCustomer.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/" + createdCustomer.getId()))
                .andExpect(status().isNotFound());
    }

    private String createTestUserInDB() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        return response;
    }
}
