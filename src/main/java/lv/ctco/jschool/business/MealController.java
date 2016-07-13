package lv.ctco.jschool.business;

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
import java.util.List;

import static lv.ctco.jschool.Consts.*;
import static lv.ctco.jschool.Consts.USER_PATH;

@RestController
@RequestMapping(USER_PATH + "/{id}" + ORDER_PATH + "/{oid}" + MEAL_PATH)
public class MealController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addMeal(@PathVariable("id") int userId, @PathVariable("oid") int orderId, @RequestBody Meal meal, UriComponentsBuilder b) {
        User u1 = userRepository.findOne(userId);
        Order order = orderRepository.findByUserAndId(u1, orderId);
        List<Meal> meals = order.getMealList();
        meals.add(meal);
        orderRepository.save(order);
        UriComponents uriComponents =
                b.path(USER_PATH + "/{id}" + ORDER_PATH + "/{oid}" + MEAL_PATH + "/{mid}").buildAndExpand(u1.getId(), order.getOrderId(), meal.getId());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(uriComponents.toUri());

        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }
}
