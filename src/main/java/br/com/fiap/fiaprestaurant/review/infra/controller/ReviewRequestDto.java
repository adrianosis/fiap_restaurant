package br.com.fiap.fiaprestaurant.review.infra.controller;

import br.com.fiap.fiaprestaurant.review.application.input.ReviewInput;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ReviewRequestDto {

    @Min(value = 1, message = "The score must be at least 1.")
    @Max(value = 5, message = "The score must be no greater than 5.")
    private int score;
    @NotBlank(message = "The comment cannot be blank.")
    @Size(max = 500, message = "The comment must have at most 500 characters.")
    private String comment;
    @NotNull(message = "The reservation ID cannot be null.")
    private int reservationId;

    public ReviewInput toInput() {
        return new ReviewInput(this.score, this.comment, this.reservationId);
    }

}