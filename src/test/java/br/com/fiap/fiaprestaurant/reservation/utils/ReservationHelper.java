package br.com.fiap.fiaprestaurant.reservation.utils;

import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
import br.com.fiap.fiaprestaurant.customer.infra.persistence.CustomerEntity;
import br.com.fiap.fiaprestaurant.reservation.application.inputs.ReserveRestaurantInput;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.ReservationStatus;
import br.com.fiap.fiaprestaurant.reservation.infra.persistence.ReservationEntity;
import br.com.fiap.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Address;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiap.fiaprestaurant.restaurant.infra.controller.SaveRestaurantRequestDto;
import br.com.fiap.fiaprestaurant.restaurant.infra.persistence.AddressEntity;
import br.com.fiap.fiaprestaurant.restaurant.infra.persistence.RestaurantEntity;
import br.com.fiap.fiaprestaurant.restaurant.infra.persistence.RestaurantRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static br.com.fiap.fiaprestaurant.customer.utils.CustomerHelper.createCustomer;
import static br.com.fiap.fiaprestaurant.customer.utils.CustomerHelper.createCustomerEntity;
import static br.com.fiap.fiaprestaurant.restaurant.utils.RestaurantHelper.*;

public class ReservationHelper {

    public static ReserveRestaurantInput createReserveRestaurantInput() {
        return ReserveRestaurantInput.builder()
                .reservationDateTime(LocalDateTime.of (2024,9,15, 18, 0))
                .guests(11)
                .restaurantId(1L)
                .customerId(1L)
                .build();
    }

    public static Reservation createReservation() {
        Restaurant restaurant = createRestaurant();
        Customer customer = createCustomer();

        return Reservation.builder()
                .reservationDateTime(LocalDateTime.of (2024,9,15, 18, 0))
                .guests(11)
                .status(ReservationStatus.RESERVED)
                .restaurant(restaurant)
                .customer(customer)
                .build();
    }

    public static ReservationEntity createReservationEntity() {
        RestaurantEntity restaurantEntity = createRestaurantEntity();
        restaurantEntity.setId(1L);
        CustomerEntity customerEntity = createCustomerEntity();
        customerEntity.setId(1L);

        return ReservationEntity.builder()
                .reservationDateTime(LocalDateTime.of (2024,9,15, 18, 0))
                .guests(11)
                .status(ReservationStatus.RESERVED)
                .restaurant(restaurantEntity)
                .customer(customerEntity)
                .build();
    }





    /*
        private long id;
    private LocalDateTime reservationDateTime;
    private int guests;
    private LocalDateTime startService;
    private LocalDateTime endService;
    private String tableTag;
    private ReservationStatus status;
    private Restaurant restaurant;
    private Customer customer;
     */

//    public static RestaurantEntity createRestaurantEntity() {
//        AddressEntity address = AddressEntity.builder()
//                .street("Av Rebouças")
//                .number("1900")
//                .district("Itaim")
//                .city("São Paulo")
//                .state("SP")
//                .postalCode("05300100")
//                .build();
//
//        return RestaurantEntity.builder()
//                .name("PIZZARIA 10")
//                .kitchenType("PIZZARIA")
//                .capacity(100)
//                .openingTime(LocalTime.of(9, 0))
//                .closingTime(LocalTime.of(22, 0))
//                .address(address)
//                .build();
//    }
//
//    public static Restaurant saveRestaurant(RestaurantGateway restaurantGateway) {
//        var restaurant = createRestaurant();
//        restaurant.setId(4L);
//        return restaurantGateway.create(restaurant);
//    }
//
//    public static RestaurantEntity saveRestaurantEntity(RestaurantRepository restaurantRepository) {
//        var restaurant = createRestaurantEntity();
//        restaurant.setId(4L);
//        return restaurantRepository.save(restaurant);
//    }
//
//    public static SaveRestaurantRequestDto createRestaurantRequest() {
//        return SaveRestaurantRequestDto.builder()
//                .name("PIZZARIA 10")
//                .kitchenType("PIZZARIA")
//                .capacity(100)
//                .openingTime(LocalTime.of(9, 0))
//                .closingTime(LocalTime.of(22, 0))
//
//                .street("Av Rebouças")
//                .number("1900")
//                .district("Itaim")
//                .city("São Paulo")
//                .state("SP")
//                .postalCode("05300100")
//                .build();
//    }

}
