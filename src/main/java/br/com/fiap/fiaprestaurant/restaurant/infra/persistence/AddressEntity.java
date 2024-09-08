package br.com.fiap.fiaprestaurant.restaurant.infra.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AddressEntity {

    private String street;
    private String number;
    private String complement;
    private String district;
    private String city;
    private String state;
    @Column(name = "postal_code")
    private String postalCode;

}
