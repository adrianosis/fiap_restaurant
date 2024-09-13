package br.com.fiap.fiaprestaurant.customer.config;

import br.com.fiap.fiaprestaurant.customer.application.gateways.CustomerGateway;
import br.com.fiap.fiaprestaurant.customer.application.usecases.CreateCustomerUseCase;
import br.com.fiap.fiaprestaurant.customer.application.usecases.DeleteCustomerByIdUseCase;
import br.com.fiap.fiaprestaurant.customer.application.usecases.FindAllCustomersUseCase;
import br.com.fiap.fiaprestaurant.customer.application.usecases.FindCustomerByIdUseCase;
import br.com.fiap.fiaprestaurant.customer.infra.gateways.CustomerEntityMapper;
import br.com.fiap.fiaprestaurant.customer.infra.gateways.CustomerRepositoryJpa;
import br.com.fiap.fiaprestaurant.customer.infra.persistence.CustomerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerConfig {

    @Bean
    CreateCustomerUseCase createCustomerUseCase(CustomerGateway customerGateway){
        return new CreateCustomerUseCase(customerGateway);
    }
    @Bean
    FindCustomerByIdUseCase findCustomerByIdUseCase(CustomerGateway customerGateway){
        return new FindCustomerByIdUseCase(customerGateway);
    }
    @Bean
    FindAllCustomersUseCase findAllCustomersUseCase(CustomerGateway customerGateway){
        return new FindAllCustomersUseCase(customerGateway);
    }
    @Bean
    DeleteCustomerByIdUseCase deleteCustomerByIdUseCase(CustomerGateway customerGateway){
        return new DeleteCustomerByIdUseCase(customerGateway);
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
