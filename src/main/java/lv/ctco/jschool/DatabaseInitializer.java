package lv.ctco.jschool;

import lv.ctco.jschool.db.CafeRepository;
import lv.ctco.jschool.db.MealRepository;
import lv.ctco.jschool.db.OrderRepository;
import lv.ctco.jschool.db.UserRepository;
import lv.ctco.jschool.entities.Cafe;
import lv.ctco.jschool.entities.Meal;
import lv.ctco.jschool.entities.Order;
import lv.ctco.jschool.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    CafeRepository cafeRepository;
    @Autowired
    MealRepository mealRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;

    @Transactional
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Zoidberg");
        user.setEmail("zoid@clam.com");
        user.setPassword("1");
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

        Order order = new Order();
        order.setMeal(meal);
        order.setUser(user);
        orderRepository.save(order);

    }
}
