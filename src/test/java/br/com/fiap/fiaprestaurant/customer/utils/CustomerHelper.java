package br.com.fiap.fiaprestaurant.customer.utils;

import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import br.com.fiap.fiaprestaurant.customer.infra.persistence.CustomerEntity;
import br.com.fiap.fiaprestaurant.customer.infra.persistence.CustomerRepository;

public class CustomerHelper {

    public static CustomerEntity createCustomerEntity(){
        return  CustomerEntity.builder()
                .name("Claudia").email("claudia@gmail.com")
                .build();
    }

    public static Customer createCustomer(){
        return  new Customer("Claudia","claudia@gmail.com");
    }

    public static CustomerEntity saveCustomerEntity(CustomerRepository customerRepository) {
        var customer = createCustomerEntity();
        customer.setId(4);
        return customerRepository.save(customer);
    }

}
