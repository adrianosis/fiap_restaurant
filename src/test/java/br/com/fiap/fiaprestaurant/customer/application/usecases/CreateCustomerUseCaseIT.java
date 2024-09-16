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
@ActiveProfiles("test")
@Transactional
class CreateCustomerUseCaseIT {

    @Autowired
    private CustomerGateway customerGateway;

    @Autowired
    private CreateCustomerUseCase createCustomerUseCase;

    @Test
    void shouldCreateCustomer() {
        // Arrange
        var customer = CustomerHelper.createCustomer();
        // Act
        var savedCustomer = createCustomerUseCase.execute(customer);
        // Assert
        assertThat(savedCustomer)
                .isNotNull()
                .isInstanceOf(Customer.class);
        assertThat(savedCustomer.getId())
                .isGreaterThan(0);
        assertThat(savedCustomer.getName())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(customer.getName());
        assertThat(savedCustomer.getEmail())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(customer.getEmail());
    }



}
