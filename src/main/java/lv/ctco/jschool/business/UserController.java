package lv.ctco.jschool.business;

import lv.ctco.jschool.db.OrderRepository;
import lv.ctco.jschool.db.UserRepository;
import lv.ctco.jschool.entities.User;
import lv.ctco.jschool.model.UserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static lv.ctco.jschool.Consts.USER_PATH;

@RestController
@RequestMapping(USER_PATH)
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> GetUserById(@PathVariable("id") int id) {
        if (!userRepository.exists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            User user = userRepository.findOne(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @RequestMapping(path = "/{email}", method = RequestMethod.GET)
    public ResponseEntity<?> GetUserByEmail(@PathVariable("email") String email) {

        User user = userRepository.findUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody User user, UriComponentsBuilder b) {
        userRepository.save(user);

        UriComponents uriComponents =
                b.path(USER_PATH + "/{id}").buildAndExpand(user.getId());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(uriComponents.toUri());


        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUserById(@PathVariable("id") int id) {

        if (!userRepository.exists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            userRepository.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> putUserById(@PathVariable("id") int id, @RequestBody User user) {

        if (!userRepository.exists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            User u = userRepository.getOne(id);
            u.setPassword(user.getPassword());
            u.setEmail(user.getEmail());
            u.setLastName(user.getLastName());
            u.setFirstName(user.getFirstName());

            userRepository.save(u);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @RequestMapping(path = "/validateUser", method = RequestMethod.POST)
    public ResponseEntity<?> checkUser(@RequestBody UserCredentials userCredentials) {
        System.out.println("userCredentials = " + userCredentials);
        return new ResponseEntity<Object>(Boolean.TRUE, HttpStatus.OK);
    }

}