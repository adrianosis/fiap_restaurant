package br.com.fiap.fiaprestaurant.restaurant.application.usecases;

import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import br.com.fiap.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static br.com.fiap.fiaprestaurant.restaurant.infra.utils.RestaurantHelper.createRestaurant;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class RestaurantUseCaseIT {

    @Autowired
    private RestaurantUseCase restaurantUseCase;

    @Test
    void shouldCreateRestaurant() {
        // Arrange
        var restaurant = createRestaurant();
        restaurant.setId(4L);

        // Act
        var savedRestaurant = restaurantUseCase.create(restaurant);

        // Assert
        assertThat(savedRestaurant).isNotNull().isInstanceOf(Restaurant.class);
        assertThat(savedRestaurant.getId()).isEqualTo(restaurant.getId());
        assertThat(savedRestaurant.getName()).isNotNull().isNotEmpty().isEqualTo(restaurant.getName());
        assertThat(savedRestaurant.getKitchenType()).isNotNull().isNotEmpty().isEqualTo(restaurant.getKitchenType());
        assertThat(savedRestaurant.getCapacity()).isEqualTo(restaurant.getCapacity());
        assertThat(savedRestaurant.getOpeningTime()).isNotNull().isEqualTo(restaurant.getOpeningTime());
        assertThat(savedRestaurant.getClosingTime()).isNotNull().isEqualTo(restaurant.getClosingTime());
        assertThat(savedRestaurant.getAddress()).isNotNull().isEqualTo(restaurant.getAddress());
    }

}
