package com.example.demo.dto.mapper;

import java.time.LocalDateTime;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.model.Customer;

public class CustomerMapper {
    public Customer mapToModel(CustomerDTO dto) {
        return new Customer(dto.getFirstName(), dto.getLastName(), dto.getEmail());
    }

    public Customer mapToModel(CustomerDTO dto, LocalDateTime time) {
        Customer customer = new Customer(dto.getFirstName(), dto.getLastName(), dto.getEmail());
        customer.setModifiedDtime(time);
        return customer;
    }
}
