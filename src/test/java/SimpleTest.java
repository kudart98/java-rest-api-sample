import io.restassured.response.*;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SimpleTest {
    @Test
    void successStatusTest() {
        given()
                .when()
                    .get("https://api.plagscan.com/v3/ping")
                .then()
                    .statusCode(200);
    }

    @Test
    void statusResponseTest() {
        Response response = given()
                .when()
                    .get("http://kn-ktapp.herokuapp.com/apitest/accounts/12345678")
                .then()
                    .statusCode(200)
                    .extract().response();
        System.out.println(response.asString());
    }

    @Test
    void statusResponseGoodBody() {
        // need this import static org.hamcrest.CoreMatchers.is;
        given()
                .when()
                    .get("http://kn-ktapp.herokuapp.com/apitest/accounts/12345678")
                .then()
                    .log().body()
                    .statusCode(200)
                    .body("isSalary", is(false));
    }

    @Test
    void successStatusReadyWithAssertThat1Test() {
        ExtractableResponse<Response> result = given()
                .auth().basic("user1", "1234")
                .when()
                    .get("https://selenoid.autotests.cloud:4444/wd/hub/status")
                .then()
                    .log().body()
                    .statusCode(200)
                    .extract();

        assertThat(result.path("value.ready"), is(true));
    }
}

