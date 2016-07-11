import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    private List<User> users = new ArrayList<User>();


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



    /*@RequestMapping(path = "/{id}/order", method = RequestMethod.PUT)
    public ResponseEntity<?> changeOrder(@PathVariable("id") int id, @RequestBody Order order) {

        User u1 = userRepository.findOne(id);
        u1.setOrder(order);
        userRepository.save(u1);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    */


}
