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
class FindCustomerByIdUseCaseIT {

    @Autowired
    private CustomerGateway customerGateway;

    @Autowired
    private FindCustomerByIdUseCase findCustomerByIdUseCase;

    @Test
    void shouldFindCustomerById() {
        // Arrange
        var customer = CustomerHelper.saveCustomerEntity(customerGateway);
        // Act
        var foundCustomer = findCustomerByIdUseCase.execute(customer.getId());
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
}
