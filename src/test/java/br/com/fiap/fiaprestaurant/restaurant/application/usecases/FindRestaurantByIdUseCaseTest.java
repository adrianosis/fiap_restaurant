package br.com.fiap.fiaprestaurant.restaurant.application.usecases;

import br.com.fiap.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static br.com.fiap.fiaprestaurant.restaurant.utils.RestaurantHelper.createRestaurant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FindRestaurantByIdUseCaseTest {

    private FindRestaurantByIdUseCase findRestaurantByIdUseCase;

    @Mock
    private RestaurantGateway restaurantGateway;
    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        findRestaurantByIdUseCase = new FindRestaurantByIdUseCase(restaurantGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldFindRestaurantById() throws Exception {
        // Arrange
        var id = 1L;
        var restaurant = createRestaurant();
        when(restaurantGateway.findById(any(Long.class))).thenReturn(restaurant);

        // Act
        var foundRestaurant = findRestaurantByIdUseCase.execute(id);

        // Assert
        verify(restaurantGateway, times(1)).findById(id);
        assertThat(foundRestaurant).isInstanceOf(Restaurant.class).isNotNull();
        assertThat(foundRestaurant.getId()).isEqualTo(restaurant.getId());
        assertThat(foundRestaurant.getName()).isEqualTo(restaurant.getName());
        assertThat(foundRestaurant.getKitchenType()).isEqualTo(restaurant.getKitchenType());
        assertThat(foundRestaurant.getCapacity()).isEqualTo(restaurant.getCapacity());
        assertThat(foundRestaurant.getOpeningTime()).isEqualTo(restaurant.getOpeningTime());
        assertThat(foundRestaurant.getClosingTime()).isEqualTo(restaurant.getClosingTime());
        assertThat(foundRestaurant.getAddress()).isEqualTo(restaurant.getAddress());
    }

}