package automation.example.demo.features.user.api;

import static automation.example.demo.config.BaseConfig.getBaseConfig;
import static automation.example.demo.pageobject.PageObject.waitForABit;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import automation.example.demo.models.User;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final String baseApiUrl = getBaseConfig().getEnvironment().getBaseApiUrl();
    private final String apiToken = getBaseConfig().getEnvironment().getApiToken();

    @Step("Retrieve all users")
    public List<User> getUsers() {
        logger.info("Retrieve all users");
        Response response = RestAssured
                .given()
                .baseUri(this.baseApiUrl)
                .log().all()
                .when()
                .get("/public/v2/users");

        if (response.statusCode() != 200) {
            throw new Error("Failed to retrieve all users");
        }

        Type userListType = new TypeToken<ArrayList<User>>() {}.getType();
        List<User> users = new Gson().fromJson(response.prettyPrint(), userListType);
        return users;
    }

    @Step("Retrieve user by userId {userId}")
    public User getUserById(int userId) {
        logger.info("Retrieve user by userId {}", +userId);
        Response response = RestAssured
                .given()
                .baseUri(this.baseApiUrl)
                .header(
                        "Authorization",
                        "Bearer " + this.apiToken)
                .log().all()
                .when()
                .get(String.format("/public/v2/users/%s", userId));

        if (response.statusCode() != 200) {
            throw new Error("Failed to retrieve user " + userId);
        }
        return new Gson().fromJson(response.prettyPrint(), User.class);
    }

    @Step("Create user {user}")
    public User createUser(User user) {
        logger.info("Create user {}", user.getName());
        Response response = RestAssured
                .given()
                .baseUri(this.baseApiUrl)
                .header(
                        "Authorization",
                        "Bearer " + this.apiToken)
                .contentType("application/json")
                .body(new Gson().toJson(user))
                .log().all()
                .when()
                .post("/public/v2/users");

        int retries = 10;
        boolean success = false;
        while (retries > 1 && !success) {
            if (response.statusCode() == 201) {
                success = true;
            }
            waitForABit(1);
            retries--;
        }

        if (!success) {
            throw new Error("Failed to create user " + user.getName());
        }
        return response.jsonPath().getObject("", User.class);
    }

    @Step("Delete user by userId {userId}")
    public void deleteUserById(int userId) {
        logger.info("Delete user {}", userId);
        Response response = RestAssured
                .given()
                .baseUri(this.baseApiUrl)
                .header(
                        "Authorization",
                        "Bearer " + this.apiToken)
                .log().all()
                .when()
                .delete(String.format("/public/v2/users/%s", userId));

        if (response.statusCode() != 204) {
            throw new Error("Failed to delete user " + userId);
        }
    }
}
