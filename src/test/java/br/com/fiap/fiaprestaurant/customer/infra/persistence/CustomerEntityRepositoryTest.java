package br.com.fiap.fiaprestaurant.customer.infra.persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static br.com.fiap.fiaprestaurant.customer.utils.CustomerHelper.createCustomerEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerEntityRepositoryTest {

    @Mock
    private CustomerRepository customerRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception{
        openMocks.close();
    }

    @Test
    void shouldSaveCustomer(){
        // Arrange
        var customer = createCustomerEntity();

        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customer);

        // Act
        var savedCustomer = customerRepository.save(customer);

        // Assert
        assertThat(savedCustomer)
                .isNotNull()
                .isEqualTo(customer);
        verify(customerRepository, times(1)).save(any(CustomerEntity.class));
    }

    @Test
    void shouldFindCustomerById(){
        // Arrange
        Long id = 1L;
        var customer = createCustomerEntity();
        customer.setId(id);
        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(customer));

        // Act
        var foundCustomerOptional = customerRepository.findById(id);

        // Assert
        assertThat (foundCustomerOptional).isPresent().containsSame(customer);
        foundCustomerOptional.ifPresent(foundCustomerEntity -> {
            assertThat(foundCustomerEntity.getId()).isEqualTo(customer.getId());
            assertThat(foundCustomerEntity.getName()).isEqualTo(customer.getName());
            assertThat(foundCustomerEntity.getEmail()).isEqualTo(customer.getEmail());
        });
        verify(customerRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void shouldDeleteCustomerById(){
        // Arrange
        Long id = 1L;
        doNothing().when(customerRepository).deleteById(any(Long.class));
        // Act
        customerRepository.deleteById(id);
        // Assert
        verify(customerRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldFindAllCustomers() {
        // Arrange
        var customer1 = createCustomerEntity();
        customer1.setId(1L);
        var customer2 = createCustomerEntity();
        customer2.setId(2L);

        var listCustomers = Arrays.asList(customer1, customer2);
        when(customerRepository.findAll()).thenReturn(listCustomers);
        // Act
        var foundCustomers = customerRepository.findAll();
        // Assert
        assertThat(foundCustomers)
                .hasSize(2)
                .containsExactlyInAnyOrder(customer1, customer2);
        verify(customerRepository, times(1)).findAll();
    }


}