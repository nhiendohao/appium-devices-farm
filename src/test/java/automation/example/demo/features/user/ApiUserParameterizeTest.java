package automation.example.demo.features.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import automation.example.demo.features.user.api.UserController;
import automation.example.demo.models.User;
import helpers.DataLoaderHelpers;
import helpers.StringHelpers;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Link;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;

@Tags({
        @Tag("Regression"),
        @Tag("ApiUser"),
})
@Feature("User")
public class ApiUserParameterizeTest {
    UserController userController;

    static Stream<User> dataProvider() {
        User[] users = DataLoaderHelpers.loadDataArrayProvider(
                User.class, "user.json", "user2.json");
        return Stream.of(users);
    }

    @BeforeEach
    public void beforeTest() {
        userController = new UserController();
    }

    // Add displayName into ParameterizeTest in order to handle upload result
    @ParameterizedTest(name = "TestCaseId: C53986860, C53986861 create user{0}")
    @Severity(SeverityLevel.NORMAL)
    @Story("Create user")
    @Issue("TC10022")
    @TmsLink("TC10022")
    @Link(name = "GoogleLink", url = "https://www.googgle.com")
    @Description("Verify user is created successfully")
    @MethodSource("dataProvider")
    public void verifyUserIsCreatedSuccessfully(User user) {
        String randomNumber = StringHelpers.generateRandomNumber(10);

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
