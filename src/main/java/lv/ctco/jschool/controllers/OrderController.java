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

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static lv.ctco.jschool.Consts.ORDER_PATH;
import static lv.ctco.jschool.Consts.USER_PATH;

@RestController
@CrossOrigin
@RequestMapping(USER_PATH + ORDER_PATH)
public class OrderController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getOrder(Principal principal) {
        User user = userRepository.findUserByEmail(principal.getName());
        Order order = orderRepository.findByUser(user);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @RequestMapping(path = "/{oId}", method = RequestMethod.GET)
    public ResponseEntity<?> getOrder(@PathVariable("oId") int oId, Principal principal) {
        Order order = orderRepository.findOne(oId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> postMealToOrder(Principal principal,
                                             UriComponentsBuilder b) {
        User user = userRepository.findUserByEmail(principal.getName());
        Order order = orderRepository.findByUser(user);
        if (order != null) {
            if (order.isSubmited()) {
                Order newOrder = new Order();
                orderRepository.save(newOrder);
                return new ResponseEntity<>("ok ok", HttpStatus.CREATED);
            }
            return new ResponseEntity<>("user has unsubmited order ", HttpStatus.BAD_REQUEST);
        } else {
            Order newOrder = new Order();
            newOrder.setUser(user);
            orderRepository.save(newOrder);
            return new ResponseEntity<>("Ok 2", HttpStatus.CREATED);
        }
    }

    @RequestMapping(path = "/{orderId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateOrder(Principal principal,
                                         @PathVariable("orderId") int orderId,
                                         @RequestBody Meal meal,
                                         UriComponentsBuilder b) {
        User user = userRepository.findUserByEmail(principal.getName());
        Order order = orderRepository.findOne(orderId);
        List<Meal> mealList = order.getMealList();
        if (order.isSubmited()) {
            List<Meal> newMealList = new ArrayList<>();
            newMealList.add(meal);
            order.setMealList(newMealList);
            orderRepository.save(order);
            UriComponents uriComponents =
                    b.path(USER_PATH + "/" + user.getId() + ORDER_PATH + "/{orderId}").buildAndExpand(order.getOrderId());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setLocation(uriComponents.toUri());
            return new ResponseEntity<String>(responseHeaders, HttpStatus.OK);
        }
        mealList.add(meal);
        order.setMealList(mealList);
        orderRepository.save(order);
        UriComponents uriComponents =
                b.path(USER_PATH + "/" + user.getId() + ORDER_PATH + "/{orderId}").buildAndExpand(order.getOrderId());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(uriComponents.toUri());

        return new ResponseEntity<String>(responseHeaders, HttpStatus.OK);
    }

    @RequestMapping(path = "/{orderId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOrder(Principal principal,
                                         @PathVariable("orderId") int orderId) {
        orderRepository.delete(orderId);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @RequestMapping(path = "/{orderId}/{mealId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOneMealFromOrder(Principal principal,
                                                    @PathVariable("orderId") int orderId,
                                                    @PathVariable("mealId") int mealId) {

        if (orderRepository.exists(orderId)) {
            Order order = orderRepository.findOne(orderId);
            order.removeMeal(mealId);
            orderRepository.save(order);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
}
