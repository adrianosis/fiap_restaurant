package br.com.fiap.fiaprestaurant.restaurant.infra.persistence;

import br.com.fiap.fiaprestaurant.restaurant.utils.RestaurantHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static br.com.fiap.fiaprestaurant.restaurant.utils.RestaurantHelper.createRestaurantEntity;
import static br.com.fiap.fiaprestaurant.restaurant.utils.RestaurantHelper.saveRestaurantEntity;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
public class RestaurantRepositoryIT {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void shouldCreateTable() {
        long registers = restaurantRepository.count();
        assertThat(registers).isPositive();
    }

    @Test
    void shouldSaveRestaurant() {
        // Arranje
        var restaurant = createRestaurantEntity();

        // Act
        var savedRestaurant = restaurantRepository.save(restaurant);

        // Assert
        assertThat(savedRestaurant).isInstanceOf(RestaurantEntity.class).isNotNull();
        assertThat(savedRestaurant.getId()).isEqualTo(restaurant.getId());
        assertThat(savedRestaurant.getName()).isEqualTo(restaurant.getName());
        assertThat(savedRestaurant.getKitchenType()).isEqualTo(restaurant.getKitchenType());
        assertThat(savedRestaurant.getCapacity()).isEqualTo(restaurant.getCapacity());
        assertThat(savedRestaurant.getOpeningTime()).isEqualTo(restaurant.getOpeningTime());
        assertThat(savedRestaurant.getClosingTime()).isEqualTo(restaurant.getClosingTime());
        assertThat(savedRestaurant.getAddress()).isEqualTo(restaurant.getAddress());
    }

    @Test
    void shouldFindRestaurantById() {
        // Arranje
        var restaurant = saveRestaurantEntity(restaurantRepository);
        var id = restaurant.getId();

        // Act
        var restaurantOptional = restaurantRepository.findById(id);

        // Assert
        assertThat(restaurantOptional).isPresent().containsSame(restaurant);
        restaurantOptional.ifPresent(savedRestaurantEntity -> {
            assertThat(savedRestaurantEntity.getId()).isEqualTo(restaurant.getId());
            assertThat(savedRestaurantEntity.getName()).isEqualTo(restaurant.getName());
            assertThat(savedRestaurantEntity.getKitchenType()).isEqualTo(restaurant.getKitchenType());
            assertThat(savedRestaurantEntity.getCapacity()).isEqualTo(restaurant.getCapacity());
            assertThat(savedRestaurantEntity.getOpeningTime()).isEqualTo(restaurant.getOpeningTime());
            assertThat(savedRestaurantEntity.getClosingTime()).isEqualTo(restaurant.getClosingTime());
            assertThat(savedRestaurantEntity.getAddress()).isEqualTo(restaurant.getAddress());
        });
    }

    @Test
    void shouldFindAllByNameOrLocationOrType() {
        // Arrange
        var name = "%";
        var location = "CENTRO";
        var kitchenType = "%";

        //Act
        var restaurants = restaurantRepository.findAllByNameOrLocationOrType(name, location, kitchenType);

        //Assert
        assertThat(restaurants).hasSize(2);
    }

}
