package ${groupId}.${rootArtifactId}.restclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.Objects;



/**
 * Exemplary Only
 **/

@ApiModel(description = "Exemplary Only")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-01-08T20:25:00.614+08:00")
public class Post   {
  
  private Long id = null;
  private Date createdAt = null;
  private Date updatedAt = null;
  private Long userId = null;
  private String content = null;
  private Boolean canDelete = false;
  private Boolean canUpdate = false;

  
  /**
   * the record's id in backend's db
   **/
  
  @ApiModelProperty(required = true, value = "the record's id in backend's db")
  @JsonProperty("id")
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  
  /**
   * create time. The system miliseconds since Jan.1 1970 GMT
   **/
  
  @ApiModelProperty(required = true, value = "create time. The system miliseconds since Jan.1 1970 GMT")
  @JsonProperty("createdAt")
  public Date getCreatedAt() {
    return createdAt;
  }
  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  
  /**
   * last update time. The system miliseconds since Jan.1 1970 GMT
   **/
  
  @ApiModelProperty(required = true, value = "last update time. The system miliseconds since Jan.1 1970 GMT")
  @JsonProperty("updatedAt")
  public Date getUpdatedAt() {
    return updatedAt;
  }
  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  
  /**
   * the author's userId
   **/
  
  @ApiModelProperty(required = true, value = "the author's userId")
  @JsonProperty("userId")
  public Long getUserId() {
    return userId;
  }
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  
  /**
   * the content of this post
   **/
  
  @ApiModelProperty(required = true, value = "the content of this post")
  @JsonProperty("content")
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }

  
  /**
   * can this post be deleted by current user?
   **/
  
  @ApiModelProperty(value = "can this post be deleted by current user?")
  @JsonProperty("canDelete")
  public Boolean getCanDelete() {
    return canDelete;
  }
  public void setCanDelete(Boolean canDelete) {
    this.canDelete = canDelete;
  }

  
  /**
   * can this post be updated by current user?
   **/
  
  @ApiModelProperty(value = "can this post be updated by current user?")
  @JsonProperty("canUpdate")
  public Boolean getCanUpdate() {
    return canUpdate;
  }
  public void setCanUpdate(Boolean canUpdate) {
    this.canUpdate = canUpdate;
  }

  

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Post post = (Post) o;
    return Objects.equals(id, post.id) &&
        Objects.equals(createdAt, post.createdAt) &&
        Objects.equals(updatedAt, post.updatedAt) &&
        Objects.equals(userId, post.userId) &&
        Objects.equals(content, post.content) &&
        Objects.equals(canDelete, post.canDelete) &&
        Objects.equals(canUpdate, post.canUpdate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, createdAt, updatedAt, userId, content, canDelete, canUpdate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Post {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    canDelete: ").append(toIndentedString(canDelete)).append("\n");
    sb.append("    canUpdate: ").append(toIndentedString(canUpdate)).append("\n");
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

