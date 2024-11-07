package com.example.demo.customer;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.CustomerNotFoundException;

import jakarta.validation.Valid;

@Service
public class CustomerService {
    @Value("${emailRegex}")
    private String emailRegex;

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public void deleteCustomer(Long id) {
        repository.delete(repository.findById(id).get());
    }

    public Customer updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer customer = findCustomer(id);
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setEmail(customerDTO.getEmail());
        customer.setModifiedDtime(LocalDateTime.now());

        return repository.save(customer);
    }

    public Customer addCustomer(@Valid CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setEmail(customerDTO.getEmail());

        return repository.save(customer);
    }

    public Customer findCustomer(Long id){
        return repository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }
}
