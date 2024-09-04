package br.com.fiap.fiaprestaurant.customer.application.gateways;

import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerGateway {
    Customer create(Customer customer);

    Optional<Customer> findCustomerById(Long id);

    void deleteCustomerById(Long id);

    List<Customer> findAllCustomers();

}
