package br.com.fiap.fiaprestaurant.restaurant.config;

import br.com.fiap.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiap.fiaprestaurant.restaurant.application.usecases.RestaurantUseCase;
import br.com.fiap.fiaprestaurant.restaurant.infra.gateways.RestaurantEntityMapper;
import br.com.fiap.fiaprestaurant.restaurant.infra.gateways.RestaurantRepositoryGateway;
import br.com.fiap.fiaprestaurant.restaurant.infra.persistence.RestaurantRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestaurantConfig {

    @Bean
    RestaurantUseCase createRestaurantUseCase(RestaurantGateway restaurantGateway) {
        return new RestaurantUseCase(restaurantGateway);
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
