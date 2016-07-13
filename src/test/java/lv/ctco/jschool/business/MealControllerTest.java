package lv.ctco.jschool.business;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.parsing.Parser;
import lv.ctco.jschool.FoodplanningApplication;
import lv.ctco.jschool.entities.Meal;
import lv.ctco.jschool.entities.Order;
import lv.ctco.jschool.entities.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static io.restassured.RestAssured.given;
import static lv.ctco.jschool.Consts.*;
import static org.junit.Assert.*;
import static org.springframework.http.HttpStatus.CREATED;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FoodplanningApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")
public class MealControllerTest {
    @Before
    public void before() {
        RestAssured.port = 8090;
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void postMealTest() throws Exception {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("The first");
        user.setEmail("john@john.com");
        user.setPassword("1234");
        Headers header = given().contentType(JSON).body(user).when().post(USER_PATH).getHeaders();

        Order order = new Order();
        Headers header1 = given().contentType(JSON).body(order).when().post(header.getValue("Location") + ORDER_PATH).getHeaders();

        Meal meal = new Meal();
        given().contentType(JSON).body(order).when().post(header1.getValue("Location") + MEAL_PATH).then().statusCode(CREATED.value());
    }
}