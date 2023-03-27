package sitle.nomoreparties.stellarburgers.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;

public class OrderAssertion {
    @Step("Получение успешного ответа на запрос создания заказа")
    public void createOrderSuccess(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true));
    }

    @Step("Получение ответа на запрос создания заказа без ингредиентов")
    public void createOrderNoIngredients(ValidatableResponse response) {
        response.assertThat()
                .statusCode(400)
                .body("message", equalTo("Ingredient ids must be provided"));
    }
    @Step("Получение ответа на запрос создания заказа с неверным хешем ингредиентов")
    public void createOrderInvalidHashIngredients(ValidatableResponse response) {
        response.assertThat()
                .statusCode(500);
    }

    @Step("Получение успешного ответа на запрос по просмотру заказов зарегистрированным пользователем")
    public void getOrderListInLoginUser(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true));
    }
    @Step("Получение ответа на запрос по просмотру заказов незарегистрированным пользователем")
    public void getOrderListNoLoginUser(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("message", equalTo("You should be authorised"));
    }
}