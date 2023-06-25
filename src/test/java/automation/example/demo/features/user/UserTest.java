package automation.example.demo.features.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import automation.example.demo.features.user.api.UserController;
import automation.example.demo.models.User;
import automation.example.demo.utils.DataLoaderUtils;
import automation.example.demo.utils.RandomUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Tags({
        @Tag("Regression"),
        @Tag("User"),
})
public class UserTest {
    UserController userController;
    User user;

    @BeforeEach
    public void beforeTest() {
        userController = new UserController();
        user = DataLoaderUtils.loadTestData("user", User.class);
    }

    @Severity(SeverityLevel.NORMAL)
    @Test
    @Description("Verify user is created successfully")
    @Story("User")
    public void verifyUserIsCreatedSuccessfully() {
        String randomNumber = RandomUtils.generateRandomNumber(10);

        user.setEmail(randomNumber + user.getEmail());
        User createUserResponse = userController.createUser(user);

        assertThat(createUserResponse.getName(), equalTo(user.getName()));
        assertThat(createUserResponse.getGender(), equalTo(user.getGender()));

        User retrieveUserResponse = userController.getUserById(createUserResponse.getId());
        assertThat(retrieveUserResponse.getName(), is(retrieveUserResponse.getName()));
        assertThat(retrieveUserResponse.getStatus(), is(retrieveUserResponse.getStatus()));

        List<User> users = userController.getUsers();
        assertThat(users.get(0).getName(), notNullValue());

        userController.deleteUserById(createUserResponse.getId());
    }
}