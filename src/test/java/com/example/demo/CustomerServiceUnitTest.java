package com.example.demo;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.controller.CustomerController;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.model.Customer;
import com.example.demo.service.CustomerService;

public class CustomerServiceUnitTest {
    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    
    @Test
    void addCustomer() {
        Customer customer = new Customer("Test", "Person","test@lhv.com");
        CustomerDTO customerDTO = new CustomerDTO("Test", "Person","test@lhv.com");

        when(customerService.addCustomer(customerDTO)).thenReturn(customer);
    }
    @Test
    void addInvalidEmailCustomer() {
        Customer customer = new Customer("Test", "Person","testlhv.com");
        CustomerDTO customerDTO = new CustomerDTO("Test", "Person","test@lhv.com");

        when(customerService.addCustomer(customerDTO)).thenReturn(customer);
    }
    @Test
    void updateCustomer() {
        CustomerDTO customerDTO = new CustomerDTO("TestChanged", "PersonChanged","testChanged@lhv.com");
        CustomerDTO updateCustomerDTO = new CustomerDTO("TestChanged", "PersonChanged", "new@lhv.com");
        Customer updatedCustomer = new Customer(updateCustomerDTO.getFirstName(), updateCustomerDTO.getLastName(), updateCustomerDTO.getEmail());

        customerService.addCustomer(customerDTO);
        when(customerService.updateCustomer(1L, updateCustomerDTO)).thenReturn(updatedCustomer);
    }
    @Test
    void findCustomer(){
        CustomerDTO customerDTO = new CustomerDTO("TestChanged", "PersonChanged","testChanged@lhv.com");
        Customer customer = customerService.addCustomer(customerDTO);
        when(customerService.findCustomer(1L)).thenReturn(customer);
    }
    @Test
    void deleteCustomer() {
        CustomerDTO customerDTO = new CustomerDTO("TestChanged", "PersonChanged", "testChanged@lhv.com");
        Customer customer = new Customer(customerDTO.getFirstName(), customerDTO.getLastName(), customerDTO.getEmail());

        when(customerService.addCustomer(customerDTO)).thenReturn(customer);

        Customer savedCustomer = customerService.addCustomer(customerDTO);
        Long id = savedCustomer.getId();

        customerService.deleteCustomer(id);

        verify(customerService, times(1)).deleteCustomer(id);
    }
}
