package sitle.nomoreparties.stellarburgers.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import sitle.nomoreparties.stellarburgers.user.UserAssertion;
import sitle.nomoreparties.stellarburgers.user.UserClient;
import sitle.nomoreparties.stellarburgers.user.UserCreate;
import sitle.nomoreparties.stellarburgers.user.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class OrderCreateTest {
    private final UserGenerator userGenerator = new UserGenerator();
    private final UserClient userClient = new UserClient();
    private final UserAssertion userAssertion = new UserAssertion();
    private final OrderClient orderClient = new OrderClient();
    UserCreate uniqueUser = userGenerator.randomDataCourier();
    OrderAssertion orderAssertion = new OrderAssertion();
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

    @DisplayName("Создание заказа зарегестрированным пользователем")
    @Test
    public void checkCreateOrderInLoginUser() {
        List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f");
        OrderCreate orderCreate = new OrderCreate(ingredients);
        ValidatableResponse response = orderClient.createOrders(accessToken, orderCreate);
        orderAssertion.createOrderSuccess(response);
    }

    @DisplayName("Создание заказа незарегестрированным пользователем")
    @Test
    public void checkCreateOrderNoLoginUser() {
        List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f");
        OrderCreate orderCreate = new OrderCreate(ingredients);
        ValidatableResponse response = orderClient.createOrders("1", orderCreate);
        orderAssertion.createOrderSuccess(response);
    }

    @DisplayName("Получениея ошибки при создании заказа c неверным хешами ингредиентов")
    @Test
    public void checkCreateOrderInvalidHashIngredients() {
        List<String> ingredients = List.of("61c0c5a71d1f820dff01bdaaa6d", "61c0c5a71d1f82dfsdf001bdaaa6f");
        OrderCreate orderCreate = new OrderCreate(ingredients);
        ValidatableResponse response = orderClient.createOrders(accessToken, orderCreate);
        orderAssertion.createOrderInvalidHashIngredients(response);
    }

    @DisplayName("Получение ошибки при созданир заказа без ингредиентов")
    @Test
    public void checkCreateOrderNoIngredients() {
        OrderCreate orderCreate = new OrderCreate();
        ValidatableResponse response = orderClient.createOrders(accessToken, orderCreate);
        orderAssertion.createOrderNoIngredients(response);
    }
}
