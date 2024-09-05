package br.com.fiap.fiaprestaurant.customer.infra.controller;

import br.com.fiap.fiaprestaurant.customer.application.usecases.CustomerUseCase;
import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerUseCase createCustomer;

    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody CustomerRequestDto dto){
        Customer savedCustomer = createCustomer.create(new Customer(dto.name(), dto.email()));
        CustomerResponseDto crdto = new CustomerResponseDto(savedCustomer.getId(), savedCustomer.getName(), savedCustomer.getEmail());
        return new ResponseEntity<>(crdto, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> findCustomerById(@PathVariable Long id) {
        Customer foundUser = createCustomer.findCustomerById(id);
        CustomerResponseDto crdto = new CustomerResponseDto(foundUser.getId(), foundUser.getName(), foundUser.getEmail());
        return new ResponseEntity<>(crdto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        createCustomer.deleteCustomerById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<CustomerResponseDto>> findAll() {
        List<CustomerResponseDto> response = createCustomer.findAllCustomers()
                .stream().map( c -> new CustomerResponseDto(c.getId(), c.getName(), c.getEmail())).toList();
        return ResponseEntity.status(200).body(response);
    }

}
