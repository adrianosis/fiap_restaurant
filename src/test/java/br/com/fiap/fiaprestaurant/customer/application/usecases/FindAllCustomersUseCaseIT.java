package br.com.fiap.fiaprestaurant.customer.application.usecases;

import br.com.fiap.fiaprestaurant.customer.application.gateways.CustomerGateway;
import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
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
class FindAllCustomersUseCaseIT {

    @Autowired
    private CustomerGateway customerGateway;

    @Autowired
    private FindAllCustomersUseCase findAllCustomersUseCase;

    @Test
    void shouldFindAllCustomers() {
        // Act
        var customers = findAllCustomersUseCase.execute();
        // Assert
        assertThat(customers).hasSize(3)
                .allSatisfy(customer -> {
                    assertThat(customer).isNotNull();
                    assertThat(customer).isInstanceOf(Customer.class);
                });
    }

}
