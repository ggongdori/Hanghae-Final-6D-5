package com.hanghae.justpotluck.domain.ingredient.controller;

import com.hanghae.justpotluck.domain.ingredient.dto.request.IngredientSaveRequest;
import com.hanghae.justpotluck.domain.ingredient.dto.request.IngredientUpdateRequestDto;
import com.hanghae.justpotluck.domain.ingredient.dto.response.IngredientResponse;
import com.hanghae.justpotluck.domain.ingredient.service.IngredientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping("/board/{boardId}/ingredient")
    public IngredientResponse saveIngredient(@RequestBody IngredientSaveRequest requestDto, @PathVariable Long boardId) {
        return ingredientService.saveIngredient(requestDto, boardId);
    }
//    @PostMapping("/ingredient/include")
//    public List<IngredientResponse> saveIngredientSearch(@RequestBody IngredientRequestDto requestDto) {
//        return ingredientService.saveIngredientSearch(requestDto);
//    }
//    @PostMapping("/ingredient/exclude")
//    public List<IngredientResponse> saveIngredientSearch2(@RequestBody IngredientRequestDto requestDto) {
//        return ingredientService.saveIngredientSearch(requestDto);
//    }


    @PatchMapping("/board/ingredient/{ingredientId}")
    public ResponseEntity<IngredientResponse> updateProcess(@RequestBody IngredientUpdateRequestDto requestDto,
                                                            @PathVariable Long ingredientId) {
        return ResponseEntity.ok(ingredientService.updateIngredient(ingredientId, requestDto));
    }
    @DeleteMapping("/board/ingredient/{ingredientId}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long ingredientId) {
        ingredientService.deleteIngredient(ingredientId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
