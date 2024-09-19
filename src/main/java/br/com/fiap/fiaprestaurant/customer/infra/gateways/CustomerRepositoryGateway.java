package br.com.fiap.fiaprestaurant.customer.infra.gateways;

import br.com.fiap.fiaprestaurant.customer.application.gateways.CustomerGateway;
import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import br.com.fiap.fiaprestaurant.customer.infra.persistence.CustomerEntity;
import br.com.fiap.fiaprestaurant.customer.infra.persistence.CustomerRepository;
import br.com.fiap.fiaprestaurant.review.infra.persistance.ReviewEntity;
import br.com.fiap.fiaprestaurant.shared.exception.RestaurantException;

import java.util.List;

public class CustomerRepositoryGateway implements CustomerGateway {

    private CustomerRepository customerRepository;

    private final CustomerEntityMapper mapper;

    public CustomerRepositoryGateway(CustomerRepository customerRepository, CustomerEntityMapper mapper) {
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
    public Customer findCustomerById(Long id) {
        return this.customerRepository.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new RestaurantException("Customer not found"));

    }

    @Override
    public void deleteCustomerById(Long id) {

        CustomerEntity customer = this.customerRepository.findById(id)
                .orElseThrow(() -> new RestaurantException("Customer not found"));
        this.customerRepository.deleteById(customer.getId());
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
}
