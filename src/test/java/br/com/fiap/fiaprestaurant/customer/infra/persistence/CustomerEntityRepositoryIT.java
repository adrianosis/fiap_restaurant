package br.com.fiap.fiaprestaurant.customer.infra.persistence;

import br.com.fiap.fiaprestaurant.customer.utils.CustomerHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
class CustomerEntityRepositoryIT {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldCreateTable(){
        long registers = customerRepository.count();
        assertThat(registers).isPositive();
    }

    @Test
    void shouldSaveCustomer() {
        //Arranje
        var customer = CustomerHelper.createCustomerEntity();
        //Act
        var savedCustomer = customerRepository.save(customer);
        //Assert
        assertThat(savedCustomer)
                .isInstanceOf(CustomerEntity.class)
                .isNotNull();
        assertThat(savedCustomer.getName())
                .isEqualTo(customer.getName());
        assertThat(savedCustomer.getEmail())
                .isEqualTo(customer.getEmail());
    }

    @Test
    void shouldFindCustomerById() {
        //Arranje
        var customer = CustomerHelper.saveCustomerEntity(customerRepository);
        var id = customer.getId();
        //Act
        var foundCustomerOptional = customerRepository.findById(id);
        //Assert
        assertThat(foundCustomerOptional)
                .isPresent()
                .containsSame(customer);
    }
    @Test
    void shouldDeleteCustomerById() {
        //Arranje
        var customer = CustomerHelper.saveCustomerEntity(customerRepository);
        var id = customer.getId();
        //Act
        customerRepository.deleteById(id);
        var foundCustomerOptional = customerRepository.findById(id);
        // Assert
        assertThat(foundCustomerOptional)
                .isEmpty();
    }
    @Test
    void shouldFindAllCustomers() {
        //Act
        var result = customerRepository.findAll();
        //Assert
        assertThat(result)
                .hasSize(3);

    }
}