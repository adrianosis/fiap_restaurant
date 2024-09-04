package br.com.fiap.fiaprestaurant.customer.infra.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="customer")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private long id;
    private String name;
    private String email;

    public CustomerEntity(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
