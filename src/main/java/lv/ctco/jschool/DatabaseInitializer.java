package lv.ctco.jschool;

import lv.ctco.jschool.db.CafeRepository;
import lv.ctco.jschool.db.MealRepository;
import lv.ctco.jschool.db.UserRepository;
import lv.ctco.jschool.entities.Cafe;
import lv.ctco.jschool.entities.Meal;
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

    @Transactional
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Zoidberg");
        user.setEmail("zoid@clam.com");
        user.setPassword("1");
        userRepository.save(user);

        User user2 = new User();
        user.setFirstName("Zapp");
        user.setLastName("Brannigan");
        user.setEmail("zappie@gmail.com");
        user.setPassword("zapp");
        userRepository.save(user2);

        Meal meal = new Meal();
        meal.setMealName("Pigs blood");
        meal.setPrice(23);
        Meal meal2 = new Meal();
        meal2.setMealName("Russian set");
        meal2.setPrice(33);

        List<Meal> mealList = new ArrayList<>();
        mealList.add(meal);
        mealList.add(meal2);

        Cafe cafeBabe = new Cafe();
        cafeBabe.setCafeName("Cafe Babe");
        cafeBabe.setMealList(mealList);
        cafeBabe.setPhoneNr("23344556");
        cafeRepository.save(cafeBabe);
    }
}