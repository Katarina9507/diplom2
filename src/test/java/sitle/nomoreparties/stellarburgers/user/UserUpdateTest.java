package sitle.nomoreparties.stellarburgers.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserUpdateTest {
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
    @DisplayName("Проверка на возможность изменения email зарегестрированным пользователем")
    public void checkUpdateUserEmail() {
        uniqueUser.setEmail(uniqueUser.getEmail() + "1");
        ValidatableResponse response = userClient.update(accessToken, uniqueUser);
        userAssertion.updateDateUserInSuccess(response);
    }

    @Test
    @DisplayName("Проверка возможности изменения пароля зарегестрированным пользователем")
    public void checkUpdateUserPassword() {
        uniqueUser.setPassword(uniqueUser.getPassword() + "1");
        ValidatableResponse response = userClient.update(accessToken, uniqueUser);
        userAssertion.updateDateUserInSuccess(response);
    }

    @Test
    @DisplayName("Проверка на возможность изменения имени зарегестрированным пользователем")
    public void checkUpdateUserName() {
        uniqueUser.setName(uniqueUser.getName() + "1");
        ValidatableResponse response = userClient.update(accessToken, uniqueUser);
        userAssertion.updateDateUserInSuccess(response);
    }

    @Test
    @DisplayName("Проверка на возможность изменения имени незарегестрированным пользователем")
    public void checkUpdateUserNameNoLogin() {
        uniqueUser.setName(uniqueUser.getName() + "1");
        ValidatableResponse response = userClient.update("1", uniqueUser);
        userAssertion.updateDateUserNotInFailed(response);
    }

    @Test
    @DisplayName("Проверка возможности изменения пароля зарегестрированным пользователем")
    public void checkUpdateUserNoPassword() {
        uniqueUser.setPassword(uniqueUser.getPassword() + "1");
        ValidatableResponse response = userClient.update("1", uniqueUser);
        userAssertion.updateDateUserNotInFailed(response);
    }

    @Test
    @DisplayName("Проверка возможности изменения email зарегестрированным пользователем")
    public void checkUpdateUserNoEmail() {
        uniqueUser.setEmail(uniqueUser.getEmail() + "1");
        ValidatableResponse response = userClient.update("1", uniqueUser);
        userAssertion.updateDateUserNotInFailed(response);
    }
}
