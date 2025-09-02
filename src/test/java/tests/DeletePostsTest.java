package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import models.PostsResponse;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import utils.Requests;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;

public class DeletePostsTest extends BaseTest {
    Integer postId;

    @Before
    @Step("Инициализация пааметра postId")
    public void init() {
        PostsResponse[] getPostsResponse = Requests.getPosts()
                .then()
                .statusCode(SC_OK)
                .extract()
                .as(PostsResponse[].class);
        postId = getPostsResponse[RandomUtils.nextInt(0, getPostsResponse.length - 1)].getId();
    }


    @Test
    @DisplayName("Удаление поста по id")
    @Description("Отправка DELETE запроса на эндпоинт /posts c существующим значением id")
    public void deletePostByIdSuccessTest() {
        Requests.deletePosts(postId)
                .then()
                .log().ifValidationFails()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Удаление поста с несуществующим id")
    @Description("Отправка DELETE запроса на эндпоинт /posts c несуществующим значением id")
    public void deletePostByIdNotFoundTest() {
        Requests.deletePosts(0)
                .then()
                .log().ifValidationFails()
                .statusCode(SC_NOT_FOUND);
    }

}
