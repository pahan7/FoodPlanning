
package lv.ctco.jschool;

import lv.ctco.jschool.db.OrderRepository;
import lv.ctco.jschool.db.UserRepository;
import lv.ctco.jschool.entities.Meal;
import lv.ctco.jschool.entities.Order;
import lv.ctco.jschool.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsers() {
        List <User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}/order/{id2}/meal", method = RequestMethod.POST)
    public ResponseEntity<?> addMeal(@PathVariable("id") int userId, @PathVariable("id2") int orderId, @RequestBody Meal meal) {
        User u1 = userRepository.findOne(userId);
        List <Order> orders = orderRepository.findByUser(u1);
        Order order = new Order();
        for (Order o : orders){
            if (o.getOrderId() == orderId)
                order = o;
        }
        List <Meal> meals = order.getMealList();
        meals.add(meal);
        order.setMealList(meals);
        orderRepository.save(order);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{id}/orders",method = RequestMethod.GET)
    public ResponseEntity<?> getOrders(@PathVariable("id") int id) {
        User u1 = userRepository.findOne(id);
        List<Order> orders = orderRepository.findByUser(u1);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}/order", method = RequestMethod.POST)
    public ResponseEntity<?> addOrder(@PathVariable("id") int id, @RequestBody Order order) {
        User u1 = userRepository.findOne(id);
        order.setUser(u1);
        orderRepository.save(order);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody User user) {
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}