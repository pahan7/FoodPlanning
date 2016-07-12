package lv.ctco.jschool.business;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.parsing.Parser;
import lv.ctco.jschool.FoodplanningApplication;
import lv.ctco.jschool.entities.Order;
import lv.ctco.jschool.entities.User;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static lv.ctco.jschool.Consts.BAD_ID;
import static lv.ctco.jschool.Consts.ORDER_PATH;
import static lv.ctco.jschool.Consts.USER_PATH;
import static org.junit.Assert.*;
import static org.springframework.http.HttpStatus.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FoodplanningApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")
public class OrderControllerTest {

    @Before
    public void before() {
        RestAssured.port = 8090;
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void getOrdersTest() {
        User user = new User();
        user.setFirstName("John");
        Headers header = given().contentType("application/json").body(user).when().post(USER_PATH).getHeaders();

        get(header.getValue("Location") + ORDER_PATH).then().statusCode(OK.value());
    }

    @Test
    public void getOrdersFailTest() {
        get(USER_PATH + BAD_ID + ORDER_PATH).then().statusCode(NOT_FOUND.value());
    }

    @Test
    public void postOrderTest() throws Exception {
        User user = new User();
        user.setFirstName("John");
        Headers header = given().contentType("application/json").body(user).when().post(USER_PATH).getHeaders();

        Order order = new Order();

        given().contentType("application/json").body(order).when().post(header.getValue("Location") + ORDER_PATH).then().statusCode(CREATED.value());

    }
}