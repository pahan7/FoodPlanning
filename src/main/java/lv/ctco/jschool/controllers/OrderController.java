package lv.ctco.jschool.controllers;

import lv.ctco.jschool.repository.OrderRepository;
import lv.ctco.jschool.repository.UserRepository;
import lv.ctco.jschool.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static lv.ctco.jschool.Consts.ORDER_PATH;
import static lv.ctco.jschool.Consts.USER_PATH;

@RestController
@CrossOrigin
@RequestMapping(USER_PATH + "/{id}" + ORDER_PATH)
public class OrderController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getOrder(@PathVariable("id") int id) {
        if (orderRepository.exists(id)) {
            Order order = orderRepository.findOne(id);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
