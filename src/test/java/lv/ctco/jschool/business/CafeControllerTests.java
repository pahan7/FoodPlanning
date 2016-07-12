package lv.ctco.jschool.business;

import io.restassured.http.Headers;
import lv.ctco.jschool.entities.Cafe;
import lv.ctco.jschool.entities.User;
import org.junit.Test;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import static lv.ctco.jschool.Consts.*;

public class CafeControllerTests {

    @Test
    public void postCafeTest() {
        Cafe cafe = new Cafe();
        cafe.setCafeName("cafe");
        cafe.setPhoneNr("1234");

        given().
                body(cafe).
                when().
                contentType(JSON).post(CAFE_PATH).then().statusCode(201);
    }

    @Test
    public void getAllCafesTestOk() {
        get(CAFE_PATH).then().statusCode(200);
    }

    @Test
    public void getCafeByIdTestOk() {
        Cafe cafe = new Cafe();
        cafe.setCafeName("cafe");
        cafe.setPhoneNr("1234");

        Headers header = given().contentType(JSON).body(cafe).when().post(CAFE_PATH).getHeaders();
        get(header.getValue("Location")).then().body("cafeName", equalTo("cafe"));
    }

    @Test
    public void getCafeByIdTestFail() {
        get(CAFE_PATH + BAD_ID).then().statusCode(404);
    }

    @Test
    public void deleteCafeByIdTestOk() {
        Cafe cafe = new Cafe();
        cafe.setCafeName("cafe");
        cafe.setPhoneNr("1234");

        Headers header = given().contentType(JSON).body(cafe).when().post(CAFE_PATH).getHeaders();
        delete(header.getValue("Location")).then().statusCode(200);

    }

    @Test
    public void deleteCafeByIdTestFail() {
        delete(CAFE_PATH + BAD_ID).then().statusCode(404);

    }

    @Test
    public void putCafeByIdTestOk() {
        Cafe cafe = new Cafe();
        cafe.setCafeName("cafe");
        cafe.setPhoneNr("1234");

        Headers header = given().contentType(JSON).body(cafe).when().post(CAFE_PATH).getHeaders();
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
                put(CAFE_PATH + BAD_ID).
                then().
                statusCode(404);
    }

}
