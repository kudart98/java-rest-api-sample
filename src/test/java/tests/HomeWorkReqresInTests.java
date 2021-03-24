package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.*;
import static io.restassured.RestAssured.given;
import static utils.FileUtils.readStringFromFile;


public class HomeWorkReqresInTests {

    @BeforeAll
    static void setup(){
        RestAssured.filters(new AllureRestAssured());
        RestAssured.baseURI = "https://reqres.in/";
    }


    @Test
    void listUsersTest() {
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("api/users")
        .then()
                .log().body()
                .statusCode(200)
                .body("support.text",
                        is("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }

    @Test
    void createUserTest() {
        String body = readStringFromFile("./src/test/resources/createUserBody.txt");
        given()
                .contentType(ContentType.JSON)
                .body(body)
        .when()
                .post("api/users")
        .then()
                .log().body()
                .statusCode(201)
                .body("name", is("Rafshan"))
                .body("job", is("Dvornik"));
    }

    @Test
    void registerSuccessful() {
        String body = readStringFromFile("./src/test/resources/loginCredential.txt");
        given()
                .contentType(ContentType.JSON)
                .body(body)
        .when()
                .post("api/register")
        .then()
                .log().body()
                .statusCode(200)
                .body("id", is(notNullValue()))
                .body("token", is(notNullValue()));
    }

    @Test
    void registerUnsuccessful() {
        String body = readStringFromFile("./src/test/resources/badCredential.txt");
        given()
                .contentType(ContentType.JSON)
                .body(body)
        .when()
                .post("api/register")
        .then()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void loginSuccessful() {
        String body = readStringFromFile("./src/test/resources/loginCredential.txt");
        given()
                .contentType(ContentType.JSON)
                .body(body)
        .when()
                .post("api/login")
        .then()
                .log().body()
                .statusCode(200)
                .body("token", is(notNullValue()));
    }

}

