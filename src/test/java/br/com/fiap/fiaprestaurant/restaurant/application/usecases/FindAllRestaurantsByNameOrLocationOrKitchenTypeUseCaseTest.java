package br.com.fiap.fiaprestaurant.restaurant.application.usecases;

import br.com.fiap.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static br.com.fiap.fiaprestaurant.restaurant.utils.RestaurantHelper.createRestaurant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FindAllRestaurantsByNameOrLocationOrKitchenTypeUseCaseTest {


    private FindAllRestaurantsByNameOrLocationOrKitchenTypeUseCase findAllRestaurantsByNameOrLocationOrKitchenTypeUseCase;

    @Mock
    private RestaurantGateway restaurantGateway;
    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        findAllRestaurantsByNameOrLocationOrKitchenTypeUseCase = new FindAllRestaurantsByNameOrLocationOrKitchenTypeUseCase(restaurantGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldFindAllRestaurantsByNameOrLocationOrKitchenType() {
        // Arrange
        var name = "PIZZARIA TOP";
        var location = "Itaim";
        var kitchenType = "PIZZARIA";

        var restaurant1 = createRestaurant();
        var restaurant2 = createRestaurant();
        var listRestaurants = Arrays.asList(restaurant1, restaurant2);
        when(restaurantGateway.findAllByNameOrLocationOrType(any(String.class), any(String.class), any(String.class)))
                .thenReturn(listRestaurants);

        // Act
        var foundRestaurants = findAllRestaurantsByNameOrLocationOrKitchenTypeUseCase.execute(name, location, kitchenType);

        // Assert
        verify(restaurantGateway, times(1)).findAllByNameOrLocationOrType(name, location, kitchenType);
        assertThat(foundRestaurants).hasSize(2).containsExactlyInAnyOrder(restaurant1, restaurant2);
    }


}