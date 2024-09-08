package br.com.fiap.fiaprestaurant.customer.infra.controller;

import br.com.fiap.fiaprestaurant.customer.application.usecases.CustomerUseCase;
import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import br.com.fiap.fiaprestaurant.customer.infra.gateways.CustomerEntityMapper;
import br.com.fiap.fiaprestaurant.customer.utils.CustomerHelper;
import br.com.fiap.fiaprestaurant.shared.exception.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CustomerControllerTest {

    private MockMvc mockMvc;
    @Mock
    private CustomerUseCase customerUseCase;

    CustomerEntityMapper mapper = new CustomerEntityMapper();

    AutoCloseable openMocks;

    @BeforeEach
    void setUp(){
        openMocks = MockitoAnnotations.openMocks(this);
        CustomerController customerController = new CustomerController(customerUseCase);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }


    @Test
    void shouldCreateCustomer() throws Exception {
        var customerRequest = CustomerHelper.createCustomerEntity();
        when(customerUseCase.create(any(Customer.class)))
                .thenAnswer(i -> i.getArgument(0));

        mockMvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerRequest)))
                .andExpect(status().isCreated());
        verify(customerUseCase, times(1)).create(any(Customer.class));
    }

    @Test
    void shouldFindCustomerById() throws Exception {
        var id = 1L;
        var customer = CustomerHelper.createCustomerEntity();
        customer.setId(id);

        when(customerUseCase.findCustomerById(id)).thenReturn(mapper.toDomain(customer));

        mockMvc.perform(get("/customer/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customer.getId()))
                .andExpect(jsonPath("$.name").value(customer.getName()))
                .andExpect(jsonPath("$.email").value(customer.getEmail()));

        verify(customerUseCase, times(1)).findCustomerById(any(Long.class));
    }

    @Test
    void shouldDeleteCustomerById() throws Exception {
        var id = 1L;
        doNothing().when(customerUseCase).deleteCustomerById(any(Long.class));

        mockMvc.perform(delete("/customer/{id}", id))
                .andExpect(status().isNoContent());
        verify(customerUseCase, times(1))
                .deleteCustomerById(any(Long.class));
    }

    @Test
    void shouldFindAllCustomers() throws Exception {
        var customer = CustomerHelper.createCustomerEntity();
        customer.setId(1L);
        var customers = Arrays.asList(mapper.toDomain(customer));
        when(customerUseCase.findAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/customer")
                        .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(jsonPath("$[0].id").value(customer.getId()))
                .andExpect(jsonPath("$[0].name").value(customer.getName()))
                .andExpect(jsonPath("$[0].email").value(customer.getEmail()))
                .andExpect(status().isOk());

        verify(customerUseCase, times(1))
                .findAllCustomers();
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}