package br.com.fiap.fiaprestaurant.review.infra.controller;

import br.com.fiap.fiaprestaurant.review.domain.entity.Review;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDto {

    private long id;
    private int score;
    private String comment;
    private long reservationId;

    public static ReviewResponseDto toDto(Review review) {
        return new ReviewResponseDto(
                review.getId(),
                review.getScore(),
                review.getComment(),
                review.getReservation() != null ? review.getReservation().getId() : 0);
    }

    public static List<ReviewResponseDto> toListDto(List<Review> reviews) {
        return reviews.stream().map(ReviewResponseDto::toDto).toList();

    }
}
