package br.com.fiap.fiaprestaurant.restaurant.application.usecases;

import br.com.fiap.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static br.com.fiap.fiaprestaurant.restaurant.utils.RestaurantHelper.createRestaurantUpdate;
import static br.com.fiap.fiaprestaurant.restaurant.utils.RestaurantHelper.saveRestaurant;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class UpdateRestaurantUseCaseIT {

    @Autowired
    private RestaurantGateway restaurantGateway;

    @Autowired
    private UpdateRestaurantUseCase updateRestaurantUseCase;

    @Test
    void shouldUpdateRestaurant() {
        // Arrange
        var restaurant = saveRestaurant(restaurantGateway);


        // Act
        var restaurantUpdate = createRestaurantUpdate();
        var foundRestaurant = updateRestaurantUseCase.execute(restaurant.getId(), restaurantUpdate);

        // Assert
        assertThat(foundRestaurant).isInstanceOf(Restaurant.class).isNotNull();
        assertThat(foundRestaurant.getId()).isEqualTo(restaurant.getId());
        assertThat(foundRestaurant.getName()).isEqualTo(restaurantUpdate.getName());
        assertThat(foundRestaurant.getKitchenType()).isEqualTo(restaurantUpdate.getKitchenType());
        assertThat(foundRestaurant.getCapacity()).isEqualTo(restaurantUpdate.getCapacity());
        assertThat(foundRestaurant.getOpeningTime()).isEqualTo(restaurantUpdate.getOpeningTime());
        assertThat(foundRestaurant.getClosingTime()).isEqualTo(restaurantUpdate.getClosingTime());
        assertThat(foundRestaurant.getAddress()).isEqualTo(restaurantUpdate.getAddress());
    }

}