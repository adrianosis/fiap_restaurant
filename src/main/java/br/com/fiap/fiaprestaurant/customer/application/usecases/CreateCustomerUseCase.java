package br.com.fiap.fiaprestaurant.customer.application.usecases;

import br.com.fiap.fiaprestaurant.customer.application.gateways.CustomerGateway;
import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateCustomerUseCase {

    private final CustomerGateway customerGateway;
    public Customer execute(Customer customer) {
        return customerGateway.create(customer);
    }

}
