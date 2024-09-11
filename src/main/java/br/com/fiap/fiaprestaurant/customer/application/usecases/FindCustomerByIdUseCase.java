package br.com.fiap.fiaprestaurant.customer.application.usecases;

import br.com.fiap.fiaprestaurant.customer.application.gateways.CustomerGateway;
import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FindCustomerByIdUseCase {

    private final CustomerGateway customerGateway;
    public Customer findCustomerById(Long id){
        return  customerGateway.findCustomerById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }

}
