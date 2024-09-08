package br.com.fiap.fiaprestaurant.restaurant.infra.controller;

import br.com.fiap.fiaprestaurant.restaurant.application.usecases.RestaurantUseCase;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantUseCase createRestaurantUseCase;

    @PostMapping
    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody SaveRestaurantDto dto) {
        Restaurant restaurant = createRestaurantUseCase.create(dto.toDomain());
        return new ResponseEntity<>(new RestaurantDto(restaurant), HttpStatus.CREATED);
    }

}
