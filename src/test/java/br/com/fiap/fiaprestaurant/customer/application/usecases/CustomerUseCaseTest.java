package br.com.fiap.fiaprestaurant.customer.application.usecases;

import br.com.fiap.fiaprestaurant.customer.application.gateways.CustomerGateway;
import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import br.com.fiap.fiaprestaurant.customer.utils.CustomerHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static br.com.fiap.fiaprestaurant.customer.utils.CustomerHelper.createCustomer;
import static br.com.fiap.fiaprestaurant.customer.utils.CustomerHelper.createCustomerEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerUseCaseTest {

    private CustomerUseCase customerUseCase;

    @Mock
    private CustomerGateway customerGateway;
    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        customerUseCase = new CustomerUseCase( customerGateway);
    }

    @Test
    void shouldCreateCustomer() {
        var customer = createCustomer();
        when(customerGateway.create(any(Customer.class)))
                .thenAnswer(i -> i.getArgument(0));

        var savedCustomer = customerUseCase.create(customer);

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

    @Test
    void shouldFindCustomerById() {
        var id = 1L;
        var customer = createCustomer();
        when(customerGateway.findCustomerById(any(Long.class)))
                .thenReturn(Optional.of(customer));

        var customerFound = customerUseCase.findCustomerById(id);

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

    @Test
    void shouldDeleteCustomerById() {
        var id = 1L;
        var customer = createCustomer();
        customer.setId(id);
        when(customerGateway.findCustomerById(id))
                .thenReturn(Optional.of(customer));
        doNothing()
                .when(customerGateway).deleteCustomerById(id);

        customerUseCase.deleteCustomerById(id);

        verify(customerGateway, times(1)).findCustomerById(any(Long.class));
        verify(customerGateway, times(1)).deleteCustomerById(any(Long.class));
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
        var foundCustomers = customerUseCase.findAllCustomers();
        // Assert
        assertThat(foundCustomers)
                .hasSize(2)
                .containsExactlyInAnyOrder(customer1, customer2);
        verify(customerGateway, times(1)).findAllCustomers();
    }
}