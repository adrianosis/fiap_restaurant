package br.com.fiap.fiaprestaurant.customer.config;

import br.com.fiap.fiaprestaurant.customer.application.gateways.CustomerGateway;
import br.com.fiap.fiaprestaurant.customer.application.usecases.CustomerUseCase;
import br.com.fiap.fiaprestaurant.customer.infra.gateways.CustomerEntityMapper;
import br.com.fiap.fiaprestaurant.customer.infra.gateways.CustomerRepositoryJpa;
import br.com.fiap.fiaprestaurant.customer.infra.persistence.CustomerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerConfig {

    @Bean
    CustomerUseCase createCustomerUseCase(CustomerGateway customerGateway){
        return new CustomerUseCase(customerGateway);
    }

    @Bean
    CustomerRepositoryJpa createRepositoryJpa(CustomerRepository customerRepository, CustomerEntityMapper mapper){
        return new CustomerRepositoryJpa(customerRepository, mapper);
    }

    @Bean
    CustomerEntityMapper returnMapper(){
        return new CustomerEntityMapper();
    }

}
