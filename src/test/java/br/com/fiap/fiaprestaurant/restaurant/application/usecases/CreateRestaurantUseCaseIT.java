package br.com.fiap.fiaprestaurant.restaurant.application.usecases;

import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static br.com.fiap.fiaprestaurant.restaurant.utils.RestaurantHelper.createRestaurant;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class CreateRestaurantUseCaseIT {

    @Autowired
    private CreateRestaurantUseCase createRestaurantUseCase;

    @Test
    void shouldCreateRestaurant() {
        // Arrange
        var restaurant = createRestaurant();

        // Act
        var savedRestaurant = createRestaurantUseCase.execute(restaurant);

        // Assert
        assertThat(savedRestaurant).isNotNull().isInstanceOf(Restaurant.class);
        assertThat(savedRestaurant.getId()).isGreaterThan(0);
        assertThat(savedRestaurant.getName()).isNotNull().isNotEmpty().isEqualTo(restaurant.getName());
        assertThat(savedRestaurant.getKitchenType()).isNotNull().isNotEmpty().isEqualTo(restaurant.getKitchenType());
        assertThat(savedRestaurant.getCapacity()).isEqualTo(restaurant.getCapacity());
        assertThat(savedRestaurant.getOpeningTime()).isNotNull().isEqualTo(restaurant.getOpeningTime());
        assertThat(savedRestaurant.getClosingTime()).isNotNull().isEqualTo(restaurant.getClosingTime());
        assertThat(savedRestaurant.getAddress()).isNotNull().isEqualTo(restaurant.getAddress());
    }

}
