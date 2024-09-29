package br.com.fiap.fiaprestaurant.restaurant.application.usecases;

import br.com.fiap.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static br.com.fiap.fiaprestaurant.restaurant.utils.RestaurantHelper.createRestaurant;
import static br.com.fiap.fiaprestaurant.restaurant.utils.RestaurantHelper.createRestaurantUpdate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UpdateRestaurantUseCaseTest {

    private UpdateRestaurantUseCase updateRestaurantUseCase;

    @Mock
    private RestaurantGateway restaurantGateway;
    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        updateRestaurantUseCase = new UpdateRestaurantUseCase(restaurantGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldUpdateRestaurant() {
        // Arrange
        var restaurantId = 1L;
        var restaurant = createRestaurant();
        when(restaurantGateway.findById(any(Long.class))).thenReturn(restaurant);
        when(restaurantGateway.save(any(Restaurant.class))).thenReturn(restaurant);

        // Act
        var restaurantUpdate = createRestaurantUpdate();
        var foundRestaurant = updateRestaurantUseCase.execute(restaurantId, restaurantUpdate);

        // Assert
        verify(restaurantGateway, times(1)).findById(restaurantId);
        assertThat(foundRestaurant).isInstanceOf(Restaurant.class).isNotNull();
        assertThat(foundRestaurant.getId()).isEqualTo(restaurantUpdate.getId());
        assertThat(foundRestaurant.getName()).isEqualTo(restaurantUpdate.getName());
        assertThat(foundRestaurant.getKitchenType()).isEqualTo(restaurantUpdate.getKitchenType());
        assertThat(foundRestaurant.getCapacity()).isEqualTo(restaurantUpdate.getCapacity());
        assertThat(foundRestaurant.getOpeningTime()).isEqualTo(restaurantUpdate.getOpeningTime());
        assertThat(foundRestaurant.getClosingTime()).isEqualTo(restaurantUpdate.getClosingTime());
        assertThat(foundRestaurant.getAddress()).isEqualTo(restaurantUpdate.getAddress());
    }

}