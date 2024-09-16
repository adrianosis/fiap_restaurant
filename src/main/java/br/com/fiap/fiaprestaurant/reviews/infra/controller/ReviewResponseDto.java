package br.com.fiap.fiaprestaurant.reviews.infra.controller;

import br.com.fiap.fiaprestaurant.reservation.infra.controller.ReservationDto;
import br.com.fiap.fiaprestaurant.reviews.domain.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

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
                review.getReservation().getId());
    }

    public static List<ReviewResponseDto> toListDto(List<Review> reviews) {
        return reviews.stream().map(ReviewResponseDto::toDto).toList();

    }
}
