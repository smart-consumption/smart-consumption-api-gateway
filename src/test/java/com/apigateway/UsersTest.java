package com.apigateway;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersTest{
    @LocalServerPort
    private int port=8080;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void givenValidUserRequest_whenCreateUser_thenUserCreated() {
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body("{\"username\":\"test user\",\"name\":\"user\", \"reviews\":\"[]\", \"watchList\":\"[]\", \"city\":{\"name\":\"Popayan\", \"department\":\"Cauca\"}}")
                .when()
                .post("/smart-consumption/api/v1/user")
                .then()
                .statusCode(201)
                .body("status", equalTo(200))
                .body("message", equalTo("Información almacenada exitosamente!"))
                .body("data[0].username", equalTo("test user"))
                .body("data[0].name", equalTo("user"))
                .body("data[0].city.name", equalTo("Popayan"))
                .body("data[0].city.department", equalTo("Cauca"));
    }

    @Test
    public void givenValidRequest_whenGetUser_thenCorrectResponse() {
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .get("/smart-consumption/api/v1/user")
                .then()
                .statusCode(200)
                .body("status", equalTo(200))
                .body("message", equalTo("Información consultada exitosamente!"))
                .body("data[0].username", equalTo("test user"))
                .body("data[0].name", equalTo("user"))
                .body("data[0].city.name", equalTo("Popayan"))
                .body("data[0].city.department", equalTo("Cauca"));
    }


}