package br.com.fiap.fiaprestaurant.restaurant.infra.controller;

import br.com.fiap.fiaprestaurant.restaurant.application.usecases.CreateRestaurantUseCase;
import br.com.fiap.fiaprestaurant.restaurant.application.usecases.FindAllRestaurantsByNameOrLocationOrKitchenTypeUseCase;
import br.com.fiap.fiaprestaurant.restaurant.application.usecases.FindRestaurantByIdUseCase;
import br.com.fiap.fiaprestaurant.restaurant.application.usecases.UpdateRestaurantUseCase;
import br.com.fiap.fiaprestaurant.restaurant.domain.entity.Restaurant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
@Tag(name = "Restaurant", description = "Operations related to restaurant management such as creating, searching, and retrieving restaurants.")
public class RestaurantController {

    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final UpdateRestaurantUseCase updateRestaurantUseCase;
    private final FindRestaurantByIdUseCase findRestaurantByIdUseCase;
    private final FindAllRestaurantsByNameOrLocationOrKitchenTypeUseCase findAllRestaurantsByNameOrLocationOrKitchenTypeUseCase;

    @PostMapping
    @Operation(summary = "Create a new restaurant", description = "Creates a new restaurant entry in the system based on the provided information.")
    public ResponseEntity<RestaurantDto> create(@Valid @RequestBody SaveRestaurantRequestDto requestDto) {
        Restaurant restaurant = createRestaurantUseCase.execute(requestDto.toDomain());
        return new ResponseEntity<>(RestaurantDto.toDto(restaurant), HttpStatus.CREATED);
    }

    @PutMapping("/{restaurantId}")
    @Operation(summary = "Update a restaurant", description = "Update a specific restaurant.")
    public ResponseEntity<RestaurantDto> updateRestaurant(@PathVariable long restaurantId,
                                                          @Valid @RequestBody SaveRestaurantRequestDto requestDto) {
        Restaurant restaurant = updateRestaurantUseCase.execute(restaurantId, requestDto.toDomain());
        return new ResponseEntity<>(RestaurantDto.toDto(restaurant), HttpStatus.OK);
    }

    @GetMapping("/{restaurantId}")
    @Operation(summary = "Find a restaurant by ID", description = "Finds a specific restaurant by its unique ID.")
    public ResponseEntity<RestaurantDto> findRestaurantById(@PathVariable long restaurantId) {
        Restaurant restaurant = findRestaurantByIdUseCase.execute(restaurantId);
        return new ResponseEntity<>(RestaurantDto.toDto(restaurant), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Find restaurants by name, location, or kitchen type", description = "Finds restaurants that match the provided name, location, or kitchen type criteria.")
    public ResponseEntity<List<RestaurantDto>> finsAllByNameOrLocationOrKitchenType(@RequestParam String name,
                                                                                    @RequestParam String location,
                                                                                    @RequestParam String kitchenType) {
        List<Restaurant> restaurants = findAllRestaurantsByNameOrLocationOrKitchenTypeUseCase.execute(name, location, kitchenType);
        return new ResponseEntity<>(RestaurantDto.toListDto(restaurants), HttpStatus.OK);
    }

}
