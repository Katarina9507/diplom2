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

public class OrderListUserTest {
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

    @DisplayName("Получение списка заказов зарегистрированного пользователя")
    @Test
    public void checkGetListOrderInLoginUser() {
        ValidatableResponse response = orderClient.getOrdersListUser(accessToken);
        orderAssertion.getOrderListInLoginUser(response);
    }
    @DisplayName("Проверка на невозможность получения заказов, если пользователь не зерегистрирован")
    @Test
    public void checkGetListOrderIfUserNotLogin() {
        ValidatableResponse response = orderClient.getOrdersListUser("1");
        orderAssertion.getOrderListNoLoginUser(response);
    }
}
