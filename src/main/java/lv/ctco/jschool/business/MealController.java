package lv.ctco.jschool.business;

import lv.ctco.jschool.db.MealRepository;
import lv.ctco.jschool.db.OrderRepository;
import lv.ctco.jschool.db.UserRepository;
import lv.ctco.jschool.entities.Meal;
import lv.ctco.jschool.entities.Order;
import lv.ctco.jschool.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;

import static lv.ctco.jschool.Consts.*;
import static lv.ctco.jschool.Consts.USER_PATH;

@RestController
@CrossOrigin
@RequestMapping(USER_PATH + "/{id}" + MEAL_PATH)
public class MealController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MealRepository mealRepository;

    @Transactional
    @RequestMapping(path = "/{mId}",method = RequestMethod.POST)
    public ResponseEntity<?> addOneMeal(@PathVariable("id") int userId, @PathVariable("mId") int mId, UriComponentsBuilder b) {
        User user = userRepository.findOne(userId);
        if (userRepository.exists(userId)) {
            Order order = new Order();
            order.setUser(user);
            Meal meal = mealRepository.findOne(mId);
            order.setMeal(meal);
            orderRepository.save(order);
            UriComponents uriComponents =
                    b.path(USER_PATH + "/{id}" + MEAL_PATH + "/" + mId).buildAndExpand(order.getOrderId());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setLocation(uriComponents.toUri());
            return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
