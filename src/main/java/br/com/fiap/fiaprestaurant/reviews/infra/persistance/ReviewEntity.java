package br.com.fiap.fiaprestaurant.reviews.infra.persistance;

import br.com.fiap.fiaprestaurant.reservation.infra.persistence.ReservationEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "review")
public class ReviewEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    @Column(name = "score", length = 5)
    private int score;
    @Column(name = "comment", length = 500)
    private String comment;
    @OneToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private ReservationEntity reservation;
}
