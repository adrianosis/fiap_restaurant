package br.com.fiap.fiaprestaurant.customer.application.usecases;

import br.com.fiap.fiaprestaurant.customer.application.gateways.CustomerGateway;
import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;

import java.util.List;

public class CustomerUseCase {

    private final CustomerGateway customerGateway;

    public CustomerUseCase(CustomerGateway customerGateway) {
        this.customerGateway = customerGateway;
    }

    public Customer create(Customer customer) {
        return customerGateway.create(customer);
    }

    public Customer findCustomerById(Long id){
        return  customerGateway.findCustomerById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }

    public void deleteCustomerById(Long id){
        Customer customer = customerGateway.findCustomerById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        customerGateway.deleteCustomerById(customer.getId());
    }

    public List<Customer> findAllCustomers(){
        return customerGateway.findAllCustomers();
    }

}
