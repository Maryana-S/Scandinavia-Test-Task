package models;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class PostsResponse {

	@SerializedName("id")
	private Integer id;

	@SerializedName("title")
	private String title;

	@SerializedName("body")
	private String body;

	@SerializedName("userId")
	private Integer userId;

}