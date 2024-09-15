package br.com.fiap.fiaprestaurant.restaurant.application.usecases;

import br.com.fiap.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static br.com.fiap.fiaprestaurant.restaurant.utils.RestaurantHelper.saveRestaurant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class FindRestaurantByIdUseCaseIT {

    @Autowired
    private RestaurantGateway restaurantGateway;

    @Autowired
    private FindRestaurantByIdUseCase findRestaurantByIdUseCase;

    @Test
    public void shouldFindRestaurantById() throws Exception {
        // Arrange
        var restaurant = saveRestaurant(restaurantGateway);
        restaurant.setId(4L);

        // Act
        var foundRestaurant = findRestaurantByIdUseCase.execute(4L);

        // Assert
        assertThat(foundRestaurant).isNotNull().isInstanceOf(Restaurant.class);
        assertThat(foundRestaurant.getId()).isEqualTo(restaurant.getId());
        assertThat(foundRestaurant.getName()).isNotNull().isNotEmpty().isEqualTo(restaurant.getName());
        assertThat(foundRestaurant.getKitchenType()).isNotNull().isNotEmpty().isEqualTo(restaurant.getKitchenType());
        assertThat(foundRestaurant.getCapacity()).isEqualTo(restaurant.getCapacity());
        assertThat(foundRestaurant.getOpeningTime()).isNotNull().isEqualTo(restaurant.getOpeningTime());
        assertThat(foundRestaurant.getClosingTime()).isNotNull().isEqualTo(restaurant.getClosingTime());
        assertThat(foundRestaurant.getAddress()).isNotNull().isEqualTo(restaurant.getAddress());
    }

    @Test
    public void shouldThrowExceptionNotFound_WhenFindRestaurantById_IdNotExists() {
        // Arrange
        var id = 4L;

        // Act
        // Assert
        assertThatThrownBy(
                () -> findRestaurantByIdUseCase.execute(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Restaurant not found");
    }

}