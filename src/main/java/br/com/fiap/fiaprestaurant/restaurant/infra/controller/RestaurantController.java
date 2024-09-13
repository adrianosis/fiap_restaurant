package br.com.fiap.fiaprestaurant.restaurant.infra.controller;

import br.com.fiap.fiaprestaurant.restaurant.application.usecases.CreateRestaurantUseCase;
import br.com.fiap.fiaprestaurant.restaurant.application.usecases.FinsAllRestaurantsByNameOrLocationOrKitchenTypeUseCase;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final FinsAllRestaurantsByNameOrLocationOrKitchenTypeUseCase finsAllRestaurantsByNameOrLocationOrKitchenTypeUseCase;

    @PostMapping
    public ResponseEntity<RestaurantDto> create(@RequestBody SaveRestaurantDto dto) {
        Restaurant restaurant = createRestaurantUseCase.execute(dto.toDomain());
        return new ResponseEntity<>(new RestaurantDto(restaurant), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<List<RestaurantDto>> finsAllByNameOrLocationOrKitchenTypeUseCase(@RequestParam String name,
                                                                                           @RequestParam String location,
                                                                                           @RequestParam String kitchenType) {
        List<Restaurant> restaurants = finsAllRestaurantsByNameOrLocationOrKitchenTypeUseCase.execute(name, location, kitchenType);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
