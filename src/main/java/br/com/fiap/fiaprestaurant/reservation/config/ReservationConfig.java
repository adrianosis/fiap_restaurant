package br.com.fiap.fiaprestaurant.reservation.config;

import br.com.fiap.fiaprestaurant.customer.application.usecases.FindCustomerByIdUseCase;
import br.com.fiap.fiaprestaurant.customer.infra.gateways.CustomerEntityMapper;
import br.com.fiap.fiaprestaurant.reservation.application.gateways.ReservationGateway;
import br.com.fiap.fiaprestaurant.reservation.application.usecases.*;
import br.com.fiap.fiaprestaurant.reservation.infra.gateways.ReservationEntityMapper;
import br.com.fiap.fiaprestaurant.reservation.infra.gateways.ReservationRepositoryGateway;
import br.com.fiap.fiaprestaurant.reservation.infra.persistence.ReservationRepository;
import br.com.fiap.fiaprestaurant.restaurant.application.usecases.FindRestaurantByIdUseCase;
import br.com.fiap.fiaprestaurant.restaurant.infra.gateways.RestaurantEntityMapper;
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
    ChangeReservationStatusUseCase changeReservationStatusUseCase(ReservationGateway reservationGateway) {
        return new ChangeReservationStatusUseCase(reservationGateway);
    }

    @Bean
    FindAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCase findAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCase(
            ReservationGateway reservationGateway) {
        return new FindAllOpenedReservationsByRestaurantIdAndReservationDateTimeUseCase(reservationGateway);
    }

    @Bean
    FindAllCompletedReservationsByCustomerIdUseCase findAllCompletedReservationsByCustomerIdUseCase(
            ReservationGateway reservationGateway) {
        return new FindAllCompletedReservationsByCustomerIdUseCase(reservationGateway);
    }

    @Bean
    ReservationGateway reservationGateway(ReservationRepository repository, ReservationEntityMapper mapper){
        return new ReservationRepositoryGateway(repository, mapper);
    }

    @Bean
    ReservationEntityMapper reservationEntityMapper(CustomerEntityMapper customerEntityMapper, RestaurantEntityMapper restaurantEntityMapper){
        return new ReservationEntityMapper(customerEntityMapper, restaurantEntityMapper);
    }

    @Bean
    FindReservationByIdUseCase findReservationByIdUseCase(ReservationGateway reservationGateway) {
        return new FindReservationByIdUseCase(reservationGateway);
    }


}
