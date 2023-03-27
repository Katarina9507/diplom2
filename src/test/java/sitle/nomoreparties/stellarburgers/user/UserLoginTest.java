package sitle.nomoreparties.stellarburgers.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserLoginTest {
    private final UserGenerator userGenerator = new UserGenerator();
    private final UserClient userClient = new UserClient();
    private final UserAssertion userAssertion = new UserAssertion();
    UserCreate uniqueUser = userGenerator.randomDataCourier();
    private String accessToken;

    @Before
    public void createUser() {
        ValidatableResponse create = userClient.create(uniqueUser);
        accessToken = userAssertion.assertCreationSuccess(create);
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            ValidatableResponse response = userClient.delete(accessToken);
            userAssertion.assertDeleteSuccess(response);
        }
    }

    @Test
    @DisplayName("Успешный входа под существующими логином и паролем пользователя")
    public void checkLoginSusses() {
        UserLogin userLogin = UserLogin.from(uniqueUser);
        ValidatableResponse login = userClient.login(userLogin);
        userAssertion.loginInSuccess(login);
    }

    @Test
    @DisplayName("Ошибка при входе с неверным email")
    public void checkEmailDoesNotMatch() {
        UserLogin userLogin = UserLogin.from(uniqueUser);
        userLogin.setEmail(userLogin.getEmail() + "sss");
        ValidatableResponse login = userClient.login(userLogin);
        userAssertion.invalidLoginError(login);
    }

    @Test
    @DisplayName("Ошибка при входе с неверным паролем")
    public void checkPasswordDoesNotMatch() {
        UserLogin userLogin = UserLogin.from(uniqueUser);
        userLogin.setPassword(userLogin.getPassword() + "sss");
        ValidatableResponse login = userClient.login(userLogin);
        userAssertion.invalidLoginError(login);
    }
}
