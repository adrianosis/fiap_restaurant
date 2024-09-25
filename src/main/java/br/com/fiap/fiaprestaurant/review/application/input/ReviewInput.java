package br.com.fiap.fiaprestaurant.review.application.input;

import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewInput {

    private int score;
    private String comment;
    private long reservationId;

}
