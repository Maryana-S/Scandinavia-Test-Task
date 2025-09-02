package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import models.PostsRequest;
import models.PostsResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.Requests;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;

public class PutPostsTest extends BaseTest {

    SoftAssertions softAssertions = new SoftAssertions();

    String title;
    String body;
    Integer userId;
    Integer postId;

    String newTitle;
    String newBody;
    Integer newUserId;

    PostsRequest putRequest;

    @Before
    @Step("Инициализация параметров")
    public void init() {
        title = RandomStringUtils.randomAlphabetic(10);
        body = RandomStringUtils.randomAlphanumeric(25);
        userId = RandomUtils.nextInt(1, 10);

        postId = Requests.postCreate(new PostsRequest(title, body, userId)).then().statusCode(201).extract().as(PostsResponse.class).getId();

        newTitle = RandomStringUtils.randomAlphabetic(10);
        newBody = RandomStringUtils.randomAlphanumeric(25);
        newUserId = RandomUtils.nextInt(1, 10);

        putRequest = new PostsRequest(newTitle, newBody, newUserId);
    }

    @Test
    @DisplayName("Успешное обновление существующего поста при отправке корректных параметров в теле запроса и существующего id")
    @Description("Отправка PUT запроса на эндпоинт /posts/{id}")
    public void putPostsSuccessTest() {
        PostsResponse putResponse = Requests.putPosts(putRequest, 1) // Передаю значение postId = 1 для успешного прохлждения теста, так как в действительности новые записи не создаются
                .then()
                .log().ifValidationFails()
                .statusCode(SC_OK)
                .extract()
                .as(PostsResponse.class);

        softAssertions.assertThat(newTitle)
                .withFailMessage("Заголовок поста title '%s' не соответствует ожидаемому '%s'", newTitle, putResponse.getTitle())
                .isEqualTo(putResponse.getTitle());
        softAssertions.assertThat(newBody)
                .withFailMessage("Тело поста body '%s' не соответствует ожидаемому '%s'", newBody, putResponse.getBody())
                .isEqualTo(putResponse.getBody());
        softAssertions.assertThat(newUserId)
                .withFailMessage("Значение userId '%d' не соответствует ожидаемому '%d'", newUserId, putResponse.getUserId())
                .isEqualTo(putResponse.getUserId());
        softAssertions.assertAll();
    }


    @Test
    @DisplayName("Отображение ошибки 404 NOT FOUND при обновлении существующего поста при отправке корректных параметров в теле запроса и несуществующего id")
    @Description("Отправка PUT запроса на эндпоинт /posts/{id}")
    public void putPostsIncorrectIdTest() {
        Requests.putPosts(putRequest, 0)
                .then()
                .log().ifValidationFails()
                .statusCode(SC_NOT_FOUND);
    }

    @After
    @Step("Удаление созданных постов")
    public void deletePost() {
        Requests.deletePosts(postId);
    }
}
