package br.com.fiap.fiaprestaurant.customer.infra.controller;

import br.com.fiap.fiaprestaurant.customer.application.usecases.CreateCustomerUseCase;
import br.com.fiap.fiaprestaurant.customer.application.usecases.DeleteCustomerByIdUseCase;
import br.com.fiap.fiaprestaurant.customer.application.usecases.FindAllCustomersUseCase;
import br.com.fiap.fiaprestaurant.customer.application.usecases.FindCustomerByIdUseCase;
import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import br.com.fiap.fiaprestaurant.review.domain.entity.Review;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@Tag(name = "Customer", description = "Endpoints for managing customers")
public class CustomerController {

    private final CreateCustomerUseCase createCustomer;
    private final FindCustomerByIdUseCase findCustomerByIdUseCase;
    private final FindAllCustomersUseCase findAllCustomersUseCase;
    private final DeleteCustomerByIdUseCase deleteCustomerByIdUseCase;

    @PostMapping
    @Operation(summary = "Create a new customer", description = "Creates a new customer and returns the details of the newly created customer.")
    public ResponseEntity<CustomerResponseDto> createCustomer(@Valid @RequestBody CustomerRequestDto customerRequestDto){
        Customer savedCustomer = createCustomer.execute(customerRequestDto.toInput());

        CustomerResponseDto crdto = new CustomerResponseDto(savedCustomer.getId(), savedCustomer.getName(), savedCustomer.getEmail());
        return new ResponseEntity<>(crdto, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Find customer by ID", description = "Returns the details of a customer by their ID.")
    public ResponseEntity<CustomerResponseDto> findCustomerById(@PathVariable Long id) {
        Customer foundUser = findCustomerByIdUseCase.execute(id);
        CustomerResponseDto crdto = new CustomerResponseDto(foundUser.getId(), foundUser.getName(), foundUser.getEmail());
        return new ResponseEntity<>(crdto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete customer", description = "Deletes a customer by their ID.")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        deleteCustomerByIdUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    @Operation(summary = "Find all customers", description = "Returns a list of all customers.")
    public ResponseEntity<List<CustomerResponseDto>> findAll() {
        List<CustomerResponseDto> response = findAllCustomersUseCase.execute()
                .stream().map( c -> new CustomerResponseDto(c.getId(), c.getName(), c.getEmail())).toList();
        return ResponseEntity.status(200).body(response);
    }

}
