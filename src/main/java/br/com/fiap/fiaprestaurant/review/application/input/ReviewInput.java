package br.com.fiap.fiaprestaurant.review.application.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewInput {

    private int score;
    private String comment;
    private long reservationId;

}
