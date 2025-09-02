package utils;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.PostsRequest;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class Requests {

    @Step("Отправка POST запроса на эндпоинт /posts. Создание поста")
    public static Response postCreate(PostsRequest postsRequest) {
        return given()
                .header("Content-type", "application/json")
                .body(postsRequest)
                .when()
                .post("/posts");
    }

    @Step("Отправка GET запроса на эндпоинт /posts. Получение списка постов")
    public static Response getPosts() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .log().ifValidationFails()
                .get("/posts");
    }

    @Step("Отправка GET запроса на эндпоинт /posts. Получение списка постов")
    public static Response getPosts(Integer postId) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .log().ifValidationFails()
                .pathParam("postId", postId)
                .get("/posts/{postId}");
    }

    @Step("Отправка PUT запроса на эндпоинт /posts. Обновление поста")
    public static Response putPosts(PostsRequest postsRequest, Integer postId) {
        return given()
                .header("Content-type", "application/json")
                .body(postsRequest)
                .when()
                .log().ifValidationFails()
                .pathParam("postId", postId)
                .put("/posts/{postId}");
    }

    @Step("Отправка PATCH запроса на эндпоинт /posts. Частичное обновление поста")
    public static Response patchPosts(Map<String, Object> patchData, Integer postId) {
        return given()
                .header("Content-type", "application/json")
                .body(patchData)
                .when()
                .log().ifValidationFails()
                .pathParam("postId", postId)
                .patch("/posts/{postId}");
    }

    @Step("Отправка DELETE запроса на эндпоинт /posts. Удаление поста")
    public static Response deletePosts(Integer postId) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .log().ifValidationFails()
                .pathParam("postId", postId)
                .delete("/posts/{postId}");
    }
}
