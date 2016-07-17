package lv.ctco.jschool.controllers;

import lv.ctco.jschool.entities.UserRole;
import lv.ctco.jschool.repository.OrderRepository;
import lv.ctco.jschool.repository.UserRepository;
import lv.ctco.jschool.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import static lv.ctco.jschool.Consts.USER_PATH;

@RestController
@CrossOrigin
@RequestMapping(USER_PATH)
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(path = "/id{id}", method = RequestMethod.GET)
    public ResponseEntity<?> GetUserById(@PathVariable("id") int id, Principal principal) {
        if (!userRepository.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            User user = userRepository.findUserByEmail(principal.getName());
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @RequestMapping(path = "/{email}", method = RequestMethod.GET)
    public ResponseEntity<?> GetUserByEmail(@PathVariable("email") String email, Principal principal) {
        User user = userRepository.findUserByEmail(email);
        userRepository.findUserByEmail(principal.getName());
        if (userRepository.exists(user.getId())) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody User user, UriComponentsBuilder b) {
        if (userRepository.findUserByEmail(user.getEmail()) == null) {
            UserRole userRole = new UserRole();
            userRole.setRole("ROLE_USER");
            user.setUserRole(Arrays.asList(userRole));
//            user.setUserRoles(Arrays.asList(userRole));
            user.setPass(passwordEncoder.encode(user.getPass()));
            userRepository.save(user);
            UriComponents uriComponents =
                    b.path(USER_PATH + "/{id}").buildAndExpand(user.getId());

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setLocation(uriComponents.toUri());

            return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<?> addUserNoJSon(@ModelAttribute User user, UriComponentsBuilder b) {
        if (userRepository.findUserByEmail(user.getEmail()) == null) {
            UserRole userRole = new UserRole();
            userRole.setRole("USER");
            user.setUserRole(Arrays.asList(userRole));
//            user.setUserRoles(Arrays.asList(userRole));
            userRole.setUser(user);
            user.setPass(passwordEncoder.encode(user.getPass()));
            userRepository.save(user);
            UriComponents uriComponents =
                    b.path(USER_PATH + "/{id}").buildAndExpand(user.getId());

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setLocation(uriComponents.toUri());

            return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> putUserById(@PathVariable("id") int id, @RequestBody User user) {

        if (!userRepository.exists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            User u = userRepository.getOne(id);
            u.setPass(user.getPass());
            u.setEmail(user.getEmail());
            u.setLastName(user.getLastName());
            u.setFirstName(user.getFirstName());
            userRepository.save(u);
            return new ResponseEntity<>(HttpStatus.OK);
        }
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
}