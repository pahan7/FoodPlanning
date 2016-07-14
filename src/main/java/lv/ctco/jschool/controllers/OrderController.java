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
        userRepository.findOne(userId);
        if (userRepository.exists(userId)) {
           List<Order> order = orderRepository.findAll();
                return new ResponseEntity<>(order, HttpStatus.OK);
            }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public void createOrder(int userId, Meal meal) {
        Order order = new Order();
        User user = userRepository.findOne(userId);
        order.setMealList(meal);
        order.setUser(user);
        userRepository.flush();
        orderRepository.saveAndFlush(order);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> postMealToOrder(@PathVariable("userId") int userId,
                                             @RequestBody Meal meal,
                                             UriComponentsBuilder b) {
        if (userRepository.exists(userId)) {
            User user = userRepository.findOne(userId);
            Order order = user.getOrder();
            if (orderRepository.exists(order.getOrderId())) {
                order.setMealList(meal);
                userRepository.save(user);
                orderRepository.save(order);
                UriComponents uriComponents =
                        b.path(USER_PATH + "/" + userId + ORDER_PATH + "/{orderId}").buildAndExpand(order.getOrderId());
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.setLocation(uriComponents.toUri());

                return new ResponseEntity<String>(responseHeaders, HttpStatus.CREATED);
            } else {
                userRepository.save(user);
                createOrder(userId, meal);
                return new ResponseEntity<String>(HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
