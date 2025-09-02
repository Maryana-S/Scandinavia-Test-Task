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

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;

public class CreatePostsTest extends BaseTest {

    SoftAssertions softAssertions = new SoftAssertions();

    String title;
    String body;
    Integer userId;
    PostsResponse postsResponse;


    @Before
    @Step("Инициализация параметров: title, body, userId")
    public void initParams() {
        title = RandomStringUtils.randomAlphabetic(10);
        body = RandomStringUtils.randomAlphanumeric(25);
        userId = RandomUtils.nextInt(1, 10);
    }

    @Test
    @DisplayName("Успешное создание поста")
    @Description("Отправка POST запроса на эндпоинт /posts c корректными значениями title, body, userId")
    public void createPostsSuccessTest() {
        postsResponse = Requests.postCreate(new PostsRequest(title, body, userId))
                .then()
                .log().ifValidationFails()
                .statusCode(SC_CREATED)
                .extract()
                .as(PostsResponse.class);

        softAssertions.assertThat(title)
                .withFailMessage("Заголовок поста title '%s' не соответствует ожидаемому '%s'", title, postsResponse.getTitle())
                .isEqualTo(postsResponse.getTitle());
        softAssertions.assertThat(body)
                .withFailMessage("Тело поста body '%s' не соответствует ожидаемому '%s'", body, postsResponse.getBody())
                .isEqualTo(postsResponse.getBody());
        softAssertions.assertThat(userId)
                .withFailMessage("Значение userId '%d' не соответствует ожидаемому '%d'", userId, postsResponse.getUserId())
                .isEqualTo(postsResponse.getUserId());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Успешное создание поста без заголовка title")
    @Description("Отправка POST запроса на эндпоинт /posts c пустым значением в поле title")
    public void createPostsEmptyTitleTest() {
        title = null;
        postsResponse = Requests.postCreate(new PostsRequest(title, body, userId))
                .then()
                .log().ifValidationFails()
                .statusCode(SC_CREATED)
                .extract()
                .as(PostsResponse.class);

        softAssertions.assertThat(title)
                .withFailMessage("Заголовок поста title '%s' не соответствует ожидаемому '%s'", title, postsResponse.getTitle())
                .isEqualTo(postsResponse.getTitle());
        softAssertions.assertThat(body)
                .withFailMessage("Тело поста body '%s' не соответствует ожидаемому '%s'", body, postsResponse.getBody())
                .isEqualTo(postsResponse.getBody());
        softAssertions.assertThat(userId)
                .withFailMessage("Значение userId '%d' не соответствует ожидаемому '%d'", userId, postsResponse.getUserId())
                .isEqualTo(postsResponse.getUserId());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Успешное создание поста без тела body")
    @Description("Отправка POST запроса на эндпоинт /posts c пустым значением в поле body")
    public void createPostsEmptyBodyTest() {
        body = null;
        postsResponse = Requests.postCreate(new PostsRequest(title, body, userId))
                .then()
                .log().ifValidationFails()
                .statusCode(SC_CREATED)
                .extract()
                .as(PostsResponse.class);

        softAssertions.assertThat(title)
                .withFailMessage("Заголовок поста title '%s' не соответствует ожидаемому '%s'", title, postsResponse.getTitle())
                .isEqualTo(postsResponse.getTitle());
        softAssertions.assertThat(body)
                .withFailMessage("Тело поста body '%s' не соответствует ожидаемому '%s'", body, postsResponse.getBody())
                .isEqualTo(postsResponse.getBody());
        softAssertions.assertThat(userId)
                .withFailMessage("Значение userId '%d' не соответствует ожидаемому '%d'", userId, postsResponse.getUserId())
                .isEqualTo(postsResponse.getUserId());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Успешное создание поста без id пользователя userId")
    @Description("Отправка POST запроса на эндпоинт /posts c пустым значением в поле userId")
    public void createPostsEmptyUserIdTest() {
        userId = null;
        postsResponse = Requests.postCreate(new PostsRequest(title, body, userId))
                .then()
                .log().ifValidationFails()
                .statusCode(SC_CREATED)
                .extract()
                .as(PostsResponse.class);

        softAssertions.assertThat(title)
                .withFailMessage("Заголовок поста title '%s' не соответствует ожидаемому '%s'", title, postsResponse.getTitle())
                .isEqualTo(postsResponse.getTitle());
        softAssertions.assertThat(body)
                .withFailMessage("Тело поста body '%s' не соответствует ожидаемому '%s'", body, postsResponse.getBody())
                .isEqualTo(postsResponse.getBody());
        softAssertions.assertThat(userId)
                .withFailMessage("Значение userId '%d' не соответствует ожидаемому '%d'", userId, postsResponse.getUserId())
                .isEqualTo(postsResponse.getUserId());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Создание поста с несуществующим значением userId")
    @Description("Отправка POST запроса на эндпоинт /posts c несуществующим значением userId")
    public void createPostsIncorrectUserIdTest() {
        postsResponse = Requests.postCreate(new PostsRequest(title, body, 0))
                .then()
                .log().ifValidationFails()
                .statusCode(SC_NOT_FOUND)
                .extract()
                .as(PostsResponse.class);
    }

    @After
    @Step("Удаление созданных записей")
    public void deletePosts() {
        if (postsResponse != null) {
            int postId = postsResponse.getId();
            Requests.deletePosts(postId);
        }
    }

}
