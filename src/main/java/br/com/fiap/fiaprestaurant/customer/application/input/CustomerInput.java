package br.com.fiap.fiaprestaurant.customer.application.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInput {

    private String name;
    private String email;
}
