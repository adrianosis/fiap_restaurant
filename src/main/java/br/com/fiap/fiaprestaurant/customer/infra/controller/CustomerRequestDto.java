package br.com.fiap.fiaprestaurant.customer.infra.controller;

import br.com.fiap.fiaprestaurant.customer.application.input.CustomerInput;
import br.com.fiap.fiaprestaurant.review.application.input.ReviewInput;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestDto{

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    public CustomerInput toInput() {
        return new CustomerInput(this.name, this.email);
    }

}
