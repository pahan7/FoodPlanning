package lv.ctco.jschool;

import lv.ctco.jschool.entities.*;
import lv.ctco.jschool.repository.CafeRepository;
import lv.ctco.jschool.repository.OrderRepository;
import lv.ctco.jschool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    CafeRepository cafeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        User user = new User();
        Order order = new Order();
        user.setFirstName("John");
        user.setLastName("Zoidberg");
        user.setEmail("zoid@clam.com");
        user.setPassword(passwordEncoder.encode("1234"));
        UserRoles userRoles = new UserRoles();
        userRoles.setRole("USER");
        user.setUserRoles(Arrays.asList(userRoles));
        userRepository.save(user);

        Meal meal = new Meal();
        meal.setMealName("Pigs blood");
        meal.setPrice(23);

        List<Meal> mealList = new ArrayList<>();
        mealList.add(meal);

        Cafe cafeBabe = new Cafe();
        cafeBabe.setCafeName("Cafe Babe");
        cafeBabe.setMealList(mealList);
        cafeBabe.setPhoneNr("23344556");
        cafeRepository.save(cafeBabe);


        order.setMealList(meal);
        order.setUser(user);
        orderRepository.save(order);


        User admin = new User();
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setEmail("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        UserRoles adminRole = new UserRoles();
        adminRole.setRole("ROLE_ADMIN");
        admin.setUserRoles(Arrays.asList(adminRole));
        userRepository.save(admin);
    }
}
