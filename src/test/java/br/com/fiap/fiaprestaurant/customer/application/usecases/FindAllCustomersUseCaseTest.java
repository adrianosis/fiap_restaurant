package br.com.fiap.fiaprestaurant.customer.application.usecases;

import br.com.fiap.fiaprestaurant.customer.application.gateways.CustomerGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;

import static br.com.fiap.fiaprestaurant.customer.utils.CustomerHelper.createCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class FindAllCustomersUseCaseTest {

    private FindAllCustomersUseCase findAllCustomersUseCase;

    @Mock
    private CustomerGateway customerGateway;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        findAllCustomersUseCase = new FindAllCustomersUseCase( customerGateway);
    }

    @Test
    void shouldFindAllCustomers() {
        // Arrange
        var customer1 = createCustomer();
        customer1.setId(1L);
        var customer2 = createCustomer();
        customer2.setId(2L);

        var listCustomers = Arrays.asList(customer1, customer2);
        when(customerGateway.findAllCustomers()).thenReturn(listCustomers);
        // Act
        var foundCustomers = findAllCustomersUseCase.execute();
        // Assert
        assertThat(foundCustomers)
                .hasSize(2)
                .containsExactlyInAnyOrder(customer1, customer2);
        verify(customerGateway, times(1)).findAllCustomers();
    }
}