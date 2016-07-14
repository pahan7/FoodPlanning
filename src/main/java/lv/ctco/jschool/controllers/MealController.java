package lv.ctco.jschool.controllers;

import lv.ctco.jschool.entities.Cafe;
import lv.ctco.jschool.repository.CafeRepository;
import lv.ctco.jschool.repository.OrderRepository;
import lv.ctco.jschool.repository.UserRepository;
import lv.ctco.jschool.entities.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;

import java.util.List;

import static lv.ctco.jschool.Consts.*;


@RestController
@CrossOrigin
@RequestMapping(CAFE_PATH + "/{cId}" + MEAL_PATH)
public class MealController {

    @Autowired
    CafeRepository cafeRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> addOneMeal(@PathVariable("cId") int cafeId) {
        if (cafeRepository.exists(cafeId)) {
            Cafe cafe = cafeRepository.findOne(cafeId);
            List<Meal> meals = cafe.getMealList();
            return new ResponseEntity<>(meals, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addOneMeal(@PathVariable("cId") int cafeId,
                                        @RequestBody Meal inputMeal, UriComponentsBuilder b) {
        if (cafeRepository.exists(cafeId)) {
            Cafe cafe = cafeRepository.findOne(cafeId);
            Meal meal = inputMeal;
            meal.setCafeId(cafe.getId());
            if (cafe.addToMeals(meal)) {
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @Transactional
    @RequestMapping(path = "/{mId}", method = RequestMethod.PUT)
    public ResponseEntity<?> modifyMeal(@PathVariable("cId") int cafeId, @PathVariable("mId") int mealId, @RequestBody Meal inputMeal) {
        if (cafeRepository.exists(cafeId)) {
            Cafe cafe = cafeRepository.findOne(cafeId);
            Meal meal = inputMeal;
            meal.setId(mealId);
            if (cafe.updateMeal(meal, mealId)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @Transactional
    @RequestMapping(path = "/{mId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> modifyMeal(@PathVariable("cId") int cafeId, @PathVariable("mId") int mealId) {
        if (cafeRepository.exists(cafeId)) {
            Cafe cafe = cafeRepository.findOne(cafeId);
            if (cafe.deleteMeal(mealId)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
