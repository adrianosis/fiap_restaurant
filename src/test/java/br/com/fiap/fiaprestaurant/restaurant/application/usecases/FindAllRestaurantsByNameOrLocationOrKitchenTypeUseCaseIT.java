package br.com.fiap.fiaprestaurant.restaurant.application.usecases;

import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class FindAllRestaurantsByNameOrLocationOrKitchenTypeUseCaseIT {

    @Autowired
    private FindAllRestaurantsByNameOrLocationOrKitchenTypeUseCase findAllRestaurantsByNameOrLocationOrKitchenTypeUseCase;

    @Test
    void shouldFindAllRestaurantsByNameOrLocationOrKitchenType() {
        // Arrange
        var name = "%";
        var location = "CENTRO";
        var kitchenType = "%";

        // Act
        var restaurants = findAllRestaurantsByNameOrLocationOrKitchenTypeUseCase.execute(name, location, kitchenType);

        // Assert
        assertThat(restaurants).hasSize(2).allSatisfy(restaurant -> {
            assertThat(restaurant).isNotNull();
            assertThat(restaurant).isInstanceOf(Restaurant.class);
        });
    }

}
