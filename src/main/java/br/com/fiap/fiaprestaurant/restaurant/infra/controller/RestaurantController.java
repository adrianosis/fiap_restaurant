package br.com.fiap.fiaprestaurant.restaurant.infra.controller;

import br.com.fiap.fiaprestaurant.restaurant.application.usecases.CreateRestaurantUseCase;
import br.com.fiap.fiaprestaurant.restaurant.application.usecases.FindAllRestaurantsByNameOrLocationOrKitchenTypeUseCase;
import br.com.fiap.fiaprestaurant.restaurant.application.usecases.FindRestaurantByIdUseCase;
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
    private final FindRestaurantByIdUseCase findRestaurantByIdUseCase;
    private final FindAllRestaurantsByNameOrLocationOrKitchenTypeUseCase findAllRestaurantsByNameOrLocationOrKitchenTypeUseCase;

    @PostMapping
    public ResponseEntity<RestaurantDto> create(@RequestBody SaveRestaurantRequestDto requestDto) {
        Restaurant restaurant = createRestaurantUseCase.execute(requestDto.toDomain());
        return new ResponseEntity<>(RestaurantDto.toDto(restaurant), HttpStatus.CREATED);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDto> findRestaurantById(@PathVariable long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantByIdUseCase.execute(restaurantId);
        return new ResponseEntity<>(RestaurantDto.toDto(restaurant), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDto>> finsAllByNameOrLocationOrKitchenType(@RequestParam String name,
                                                                                    @RequestParam String location,
                                                                                    @RequestParam String kitchenType) {
        List<Restaurant> restaurants = findAllRestaurantsByNameOrLocationOrKitchenTypeUseCase.execute(name, location, kitchenType);
        return new ResponseEntity<>(RestaurantDto.toListDto(restaurants), HttpStatus.OK);
    }

}
