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
        user.setPass(passwordEncoder.encode("1234"));
        UserRole userRole = new UserRole();
        userRole.setRole("USER");
        user.setUserRole(Arrays.asList(userRole));
        userRole.setUser(user);
        userRepository.save(user);

        Meal mealOne = new Meal();
        mealOne.setMealName("Pigs blooood");
        mealOne.setPrice(23);

        Meal mealTwo = new Meal();
        mealTwo.setMealName("Pigs blood");
        mealTwo.setPrice(25);

        List<Meal> mealList1 = new ArrayList<>();
        mealList1.add(mealOne);

        List<Meal> mealList2 = new ArrayList<>();
        mealList2.add(mealTwo);



        Cafe cafeBabe = new Cafe();
        cafeBabe.setCafeName("Cafe Babe");
        cafeBabe.setMealList(mealList1);
        cafeBabe.setPhoneNr("23344556");
        cafeRepository.save(cafeBabe);

//
//        order.setMealList(meal);
//        order.setUser(user);
//        orderRepository.save(order);
        Cafe cafeBabe2 = new Cafe();
        cafeBabe2.setCafeName("Cafe Babe2");
        cafeBabe2.setMealList(mealList2);
        cafeBabe2.setPhoneNr("233441116");
        cafeRepository.save(cafeBabe2);


        order.setMealList(mealOne);
        order.setUser(user);
        orderRepository.save(order);


        User admin = new User();
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setEmail("admin@gmail.com");
        admin.setPass(passwordEncoder.encode("admin"));
        UserRole adminRole = new UserRole();
        adminRole.setUser(admin);
        adminRole.setRole("ROLE_ADMIN");
        admin.setUserRole(Arrays.asList(adminRole));
        userRepository.save(admin);



//
//        order.setMealList(mealOne);
//        order.setUser(admin);
//        orderRepository.save(order);
    }
}
