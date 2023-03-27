package sitle.nomoreparties.stellarburgers.user;

import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerator {
    public UserCreate randomDataCourier() {
        return new UserCreate(RandomStringUtils.randomAlphabetic(10) + "@mail.ru", "Qwerty1","Олег");

    }
}
