package lv.ctco.jschool;

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

    private List<User> users = new ArrayList<User>();

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsers() {
        users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}/order", method = RequestMethod.POST)
    public ResponseEntity<?> addOrder(@PathVariable("id") int id, @RequestBody Order order) {

        if (order.getMeal().equals("")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            User u1 = userRepository.findOne(id);
            u1.setOrder(order);
            userRepository.save(u1);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @RequestMapping(path = "/{id}/order", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOrder(@PathVariable("id") int id, @PathVariable("id2") int id2) {
        User u1 = userRepository.findOne(id);
        u1.setOrder(null);
        userRepository.save(u1);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    /*@RequestMapping(path = "/{id}/order", method = RequestMethod.PUT)
    public ResponseEntity<?> changeOrder(@PathVariable("id") int id, @RequestBody lv.ctco.jschool.Order order) {

        lv.ctco.jschool.User u1 = userRepository.findOne(id);
        u1.setOrder(order);
        userRepository.save(u1);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    */

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> studentsPost(@RequestBody User user) {
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
