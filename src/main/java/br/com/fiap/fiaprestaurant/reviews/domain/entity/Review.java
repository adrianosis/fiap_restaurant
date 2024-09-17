package br.com.fiap.fiaprestaurant.reviews.domain.entity;

import br.com.fiap.fiaprestaurant.reservation.domain.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
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
