package lv.ctco.jschool.business;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.parsing.Parser;
import lv.ctco.jschool.FoodplanningApplication;
import lv.ctco.jschool.entities.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static lv.ctco.jschool.Consts.CAFE_PATH;
import static lv.ctco.jschool.Consts.JSON;
import static lv.ctco.jschool.Consts.USER_PATH;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FoodplanningApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")
public class UserControllerTests {

    @Before
    public void before() {
        RestAssured.port = 8090;
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void getAllUsersTest() {
        get(CAFE_PATH).then().statusCode(200);
    }

    @Test
    public void getOneUserTestByEmail(){
        User user = new User();
        user.setFirstName("John");
        user.setLastName("The first");
        user.setEmail("john@john.com");
        user.setPassword("1234");
        Headers header = given().contentType(JSON).body(user).when().post(USER_PATH).getHeaders();
        get(header.getValue("Location")).then().body("password", equalTo("1234"));

    }
}
