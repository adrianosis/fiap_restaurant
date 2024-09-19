package br.com.fiap.fiaprestaurant.review.infra.controller;

import br.com.fiap.fiaprestaurant.review.application.input.ReviewInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDto {
    private int score;
    private String comment;
    private long reservationId;

    public ReviewInput toInput() {
        return new ReviewInput(this.score, this.comment, this.reservationId);
    }

}