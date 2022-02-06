import com.sun.org.glassfish.gmbal.Description;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojos.CreateUserResponse;
import pojos.UserPojoFull;
import pojos.UserRequest;
import utils.RestWrapper;
import utils.UserGenerator;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RestTest {

    private static RestWrapper api;

    @BeforeAll
    public static void prepareClient() {
        api = RestWrapper.loginAs("eve.holt@reqres.in", "cityslicka");
    }

    private static final RequestSpecification REQ_SPEC =
            new RequestSpecBuilder()
                    .setBaseUri("https://reqres.in/api")
                    .setBasePath("/users")
                    .setContentType(ContentType.JSON)
                    .build();

    @Test
    public void getUsers() {
        given()
                .spec(REQ_SPEC)
                .expect()
                .when().get()
                .then()
                .statusCode(200)
                        .body("data[0].email", equalTo("george.bluth@reqres.in"))
                .and().body("data[5].email", equalTo("tracey.ramos@reqres.in"))
                .and().body("data[5].first_name", equalTo("Tracey"))
                .and().body("data.find{it.email == 'george.bluth@reqres.in'}.first_name", equalTo("George"))
                .and().body("data.find{it.first_name == 'Janet'}.email", equalTo("janet.weaver@reqres.in"));
    }

    @Test
    public void getListOfUsers() {
        List<String> list = given()
                .spec(REQ_SPEC)
                .when().get()
                .then().statusCode(200)
                .extract().jsonPath().getList("data.email");

        Assertions.assertTrue(list.contains("emma.wong@reqres.in"));
    }

    @DisplayName("Get list of users using Pojo")
    @Description("Get list of users and verify user with given email exist")
    @Test
    public void getListOfUsersPojo() {
        assertThat(api.user.getUsers())
                .extracting(UserPojoFull::getEmail)
                .contains("charles.morris@reqres.in");
    }

    @DisplayName("Create user")
    @Description("Check sending request Create User and validate user with name exist in the system")
    @Test
    public void createUser() {
        UserRequest userRequest = UserGenerator.getSimpleUser();
        CreateUserResponse rs = api.user.createUser(userRequest);
        assertThat(rs)
                .isNotNull()
                .extracting(CreateUserResponse::getName)
                .isEqualTo(userRequest.getName());
    }

    public void updateUser() {

    }
}
