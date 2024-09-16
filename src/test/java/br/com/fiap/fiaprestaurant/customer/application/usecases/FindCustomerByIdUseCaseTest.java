package br.com.fiap.fiaprestaurant.customer.application.usecases;

import br.com.fiap.fiaprestaurant.customer.application.gateways.CustomerGateway;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static br.com.fiap.fiaprestaurant.customer.utils.CustomerHelper.createCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FindCustomerByIdUseCaseTest {

    private FindCustomerByIdUseCase findCustomerByIdUseCase;
    @Mock
    private CustomerGateway customerGateway;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        findCustomerByIdUseCase = new FindCustomerByIdUseCase( customerGateway);
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    void shouldFindCustomerById() {
        // Arrange
        var id = 1L;
        var customer = createCustomer();
        when(customerGateway.findCustomerById(any(Long.class)))
                .thenReturn(Optional.of(customer));

        // Act
        var customerFound = findCustomerByIdUseCase.execute(id);

        // Assert
        verify(customerGateway, times(1))
                .findCustomerById(id);
        assertThat(customerFound)
                .isEqualTo(customer);
        assertThat(customer.getId())
                .isEqualTo(customerFound.getId());
        assertThat(customer.getName())
                .isEqualTo(customerFound.getName());
        assertThat(customer.getEmail())
                .isEqualTo(customerFound.getEmail());

    }
}