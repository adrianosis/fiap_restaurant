package br.com.fiap.fiaprestaurant.customer.infra.gateways;

import br.com.fiap.fiaprestaurant.customer.application.gateways.CustomerGateway;
import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import br.com.fiap.fiaprestaurant.customer.infra.persistence.CustomerEntity;
import br.com.fiap.fiaprestaurant.customer.infra.persistence.CustomerRepository;

import java.util.List;
import java.util.Optional;

public class CustomerRepositoryJpa implements CustomerGateway {

    private CustomerRepository customerRepository;

    private final CustomerEntityMapper mapper;

    public CustomerRepositoryJpa(CustomerRepository customerRepository, CustomerEntityMapper mapper) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    @Override
    public Customer create(Customer customer) {
        CustomerEntity entity = mapper.toEntity(customer);
        CustomerEntity customerSaved = this.customerRepository.save(entity);
        return mapper.toDomain(customerSaved);
    }

    @Override
    public Optional<Customer> findCustomerById(Long id) {
        Optional<CustomerEntity> optionalCustomer =  this.customerRepository.findById(id);
        return optionalCustomer.map(mapper::toDomain);
    }

    @Override
    public void deleteCustomerById(Long id) {
        this.customerRepository.deleteById(id);
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
}
