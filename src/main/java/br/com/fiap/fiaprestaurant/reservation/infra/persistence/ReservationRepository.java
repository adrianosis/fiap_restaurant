package br.com.fiap.fiaprestaurant.reservation.infra.persistence;

import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
