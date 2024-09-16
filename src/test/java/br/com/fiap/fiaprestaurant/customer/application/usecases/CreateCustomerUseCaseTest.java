package br.com.fiap.fiaprestaurant.customer.application.usecases;

import br.com.fiap.fiaprestaurant.customer.application.gateways.CustomerGateway;
import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static br.com.fiap.fiaprestaurant.customer.utils.CustomerHelper.createCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateCustomerUseCaseTest {

    private CreateCustomerUseCase createCustomerUseCase;

    @Mock
    private CustomerGateway customerGateway;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        createCustomerUseCase = new CreateCustomerUseCase( customerGateway);
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    void shouldCreateCustomer() {
        // Arrange
        var customer = createCustomer();
        customer.setId(1L);
        when(customerGateway.create(any(Customer.class)))
                .thenAnswer(i -> i.getArgument(0));

        // Act
        var savedCustomer = createCustomerUseCase.execute(customer);

        // Assert
        assertThat(savedCustomer)
                .isInstanceOf(Customer.class)
                .isNotNull();
        assertThat(savedCustomer.getName())
                .isEqualTo(customer.getName());
        assertThat(savedCustomer.getEmail())
                .isEqualTo(customer.getEmail());
        assertThat(savedCustomer.getId())
                .isNotNull();
        verify(customerGateway, times(1)).create(customer);
    }

}