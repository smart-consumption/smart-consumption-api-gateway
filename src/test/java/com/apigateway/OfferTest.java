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
public class OfferTest {
    @LocalServerPort
    private int port;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void givenValidOfferRequest_whenCreateOffer_thenOfferCreated() {
        String productId = "35057b47-aff7-423b-9e84-53022e2bc1b6";
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body("{\"description\":\"Descuento en shampoo\",\"startDate\":\"2023-09-20T14:30:00\",\"endDate\":\"2023-09-30T14:30:00\",\"discountPercentage\":20}")
                .when()
                .post("/smart-consumption/api/v1/offer/" + productId)
                .then()
                .statusCode(201)
                .body("status", equalTo(200))
                .body("message", equalTo("Información almacenada exitosamente!"))
                .body("data.description", equalTo("Descuento en shampoo"))
                .body("data.discountPercentage", equalTo(20));
    }


    @Test
    public void givenValidRequest_whenGetOffers_thenCorrectResponse() {
        String productId = "35057b47-aff7-423b-9e84-53022e2bc1b6";
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .get("/smart-consumption/api/v1/offer/" + productId)
                .then()
                .statusCode(200)
                .body("status", equalTo(200))
                .body("message", equalTo("Ofertas consultadas exitosamente!"))
                .body("data[0].description", equalTo("Descuento en shampoo"))
                .body("data[0].period.startDate", equalTo("2023-09-20T14:30:00"))
                .body("data[0].period.endDate", equalTo("2023-09-30T14:30:00"))
                .body("data[0].discountPercentage", equalTo(20))
                .body("data[0].product.name", equalTo("shampoo"));
    }
}
