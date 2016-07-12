package lv.ctco.jschool.business;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import lv.ctco.jschool.FoodplanningApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static io.restassured.RestAssured.get;
import static lv.ctco.jschool.Consts.CAFE_PATH;

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


}
