package br.com.fiap.fiaprestaurant.reservation.config;

import br.com.fiap.fiaprestaurant.customer.application.usecases.FindCustomerByIdUseCase;
import br.com.fiap.fiaprestaurant.reservation.application.gateways.ReservationGateway;
import br.com.fiap.fiaprestaurant.reservation.application.usecases.ReserveRestaurantUseCase;
import br.com.fiap.fiaprestaurant.reservation.infra.gateways.ReservationEntityMapper;
import br.com.fiap.fiaprestaurant.reservation.infra.gateways.ReservationRepositoryGateway;
import br.com.fiap.fiaprestaurant.reservation.infra.persistence.ReservationRepository;
import br.com.fiap.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiap.fiaprestaurant.restaurant.application.usecases.CreateRestaurantUseCase;
import br.com.fiap.fiaprestaurant.restaurant.application.usecases.FindRestaurantByIdUseCase;
import br.com.fiap.fiaprestaurant.restaurant.infra.gateways.RestaurantEntityMapper;
import br.com.fiap.fiaprestaurant.restaurant.infra.gateways.RestaurantRepositoryGateway;
import br.com.fiap.fiaprestaurant.restaurant.infra.persistence.RestaurantRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReservationConfig {

    @Bean
    ReserveRestaurantUseCase reserveRestaurantUseCase(ReservationGateway reservationGateway,
                                                      FindRestaurantByIdUseCase findRestaurantByIdUseCase,
                                                      FindCustomerByIdUseCase findCustomerByIdUseCase) {
        return new ReserveRestaurantUseCase(reservationGateway, findRestaurantByIdUseCase, findCustomerByIdUseCase);
    }

    @Bean
    ReservationGateway reservationGateway(ReservationRepository repository, ReservationEntityMapper mapper){
        return new ReservationRepositoryGateway(repository, mapper);
    }

}
