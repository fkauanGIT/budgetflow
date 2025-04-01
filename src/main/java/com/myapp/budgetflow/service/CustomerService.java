package com.myapp.budgetflow.service;

import com.myapp.budgetflow.model.Customer;
import com.myapp.budgetflow.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Customer createCustomer(Customer customer) {
        customer.setPwd(passwordEncoder.encode(customer.getPwd()));
        return customerRepository.save(customer);
    }

    public List<Customer> listAll() {
        return customerRepository.findAll();
    }

    public Customer getUserById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    @Transactional
    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = getUserById(id);
        customer.setUsername(customerDetails.getUsername());
        customer.setEmail(customerDetails.getEmail());

        if (customerDetails.getPwd() != null && !customerDetails.getPwd().isEmpty()) {
            customer.setPwd(passwordEncoder.encode(customerDetails.getPwd()));
        }

        return customerRepository.save(customer);
    }

    @Transactional
    public void deleteUser(Long id) {
        Customer customer = getUserById(id);
        customerRepository.delete(customer);
    }
}
