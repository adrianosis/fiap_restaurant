package br.com.fiap.fiaprestaurant.reservation.infra.controller;

import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import br.com.fiap.fiaprestaurant.reservation.domain.entity.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReservationDto {

    private long id;
    private LocalDateTime reservationDateTime;
    private int guests;
    private LocalDateTime startService;
    private LocalDateTime endService;
    private String tableTag;
    private ReservationStatus status;
    private long restaurantId;
    private long customerId;

    public static ReservationDto toDto(Reservation reservation) {
        return new ReservationDto(
                reservation.getId(),
                reservation.getReservationDateTime(),
                reservation.getGuests(),
                reservation.getStartService(),
                reservation.getEndService(),
                reservation.getTableTag(),
                reservation.getStatus(),
                reservation.getRestaurant().getId(),
                reservation.getCustomer().getId()
        );
    }

    public static List<ReservationDto> toListDto(List<Reservation> reservations) {
        return reservations.stream().map(ReservationDto::toDto).toList();
    }

}
