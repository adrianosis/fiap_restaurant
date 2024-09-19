package br.com.fiap.fiaprestaurant.customer.application.gateways;

import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;

import java.util.List;

public interface CustomerGateway {
    Customer create(Customer customer);

    Customer findCustomerById(Long id);

    void deleteCustomerById(Long id);

    List<Customer> findAllCustomers();

}
