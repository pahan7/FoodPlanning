package lv.ctco.jschool.business;

import lv.ctco.jschool.db.OrderRepository;
import lv.ctco.jschool.db.UserRepository;
import lv.ctco.jschool.entities.Order;
import lv.ctco.jschool.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static lv.ctco.jschool.Consts.ORDER_PATH;
import static lv.ctco.jschool.Consts.USER_PATH;

@RestController
@RequestMapping(USER_PATH + "/{id}" + ORDER_PATH)
public class OrderController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getOrders(@PathVariable("id") int id) {
        if (!userRepository.exists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            User u1 = userRepository.findOne(id);
            List<Order> orders = orderRepository.findByUser(u1);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }
    }



    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addOrder(@PathVariable("id") int id, @RequestBody Order order, UriComponentsBuilder b) {
        User u1 = userRepository.findOne(id);
        order.setUser(u1);
        orderRepository.save(order);


        UriComponents uriComponents =
                b.path(USER_PATH + "/{id}" + ORDER_PATH + "/{oid}").buildAndExpand(u1.getId(), order.getOrderId());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(uriComponents.toUri());

        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }
}
