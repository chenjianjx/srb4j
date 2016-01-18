package ${package}.restclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;





@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-01-08T20:25:00.614+08:00")
public class UpdatePostRequest   {
  
  private String content = null;
  private Long id = null;

  
  /**
   * the post's content
   **/
  
  @ApiModelProperty(required = true, value = "the post's content")
  @JsonProperty("content")
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }

  
  /**
   * the postId.
   **/
  
  @ApiModelProperty(value = "the postId.")
  @JsonProperty("id")
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdatePostRequest updatePostRequest = (UpdatePostRequest) o;
    return Objects.equals(content, updatePostRequest.content) &&
        Objects.equals(id, updatePostRequest.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(content, id);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdatePostRequest {\n");
    
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

