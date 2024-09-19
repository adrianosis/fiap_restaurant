package br.com.fiap.fiaprestaurant.review.infra.controller;

import br.com.fiap.fiaprestaurant.review.application.usecases.*;
import br.com.fiap.fiaprestaurant.review.domain.entity.Review;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final CreateReviewUseCase createReviewUseCase;
    private final FindAllReviewsByRestaurantIdUseCase findAllReviewsByRestaurantIdUseCase;
    private final FindAllReviewsByCustomerIdUseCase findAllReviewsByCustomerIdUseCase;
    private final FindReviewByIdUseCase findReviewByIdUseCase;
    private final RemoveReviewByIdUseCase removeReviewByIdUseCase;

    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(@RequestBody ReviewRequestDto reviewRequestDto) throws Exception {
        Review review = createReviewUseCase.execute(reviewRequestDto.toInput());
        return new ResponseEntity<>((ReviewResponseDto.toDto(review)), HttpStatus.CREATED);
    }

    @GetMapping("/restaurant/{restaurantId}")
    ResponseEntity<List<ReviewResponseDto>> findAllReviewsByRestaurantId(@PathVariable Long restaurantId) throws Exception {
        List<Review> reviews = findAllReviewsByRestaurantIdUseCase.execute(restaurantId);
        return new ResponseEntity<>(ReviewResponseDto.toListDto(reviews), HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    ResponseEntity<List<ReviewResponseDto>> findAllReviewsByCustomerId(@PathVariable Long customerId) throws Exception {
        List<Review> reviews = findAllReviewsByCustomerIdUseCase.execute(customerId);
        return new ResponseEntity<>(ReviewResponseDto.toListDto(reviews), HttpStatus.OK);
    }

    @GetMapping("/{reviewId}")
    ResponseEntity<ReviewResponseDto> findReviewById(@PathVariable Long reviewId) throws Exception {
        Review review = findReviewByIdUseCase.execute(reviewId);
        return new ResponseEntity<>(ReviewResponseDto.toDto(review), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@Valid @PathVariable Long id) throws Exception {
        removeReviewByIdUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
