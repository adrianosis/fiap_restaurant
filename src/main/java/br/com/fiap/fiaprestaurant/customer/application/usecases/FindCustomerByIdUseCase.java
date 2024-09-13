package br.com.fiap.fiaprestaurant.customer.application.usecases;

import br.com.fiap.fiaprestaurant.customer.application.gateways.CustomerGateway;
import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindCustomerByIdUseCase {

    private final CustomerGateway customerGateway;

    public Customer execute(Long id){
        return  customerGateway.findCustomerById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }

}
