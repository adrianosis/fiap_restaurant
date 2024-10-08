package br.com.fiap.fiaprestaurant.reservation.utils;

import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import br.com.fiap.fiaprestaurant.customer.infra.persistence.CustomerEntity;
import br.com.fiap.fiaprestaurant.reservation.application.gateways.ReservationGateway;
import br.com.fiap.fiaprestaurant.reservation.application.inputs.ReserveRestaurantInput;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.ReservationStatus;
import br.com.fiap.fiaprestaurant.reservation.infra.controller.ReserveRestaurantRequestDto;
import br.com.fiap.fiaprestaurant.reservation.infra.persistence.ReservationEntity;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiap.fiaprestaurant.restaurant.infra.persistence.RestaurantEntity;

import java.time.LocalDateTime;

import static br.com.fiap.fiaprestaurant.customer.utils.CustomerHelper.createCustomer;
import static br.com.fiap.fiaprestaurant.customer.utils.CustomerHelper.createCustomerEntity;
import static br.com.fiap.fiaprestaurant.restaurant.utils.RestaurantHelper.createRestaurant;
import static br.com.fiap.fiaprestaurant.restaurant.utils.RestaurantHelper.createRestaurantEntity;

public class ReservationHelper {

    public static ReserveRestaurantInput createReserveRestaurantInput() {
        return ReserveRestaurantInput.builder()
                .reservationDateTime(LocalDateTime.of(2024, 9, 15, 18, 0))
                .guests(11)
                .restaurantId(1L)
                .customerId(1L)
                .build();
    }

    public static Reservation createReservation() {
        Restaurant restaurant = createRestaurant();
        Customer customer = createCustomer();

        return Reservation.builder()
                .reservationDateTime(LocalDateTime.of(2024, 9, 15, 18, 0))
                .guests(11)
                .status(ReservationStatus.RESERVED)
                .restaurant(restaurant)
                .customer(customer)
                .build();
    }

    public static Reservation saveReservation(ReservationGateway reservationGateway) {
        var reservation = createReservation();
        reservation.getCustomer().setId(1L);
        reservation.getRestaurant().setId(1L);
        reservationGateway.save(reservation);
        return reservationGateway.save(reservation);
    }

    public static ReservationEntity createReservationEntity() {
        RestaurantEntity restaurantEntity = createRestaurantEntity();
        restaurantEntity.setId(1L);
        CustomerEntity customerEntity = createCustomerEntity();
        customerEntity.setId(1L);

        return ReservationEntity.builder()
                .reservationDateTime(LocalDateTime.of(2024, 9, 15, 18, 0))
                .guests(11)
                .status(ReservationStatus.RESERVED)
                .restaurant(restaurantEntity)
                .customer(customerEntity)
                .build();
    }

    public static ReserveRestaurantRequestDto createReserveRestaurantRequest() {
        return ReserveRestaurantRequestDto.builder()
                .reservationDateTime(LocalDateTime.of(2024, 9, 15, 18, 0))
                .guests(1)
                .restaurantId(1L)
                .customerId(1L)
                .build();
    }

}
