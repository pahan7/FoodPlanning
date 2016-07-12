package lv.ctco.jschool;

import lv.ctco.jschool.entities.Cafe;
import lv.ctco.jschool.entities.Meal;
import lv.ctco.jschool.entities.Order;
import lv.ctco.jschool.entities.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static io.restassured.RestAssured.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FoodplanningApplication.class)
@WebAppConfiguration
public class FoodplanningApplicationTests {

    @Test
    public void addUserTest() {
        User user = new User();
        user.setFirstName("Alina");

        given().
        body(user).
        when().
        contentType("application/json").post("/users").then().statusCode(201);
    }

    @Test
    public void addOrderTest() {
        Order order = new Order();

        given().
                body(order).
                when().
                contentType("application/json").post("/users/1/order").then().statusCode(201);
    }

    @Test
    public void addMealTest() {


        Meal meal = new Meal();
        meal.setPrice(2.00);
        meal.setMealName("Chicken");
        given().
                body(meal).
                when().
                contentType("application/json").post("users/1/order/1/meal").then().statusCode(201);
    }

    @Test
    public void addCafeTest() {
        Cafe cafe = new Cafe();
        cafe.setCafeName("MyCafe");
        cafe.setPhoneNr("1234");
        given().
                body(cafe).
                when().
                contentType("application/json").post("/cafes").then().statusCode(201);
    }

    @Test
    public void getCafesTest() {
        get("/cafes").then().statusCode(200);
    }

    @Test
    public void getUsersTest() {
        get("/users").then().statusCode(200);
    }

    @Test
    public void getOrdersByUserIdTest() {
        get("/users/1/orders").then().statusCode(200);
    }

}
