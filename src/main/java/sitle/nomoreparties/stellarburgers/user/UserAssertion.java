package sitle.nomoreparties.stellarburgers.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;

public class UserAssertion {

    @Step("Успешный ответ на запрос по созданию пользователя")
    public String assertCreationSuccess(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(200)
                .body("success", is(true))
                .extract().path("accessToken");
    }

    @Step("Успешный ответ на запрос по удалению пользователя")
    public void assertDeleteSuccess(ValidatableResponse response) {
        response.assertThat()
                .statusCode(202)
                .body("message", equalTo("User successfully removed"));
    }

    @Step("Ответ с ошибкой на запрос по созданию пользователя с существующим e-mail")
    public void assertCreationDoubleUserFailed(ValidatableResponse response) {
        response.assertThat()
                .statusCode(403)
                .body("message", equalTo("User already exists"));
    }

    @Step("Ответ с ошибкой на запрос по созданию пользователя c незаполненным обязательным полем")
    public void assertCreationUserWithoutRequiredField(ValidatableResponse response) {
        response.assertThat()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Step("Успешный ответ на запрос логин курьера в системе")
    public void loginInSuccess(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("accessToken", notNullValue());
    }

    @Step("Ответ с ошибкой при указании неверного логина или пароля")
    public void invalidLoginError(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("message", equalTo("email or password are incorrect"));
    }

    @Step("Успешный ответ на запрос изменения данных зарегестрированным пользователем")
    public void updateDateUserInSuccess(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true));
    }
    @Step("Успешный ответ на запрос изменения данных зарегестрированным пользователем")
    public void updateDateUserNotInFailed(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("message", equalTo("You should be authorised"));
    }
}
