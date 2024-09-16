package br.com.fiap.fiaprestaurant.reservation.infra.gateways;

import br.com.fiap.fiaprestaurant.customer.infra.gateways.CustomerEntityMapper;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import br.com.fiap.fiaprestaurant.reservation.infra.persistence.ReservationEntity;
import br.com.fiap.fiaprestaurant.restaurant.infra.gateways.RestaurantEntityMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReservationEntityMapper {

    private final CustomerEntityMapper customerEntityMapper;
    private final RestaurantEntityMapper restaurantEntityMapper;

    public ReservationEntity toEntity(Reservation reservation) {
        return new ReservationEntity(reservation.getId(),
                reservation.getReservationDateTime(),
                reservation.getGuests(),
                reservation.getStartService(),
                reservation.getEndService(),
                reservation.getTableTag(),
                reservation.getStatus(),
                restaurantEntityMapper.toEntity(reservation.getRestaurant()),
                customerEntityMapper.toEntity(reservation.getCustomer())
        );
    }

    public Reservation toDomain(ReservationEntity entity) {
        return new Reservation(entity.getId(),
                entity.getReservationDateTime(),
                entity.getGuests(),
                entity.getStartService(),
                entity.getEndService(),
                entity.getTableTag(),
                entity.getStatus(),
                restaurantEntityMapper.toDomain(entity.getRestaurant()),
                customerEntityMapper.toDomain(entity.getCustomer())
        );
    }

}
