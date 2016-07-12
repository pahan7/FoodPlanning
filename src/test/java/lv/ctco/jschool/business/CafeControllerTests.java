package lv.ctco.jschool.business;

import io.restassured.http.Headers;
import lv.ctco.jschool.entities.Cafe;
import lv.ctco.jschool.entities.User;
import org.junit.Test;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static lv.ctco.jschool.Consts.BAD_ID;
import static lv.ctco.jschool.Consts.JSON;
import static lv.ctco.jschool.Consts.USER_PATH;
import static org.hamcrest.Matchers.equalTo;

public class CafeControllerTests {

    @Test
    public void postCafeTest() {
        Cafe cafe = new Cafe();
        cafe.setCafeName("cafe");
        cafe.setPhoneNr("1234");

        given().
                body(cafe).
                when().
                contentType(JSON).post(USER_PATH).then().statusCode(201);
    }

    @Test
    public void getAllCafesTestOk() {
        get(USER_PATH).then().statusCode(200);
    }

    @Test
    public void getCafeByIdTestOk() {
        Cafe cafe = new Cafe();
        cafe.setCafeName("cafe");
        cafe.setPhoneNr("1234");

        Headers header = given().contentType(JSON).body(cafe).when().post(USER_PATH).getHeaders();
        get(header.getValue("Location")).then().body("cafeName", equalTo("cafe"));
    }

    @Test
    public void getCafeByIdTestFail() {
        get(USER_PATH + BAD_ID).then().statusCode(404);
    }

    @Test
    public void deleteCafeByIdTestOk() {
        Cafe cafe = new Cafe();
        cafe.setCafeName("cafe");
        cafe.setPhoneNr("1234");

        Headers header = given().contentType(JSON).body(cafe).when().post(USER_PATH).getHeaders();
        delete(header.getValue("Location")).then().statusCode(200);

    }

    @Test
    public void deleteCafeByIdTestFail() {
        delete(USER_PATH + BAD_ID).then().statusCode(404);

    }

    @Test
    public void putCafeByIdTestOk() {
        Cafe cafe = new Cafe();
        cafe.setCafeName("cafe");
        cafe.setPhoneNr("1234");

        Headers header = given().contentType(JSON).body(cafe).when().post(USER_PATH).getHeaders();
        cafe.setCafeName("new cafe");
        cafe.setPhoneNr("3421");

        given().
                contentType(JSON).
                body(cafe).when().
                put(header.getValue("Location")).
                then().
                statusCode(200);
    }

    @Test
    public void putCafeByIdTestFail() {
        Cafe cafe = new Cafe();

        given().
                contentType(JSON).
                body(cafe).when().
                put(USER_PATH + BAD_ID).
                then().
                statusCode(404);
    }

}
