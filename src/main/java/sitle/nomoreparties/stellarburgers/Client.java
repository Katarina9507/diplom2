package sitle.nomoreparties.stellarburgers;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Client {
    protected final String URL = "https://stellarburgers.nomoreparties.site/";
    protected final String PREFIX = "/api";

    protected RequestSpecification spec() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(URL)
                .basePath(PREFIX);
    }
}