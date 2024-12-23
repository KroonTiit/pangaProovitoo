package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.mapper.CustomerMapper;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;

import jakarta.validation.Valid;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public void deleteCustomer(Long id) {
        repository.delete(repository.findById(id).get());
    }

    public Customer updateCustomer(Long id, CustomerDTO customerDTO) {
        CustomerMapper customerMapper = new CustomerMapper();
        return repository.save(customerMapper.mapToModel(customerDTO, LocalDateTime.now()));
    }

    public Customer addCustomer(@Valid CustomerDTO customerDTO) {
        CustomerMapper customerMapper = new CustomerMapper();
        return repository.save(customerMapper.mapToModel(customerDTO));
    }

    public Customer findCustomer(Long id){
        return repository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }
}
