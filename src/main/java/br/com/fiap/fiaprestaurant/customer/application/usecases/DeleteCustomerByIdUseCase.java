package br.com.fiap.fiaprestaurant.customer.application.usecases;

import br.com.fiap.fiaprestaurant.customer.application.gateways.CustomerGateway;
import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteCustomerByIdUseCase {

    private final CustomerGateway customerGateway;
    public void execute(Long id){
        Customer customer = customerGateway.findCustomerById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        customerGateway.deleteCustomerById(customer.getId());
    }
}
