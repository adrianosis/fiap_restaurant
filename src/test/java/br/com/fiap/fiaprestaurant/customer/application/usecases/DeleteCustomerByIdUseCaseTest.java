package br.com.fiap.fiaprestaurant.customer.application.usecases;

import br.com.fiap.fiaprestaurant.customer.application.gateways.CustomerGateway;
import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static br.com.fiap.fiaprestaurant.customer.utils.CustomerHelper.createCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DeleteCustomerByIdUseCaseTest {

    private DeleteCustomerByIdUseCase deleteCustomerByIdUseCase;

    @Mock
    private CustomerGateway customerGateway;
    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        deleteCustomerByIdUseCase = new DeleteCustomerByIdUseCase( customerGateway);
    }

    @Test
    void shouldDeleteCustomerById() {
        // Arrange
        var id = 1L;
        var customer = createCustomer();
        customer.setId(id);
        when(customerGateway.findCustomerById(id))
                .thenReturn(Optional.of(customer));
        doNothing()
                .when(customerGateway).deleteCustomerById(id);

        // Act
        deleteCustomerByIdUseCase.deleteCustomerById(id);

        // Assert
        verify(customerGateway, times(1)).findCustomerById(any(Long.class));
        verify(customerGateway, times(1)).deleteCustomerById(any(Long.class));
    }
}