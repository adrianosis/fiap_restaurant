package br.com.fiap.fiaprestaurant.review.infra.controller;

import br.com.fiap.fiaprestaurant.review.application.usecases.*;
import br.com.fiap.fiaprestaurant.review.domain.entity.Review;
import br.com.fiap.fiaprestaurant.shared.exception.RestaurantException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/review")
@Tag(name = "Review", description = "Endpoints for managing reviews")
public class ReviewController {

    private final CreateReviewUseCase createReviewUseCase;
    private final FindAllReviewsByRestaurantIdUseCase findAllReviewsByRestaurantIdUseCase;
    private final FindAllReviewsByCustomerIdUseCase findAllReviewsByCustomerIdUseCase;
    private final FindReviewByIdUseCase findReviewByIdUseCase;
    private final RemoveReviewByIdUseCase removeReviewByIdUseCase;

    @PostMapping
    @Operation(summary = "Create a new review", description = "Creates a new review for a restaurant.")
    public ResponseEntity<ReviewResponseDto> createReview(@Valid @RequestBody ReviewRequestDto reviewRequestDto) throws RestaurantException {
        Review review = createReviewUseCase.execute(reviewRequestDto.toInput());
        return new ResponseEntity<>((ReviewResponseDto.toDto(review)), HttpStatus.CREATED);
    }

    @GetMapping("/restaurant/{restaurantId}")
    @Operation(summary = "Find all reviews by restaurant", description = "Returns a list of all reviews associated with a specific restaurant.")
    ResponseEntity<List<ReviewResponseDto>> findAllReviewsByRestaurantId(@PathVariable Long restaurantId) throws RestaurantException {
        List<Review> reviews = findAllReviewsByRestaurantIdUseCase.execute(restaurantId);
        return new ResponseEntity<>(ReviewResponseDto.toListDto(reviews), HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Find all reviews by customer", description = "Returns a list of all reviews made by a specific customer.")
    ResponseEntity<List<ReviewResponseDto>> findAllReviewsByCustomerId(@PathVariable Long customerId) throws RestaurantException {
        List<Review> reviews = findAllReviewsByCustomerIdUseCase.execute(customerId);
        return new ResponseEntity<>(ReviewResponseDto.toListDto(reviews), HttpStatus.OK);
    }

    @GetMapping("/{reviewId}")
    @Operation(summary = "Find review by ID", description = "Retrieves a specific review by its unique ID.")
    public ResponseEntity<ReviewResponseDto> findReviewById(@PathVariable Long reviewId) throws RestaurantException {
        Review review = findReviewByIdUseCase.execute(reviewId);
        return new ResponseEntity<>(ReviewResponseDto.toDto(review), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete review", description = "Deletes a specific review by its unique ID.")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) throws RestaurantException {
        removeReviewByIdUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
