package br.com.fiap.fiaprestaurant.customer.utils;

import br.com.fiap.fiaprestaurant.customer.application.gateways.CustomerGateway;
import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import br.com.fiap.fiaprestaurant.customer.infra.controller.CustomerRequestDto;
import br.com.fiap.fiaprestaurant.customer.infra.persistence.CustomerEntity;
import br.com.fiap.fiaprestaurant.customer.infra.persistence.CustomerRepository;

public class CustomerHelper {

    public static CustomerRequestDto createCustomerDTORequest() {
        return new CustomerRequestDto("Claudia", "claudia@gmail.com");
    }

    public static CustomerEntity createCustomerEntity() {
        return CustomerEntity.builder()
                .name("Claudia").email("claudia@gmail.com")
                .build();
    }

    public static Customer createCustomer() {
        return new Customer("Claudia", "claudia@gmail.com");
    }

    public static CustomerEntity saveCustomerEntity(CustomerRepository customerRepository) {
        var customer = createCustomerEntity();
        customer.setId(4);
        return customerRepository.save(customer);
    }

    public static Customer saveCustomerEntity(CustomerGateway customerGateway) {
        var customer = createCustomer();
        customer.setId(4L);
        return customerGateway.create(customer);
    }

}
