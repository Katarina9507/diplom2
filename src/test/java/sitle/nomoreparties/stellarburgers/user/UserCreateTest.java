package sitle.nomoreparties.stellarburgers.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class UserCreateTest {

    private final UserGenerator userGenerator = new UserGenerator();
    private final UserClient userClient = new UserClient();
    private final UserAssertion userAssertion = new UserAssertion();
    UserCreate uniqueUser = userGenerator.randomDataCourier();
    private String accessToken;

    @After
    public void deleteUser() {
        if (accessToken != null) {
            ValidatableResponse response = userClient.delete(accessToken);
            userAssertion.assertDeleteSuccess(response);
        }
    }

    @Test
    @DisplayName("Успешное создание пользователя")
    public void checkUserCreateSusses() {
        ValidatableResponse create = userClient.create(uniqueUser);
        accessToken = userAssertion.assertCreationSuccess(create);
    }

    @Test
    @DisplayName("Невозможное создание дубля пользователя")
    public void checkDoubleCourier() {
        ValidatableResponse create = userClient.create(uniqueUser);
        accessToken = userAssertion.assertCreationSuccess(create);
        ValidatableResponse create2 = userClient.create(uniqueUser);
        userAssertion.assertCreationDoubleUserFailed(create2);
    }

    @Test
    @DisplayName("Невозможное создание пользователя без имени")
    public void checkCreateCourierNoName() {
        uniqueUser.setName(null);
        ValidatableResponse create = userClient.create(uniqueUser);
        userAssertion.assertCreationUserWithoutRequiredField(create);
    }

    @Test
    @DisplayName("Невозможное создание пользователя без email")
    public void checkCreateUserNoEmail() {
        uniqueUser.setEmail(null);
        ValidatableResponse create = userClient.create(uniqueUser);
        userAssertion.assertCreationUserWithoutRequiredField(create);
    }

    @Test
    @DisplayName("Невозможное создание пользователя без пароля")
    public void checkCreateUserNoPassword() {
        uniqueUser.setPassword(null);
        ValidatableResponse create = userClient.create(uniqueUser);
        userAssertion.assertCreationUserWithoutRequiredField(create);
    }
}
