package br.com.fiap.fiaprestaurant.restaurant.config;

import br.com.fiap.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiap.fiaprestaurant.restaurant.application.usecases.CreateRestaurantUseCase;
import br.com.fiap.fiaprestaurant.restaurant.application.usecases.FindRestaurantByIdUseCase;
import br.com.fiap.fiaprestaurant.restaurant.application.usecases.FindAllRestaurantsByNameOrLocationOrKitchenTypeUseCase;
import br.com.fiap.fiaprestaurant.restaurant.infra.gateways.RestaurantEntityMapper;
import br.com.fiap.fiaprestaurant.restaurant.infra.gateways.RestaurantRepositoryGateway;
import br.com.fiap.fiaprestaurant.restaurant.infra.persistence.RestaurantRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestaurantConfig {

    @Bean
    CreateRestaurantUseCase createRestaurantUseCase(RestaurantGateway restaurantGateway) {
        return new CreateRestaurantUseCase(restaurantGateway);
    }

    @Bean
    FindRestaurantByIdUseCase findRestaurantByIdUseCase(RestaurantGateway restaurantGateway){
        return new FindRestaurantByIdUseCase(restaurantGateway);
    }

    @Bean
    FindAllRestaurantsByNameOrLocationOrKitchenTypeUseCase finsAllRestaurantsByNameOrLocationOrKitchenTypeUseCase(RestaurantGateway restaurantGateway){
        return new FindAllRestaurantsByNameOrLocationOrKitchenTypeUseCase(restaurantGateway);
    }

    @Bean
    RestaurantRepositoryGateway createRestaurantRepositoryGateway(RestaurantRepository repository, RestaurantEntityMapper mapper){
        return new RestaurantRepositoryGateway(repository, mapper);
    }


    @Bean
    RestaurantEntityMapper createMapper(){
        return new RestaurantEntityMapper();
    }

}
