package sitle.nomoreparties.stellarburgers.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class OrderClient extends sitle.nomoreparties.stellarburgers.Client {

    protected final String ROOT = "/orders";

    @Step("Отправить запрос на создание заказа пользователем")
    public ValidatableResponse createOrders(String token, OrderCreate orderCreate) {
        return spec()
                .header("Authorization", token)
                .body(orderCreate)
                .when()
                .post(ROOT)
                .then().log().all();
    }
    @Step("Отправить запрос на получение списка заказов")
    public ValidatableResponse getOrdersListUser(String token) {
        return spec()
                .header("Authorization", token)
                .when()
                .get(ROOT)
                .then().log().all();
    }
}