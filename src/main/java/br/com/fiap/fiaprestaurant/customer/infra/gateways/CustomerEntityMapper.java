package br.com.fiap.fiaprestaurant.customer.infra.gateways;

import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import br.com.fiap.fiaprestaurant.customer.infra.persistence.CustomerEntity;

public class CustomerEntityMapper {


    public CustomerEntity toEntity(Customer customer){
        return new CustomerEntity(customer.getId(), customer.getName(), customer.getEmail());
    }

    public Customer toDomain(CustomerEntity entity){
        return new Customer(entity.getId(), entity.getName(), entity.getEmail());
    }
}
