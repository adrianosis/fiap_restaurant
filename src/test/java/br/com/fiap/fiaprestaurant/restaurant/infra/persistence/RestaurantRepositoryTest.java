package br.com.fiap.fiaprestaurant.restaurant.infra.persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static br.com.fiap.fiaprestaurant.restaurant.utils.RestaurantHelper.createRestaurantEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class RestaurantRepositoryTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldSaveRestaurant() {
        // Arrange
        var restaurant = createRestaurantEntity();

        when(restaurantRepository.save(any(RestaurantEntity.class))).thenReturn(restaurant);

        // Act
        var savedRestaurant = restaurantRepository.save(restaurant);

        // Assert
        assertThat(savedRestaurant)
                .isNotNull()
                .isEqualTo(restaurant);
        verify(restaurantRepository, times(1)).save(any(RestaurantEntity.class));
    }

    @Test
    void shouldFindRestaurantById() {
        // Arrange
        long id = 1L;
        var restaurant = createRestaurantEntity();
        restaurant.setId(id);
        when(restaurantRepository.findById(any(Long.class))).thenReturn(Optional.of(restaurant));

        // Act
        var foundRestaurant = restaurantRepository.findById(id);

        // Assert
        verify(restaurantRepository, times(1)).findById(any(Long.class));
        assertThat(foundRestaurant).isPresent().containsSame(restaurant);
        foundRestaurant.ifPresent(savedRestaurantEntity -> {
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
    void shouldDeleteRestaurantById() {
        // Arrange
        Long id = 1L;
        doNothing().when(restaurantRepository).deleteById(any(Long.class));

        // Act
        restaurantRepository.deleteById(id);

        // Assert
        verify(restaurantRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldFindAllByNameOrLocationOrType() {
        // Arrange
        var name = "%";
        var location = "%";
        var kitchenType = "PIZZARIA";

        var restaurant1 = createRestaurantEntity();
        var restaurant2 = createRestaurantEntity();
        var listRestaurants = Arrays.asList(restaurant1, restaurant2);
        when(restaurantRepository.findAllByNameOrLocationOrType(any(String.class), any(String.class), any(String.class)))
                .thenReturn(listRestaurants);

        // Act
        var foundRestaurants = restaurantRepository.findAllByNameOrLocationOrType(name, location, kitchenType);

        // Assert
        verify(restaurantRepository, times(1)).findAllByNameOrLocationOrType(name, location, kitchenType);
        assertThat(foundRestaurants).hasSize(2).containsExactlyInAnyOrder(restaurant1, restaurant2);
    }


}
