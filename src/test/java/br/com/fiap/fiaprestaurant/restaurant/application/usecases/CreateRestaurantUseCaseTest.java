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

public class CreateRestaurantUseCaseTest {

    private CreateRestaurantUseCase createRestaurantUseCase;

    @Mock
    private RestaurantGateway restaurantGateway;
    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        createRestaurantUseCase = new CreateRestaurantUseCase(restaurantGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldCreateRestaurant() {
        // Arrange
        var restaurant = createRestaurant();
        restaurant.setId(1L);
        when(restaurantGateway.create(any(Restaurant.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        var savedRestaurant = createRestaurantUseCase.execute(restaurant);

        // Assert
        verify(restaurantGateway, times(1)).create(restaurant);
        assertThat(savedRestaurant).isInstanceOf(Restaurant.class).isNotNull();
        assertThat(savedRestaurant.getId()).isEqualTo(restaurant.getId());
        assertThat(savedRestaurant.getName()).isEqualTo(restaurant.getName());
        assertThat(savedRestaurant.getKitchenType()).isEqualTo(restaurant.getKitchenType());
        assertThat(savedRestaurant.getCapacity()).isEqualTo(restaurant.getCapacity());
        assertThat(savedRestaurant.getOpeningTime()).isEqualTo(restaurant.getOpeningTime());
        assertThat(savedRestaurant.getClosingTime()).isEqualTo(restaurant.getClosingTime());
        assertThat(savedRestaurant.getAddress()).isEqualTo(restaurant.getAddress());

    }


}
