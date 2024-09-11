package br.com.fiap.fiaprestaurant.customer.application.usecases;

import br.com.fiap.fiaprestaurant.customer.application.gateways.CustomerGateway;
import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class CreateCustomerUseCase {

    private final CustomerGateway customerGateway;
    public Customer create(Customer customer) {
        return customerGateway.create(customer);
    }

}
