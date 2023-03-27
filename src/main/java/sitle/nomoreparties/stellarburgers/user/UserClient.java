package sitle.nomoreparties.stellarburgers.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class UserClient extends sitle.nomoreparties.stellarburgers.Client {
    protected final String ANCHOR = "/auth";

    @Step("Запрос на создание пользователя")
    public ValidatableResponse create(UserCreate userCreate) {
        return spec()
                .body(userCreate)
                .when()
                .post(ANCHOR + "/register")
                .then().log().all();
    }

    @Step("Запрос на удаление пользователя")
    public ValidatableResponse delete(String token) {
        return spec()
                .header("Authorization", token)
                .when()
                .delete(ANCHOR + "/user")
                .then().log().all();
    }

    @Step("Запрос на изменение данных пользователя")
    public ValidatableResponse update(String token, UserCreate userCreate) {
        return spec()
                .header("Authorization", token)
                .body(userCreate)
                .when()
                .patch(ANCHOR + "/user")
                .then().log().all();
    }

    @Step("Запрос на вход под логином курьера")
    public ValidatableResponse login(UserLogin userLogin) {
        return spec()
                .body(userLogin)
                .when()
                .post(ANCHOR + "/login")
                .then().log().all();
    }
}
