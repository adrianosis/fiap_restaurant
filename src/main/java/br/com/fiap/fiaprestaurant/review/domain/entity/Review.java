package br.com.fiap.fiaprestaurant.review.domain.entity;

import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Review {

    private long id;
    private int score;
    private String comment;
    private Reservation reservation;

    public Review(int score, String comment, Reservation reservation) {
        this.score = score;
        this.comment = comment;
        this.reservation = reservation;
    }

}
