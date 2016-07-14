package lv.ctco.jschool.controllers;

import lv.ctco.jschool.entities.Meal;
import lv.ctco.jschool.entities.User;
import lv.ctco.jschool.repository.OrderRepository;
import lv.ctco.jschool.repository.UserRepository;
import lv.ctco.jschool.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static lv.ctco.jschool.Consts.ORDER_PATH;
import static lv.ctco.jschool.Consts.USER_PATH;

@RestController
@CrossOrigin
@RequestMapping(USER_PATH + "/{userId}" + ORDER_PATH)
public class OrderController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getOrder(@PathVariable("userId") int userId) {
        if (userRepository.exists(userId)) {
            Order order = orderRepository.findByUser(userRepository.findOne(userId));
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public void createOrder(int userId, Meal meal) {
        Order order = new Order();
        User user = userRepository.findOne(userId);
        order.setMealList(meal);
        order.setUser(user);
        orderRepository.saveAndFlush(order);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> postMealToOrder(@PathVariable("userId") int userId,
                                             @RequestBody Meal meal,
                                             UriComponentsBuilder b) {
        if (userRepository.exists(userId)) {
            User user = userRepository.findOne(userId);
            Order order = orderRepository.findByUser(user);
            if (order != null) {
                order.setMealList(meal);
                orderRepository.save(order);

                UriComponents uriComponents =
                        b.path(USER_PATH + "/" + userId + ORDER_PATH + "/{orderId}").buildAndExpand(order.getOrderId());
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.setLocation(uriComponents.toUri());

                return new ResponseEntity<String>(responseHeaders, HttpStatus.CREATED);
            } else {
                createOrder(userId, meal);
                return new ResponseEntity<String>(HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "/{orderId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateOrder(@PathVariable("userId") int userId,
                                         @PathVariable("orderId") int orderId,
                                         @RequestBody Meal meal,
                                         UriComponentsBuilder b) {
        if (userRepository.exists(userId)) {
            Order order = orderRepository.findOne(orderId);
            List<Meal> mealList = new ArrayList<>();
            mealList.add(meal);
            order.setMealList(mealList);
            orderRepository.save(order);
            UriComponents uriComponents =
                    b.path(USER_PATH + "/" + userId + ORDER_PATH + "/{orderId}").buildAndExpand(order.getOrderId());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setLocation(uriComponents.toUri());

            return new ResponseEntity<String>(responseHeaders, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "/{orderId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOrder(@PathVariable("userId") int userId,
                                         @PathVariable("orderId") int orderId) {
        if (userRepository.exists(userId)) {
            orderRepository.delete(orderId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "/{orderId}/{mealId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOneMealFromOrder(@PathVariable("userId") int userId,
                                                    @PathVariable("orderId") int orderId,
                                                    @PathVariable("mealId") int mealId){
        if (userRepository.exists(userId)) {
            if(orderRepository.exists(orderId)){
                Order order = orderRepository.findOne(orderId);
                //TODO
                List<Meal> mealList = order.getMealList();
                Meal meal = mealList.get(mealId);
                mealList.remove(meal);
                order.setMealList(mealList);
                orderRepository.save(order);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
