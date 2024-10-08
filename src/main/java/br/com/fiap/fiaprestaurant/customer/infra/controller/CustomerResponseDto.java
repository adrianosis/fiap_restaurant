package br.com.fiap.fiaprestaurant.customer.infra.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDto{

    private Long id;
    private String name;
    private String email;
}
