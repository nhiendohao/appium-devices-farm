package automation.example.demo.features.user.api;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import automation.example.demo.constants.Constants;
import automation.example.demo.models.User;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public List<User> getUsers() {
        logger.info("Retrieve all users");
        Response response = RestAssured.given()
                                       .baseUri(Constants.API_BASE_URL)
                                       .when().log().all()
                                       .get("/public/v2/users");
        if (response.statusCode() != 200) {
            throw new Error("Failed to retrieve all users");
        }

        Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
        List<User> users = new Gson().fromJson(response.prettyPrint(), userListType);
        return users;
    }

    public User getUserById(int userId) {
        logger.info("Retrieve user by userId " + userId);
        Response response = RestAssured.given()
                                       .baseUri(Constants.API_BASE_URL)
                                       .header("Authorization", "Bearer " + Constants.API_TOKEN)
                                       .when().log().all()
                                       .get(String.format("/public/v2/users/%s", userId));

        if (response.statusCode() != 200) {
            throw new Error("Failed to retrieve user " + userId);
        }
        return response.jsonPath().getObject("", User.class);
    }


    @Step("Create user")
    public User createUser(User user) {
        logger.info("Create user" + user.getName());
        Response response = RestAssured
                .given()
                .baseUri(Constants.API_BASE_URL)
                .header("Authorization", "Bearer " + Constants.API_TOKEN)
                .contentType("application/json")
                .when().log().all()
                .body(new Gson().toJson(user))
                .post("/public/v2/users");

        if (response.statusCode() != 201) {
            throw new Error("Failed to create user " + user.getName());
        }
        return response.jsonPath().getObject("", User.class);
    }


    public void deleteUserById(int userId) {
        Response response = RestAssured
                .given()
                .baseUri(Constants.API_BASE_URL)
                .header("Authorization", "Bearer " + Constants.API_TOKEN)
                .when().log().all()
                .delete(String.format("/public/v2/users/%s", userId));

        if (response.statusCode() != 204) {
            throw new Error("Failed to delete user " + userId);
        }
    }
}
