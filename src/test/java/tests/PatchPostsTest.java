package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import models.PostsRequest;
import models.PostsResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.Requests;

import java.util.HashMap;
import java.util.Map;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

public class PatchPostsTest extends BaseTest {

    String newValue;
    Integer newId;


    String title;
    String body;
    Integer userId;
    Integer postId;

    @Before
    @Step("Инициализация тестовых данных")
    public void init() {
        title = RandomStringUtils.randomAlphabetic(10);
        body = RandomStringUtils.randomAlphanumeric(25);
        userId = RandomUtils.nextInt(1, 10);

        postId = Requests.postCreate(new PostsRequest(title, body, userId)).as(PostsResponse.class).getId();

        newValue = RandomStringUtils.randomAlphabetic(20);
        newId = RandomUtils.nextInt(1, 50);
    }

    @Test
    @DisplayName("Успешное обновление значения поля title")
    @Description("Отправка PATCH запроса на эндпоинт /posts/{id}, обновление значения поля title")
    public void patchTitleSuccessTest() {
        Map<String, Object> newTitle = new HashMap<>();
        newTitle.put("title", newValue);
        PostsResponse patchPost = Requests.patchPosts(newTitle, postId)
                .then()
                .log().ifValidationFails()
                .statusCode(SC_OK)
                .extract()
                .as(PostsResponse.class);
        assertEquals(newValue, patchPost.getTitle());
    }

    @Test
    @DisplayName("Успешное обновление значения поля body")
    @Description("Отправка PATCH запроса на эндпоинт /posts/{id}, обновление значения поля body")
    public void patchBodySuccessTest() {
        Map<String, Object> newBody = new HashMap<>();
        newBody.put("body", newValue);
        PostsResponse patchPost = Requests.patchPosts(newBody, postId)
                .then()
                .log().ifValidationFails()
                .statusCode(SC_OK)
                .extract()
                .as(PostsResponse.class);
        assertEquals(newValue, patchPost.getBody());
    }

    @Test
    @DisplayName("Успешное обновление значения поля userId")
    @Description("Отправка PATCH запроса на эндпоинт /posts/{id}, обновление значения поля userId")
    public void patchUserIdSuccessTest() {
        Map<String, Object> newUserId = new HashMap<>();
        newUserId.put("userId", newId);
        PostsResponse patchPost = Requests.patchPosts(newUserId, postId)
                .then()
                .log().ifValidationFails()
                .statusCode(SC_OK)
                .extract()
                .as(PostsResponse.class);
        assertEquals(newId, patchPost.getUserId());
    }

    @Test
    @DisplayName("Отображение ошибки 400 BAD REQUEST при обновлении значения поля id")
    @Description("Отправка PATCH запроса на эндпоинт /posts/{id}, обновление значения поля id")
    public void patchIdSuccessTest() {
        Map<String, Object> newPostId = new HashMap<>();
        newPostId.put("id", newId);
        PostsResponse patchPost = Requests.patchPosts(newPostId, postId)
                .then()
                .log().ifValidationFails()
                .statusCode(SC_BAD_REQUEST)
                .extract()
                .as(PostsResponse.class);
    }

    @After
    @Step("Удаление созданных записей")
    public void deletePost() {
        Requests.deletePosts(postId);
    }

}
