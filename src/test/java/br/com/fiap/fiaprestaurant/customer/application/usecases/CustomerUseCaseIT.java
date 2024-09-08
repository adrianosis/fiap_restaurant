package br.com.fiap.fiaprestaurant.customer.application.usecases;

import br.com.fiap.fiaprestaurant.customer.application.gateways.CustomerGateway;
import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import br.com.fiap.fiaprestaurant.customer.utils.CustomerHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class CustomerUseCaseIT {

    @Autowired
    private CustomerGateway customerGateway;

    @Autowired
    private CustomerUseCase customerUseCase;

    @Test
    void shouldCreateCustomer() {
        // Arrange
        var customer = CustomerHelper.createCustomer();
        // Act
        var savedCustomer = customerUseCase.create(customer);
        // Assert
        assertThat(savedCustomer)
                .isNotNull()
                .isInstanceOf(Customer.class);
        assertThat(savedCustomer.getId())
                .isNotNull();
        assertThat(savedCustomer.getName())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(savedCustomer.getName());
        assertThat(savedCustomer.getEmail())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(savedCustomer.getEmail());
    }


    @Test
    void shouldFindCustomerById() {
        // Arrange
        var customer = CustomerHelper.saveCustomerEntity(customerGateway);
        // Act
        var foundCustomer = customerUseCase.findCustomerById(customer.getId());
        // Assert
        assertThat(foundCustomer)
                .isNotNull()
                .isInstanceOf(Customer.class);
        assertThat(foundCustomer.getId())
                .isEqualTo(customer.getId());
        assertThat(foundCustomer.getName())
                .isEqualTo(customer.getName());
        assertThat(foundCustomer.getEmail())
                .isEqualTo(customer.getEmail());
    }


    @Test
    void shouldDeleteCustomerById() {
        // Arrange
        var customer = CustomerHelper.saveCustomerEntity(customerGateway);
        // Act
        customerUseCase.deleteCustomerById(customer.getId());
    }

    @Test
    void shouldFindAllCustomers() {
        // Act
        var customers = customerUseCase.findAllCustomers();
        // Assert
        assertThat(customers).hasSize(3)
                .allSatisfy(customer -> {
                    assertThat(customer).isNotNull();
                    assertThat(customer).isInstanceOf(Customer.class);
                });
    }

}
