package br.com.fiap.fiaprestaurant.customer.application.usecases;

import br.com.fiap.fiaprestaurant.customer.application.gateways.CustomerGateway;
import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import br.com.fiap.fiaprestaurant.shared.exception.RestaurantException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteCustomerByIdUseCase {

    private final CustomerGateway customerGateway;
    public void execute(Long id){
        customerGateway.deleteCustomerById(id);
    }
}
