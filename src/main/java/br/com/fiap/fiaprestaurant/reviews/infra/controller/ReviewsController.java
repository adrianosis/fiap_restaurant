package br.com.fiap.fiaprestaurant.reviews.infra.controller;

import br.com.fiap.fiaprestaurant.reviews.domain.entity.Reviews;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.fiap.fiaprestaurant.reviews.application.usecases.CreateReviewsUseCase;

@RestController
@RequestMapping("/reviews")
public class ReviewsController {

    private final CreateReviewsUseCase createReviewsUseCase;

    public ReviewsController(CreateReviewsUseCase createReviewsUseCase) {
        this.createReviewsUseCase = createReviewsUseCase;
    }

    @PostMapping
    public ResponseEntity<String> createReview(@RequestBody ReviewsDto reviewsDto) {
        createReviewsUseCase.create(reviewsDto.toDomain());
        return new ResponseEntity<>("Obrigado pela sua avaliação", HttpStatus.CREATED);
    }
}
