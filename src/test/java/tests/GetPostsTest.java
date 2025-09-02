package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.PostsResponse;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import utils.Requests;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertTrue;

public class GetPostsTest extends BaseTest {

    SoftAssertions softAssertions = new SoftAssertions();

    @Test
    @DisplayName("Запрос списка постов")
    @Description("Отправка GET запроса на эндпоинт /posts, получение списка всех постов")
    public void getAllPostsSuccessTest() {
        PostsResponse[] getPostsResponse = Requests.getPosts()
                .then()
                .log().ifValidationFails()
                .statusCode(SC_OK)
                .extract()
                .as(PostsResponse[].class);
        assertTrue(getPostsResponse.length > 0);
    }

    @Test
    @DisplayName("Запрос поста по id")
    @Description("Отправка GET запроса на эндпоинт /posts/{postId}")
    public void getPostByIdSuccessTest() {
        int orderNumber = 0;
        PostsResponse[] getPostsResponse = Requests.getPosts()
                .then()
                .statusCode(SC_OK)
                .extract()
                .as(PostsResponse[].class);
        int postId = getPostsResponse[orderNumber].getId();
        int userId = getPostsResponse[orderNumber].getUserId();
        String title = getPostsResponse[orderNumber].getTitle();
        String body = getPostsResponse[orderNumber].getBody();

        PostsResponse getPostResponse = Requests.getPosts(postId)
                .then()
                .log().ifValidationFails()
                .statusCode(SC_OK)
                .extract()
                .as(PostsResponse.class);

        softAssertions.assertThat(title)
                .withFailMessage("Заголовок поста title '%s' не соответствует ожидаемому '%s'", title, getPostResponse.getTitle())
                .isEqualTo(getPostResponse.getTitle());
        softAssertions.assertThat(body)
                .withFailMessage("Тело поста body '%s' не соответствует ожидаемому '%s'", body, getPostResponse.getBody())
                .isEqualTo(getPostResponse.getBody());
        softAssertions.assertThat(userId)
                .withFailMessage("Значение userId '%d' не соответствует ожидаемому '%d'", userId, getPostResponse.getUserId())
                .isEqualTo(getPostResponse.getUserId());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Запрос поста с несуществующим id")
    @Description("Отправка GET запроса на эндпоинт /posts/{postId} с несуществующим postId")
    public void getPostByIncorrectIdTest() {
        Requests.getPosts(0)
                .then()
                .statusCode(SC_NOT_FOUND);
    }
}
