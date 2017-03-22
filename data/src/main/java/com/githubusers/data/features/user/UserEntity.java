package com.githubusers.data.features.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.DoubleSummaryStatistics;

import lombok.Data;

/**
 * Class that represents a GitHub's user in the domain layer
 */
@Data
public class UserEntity {
  @SerializedName("login")
  @Expose
  private String login;
  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("avatar_url")
  @Expose
  private String avatarUrl;
  @SerializedName("gravatar_id")
  @Expose
  private String gravatarId;
  @SerializedName("url")
  @Expose
  private String url;
  @SerializedName("html_url")
  @Expose
  private String htmlUrl;
  @SerializedName("followers_url")
  @Expose
  private String followersUrl;
  @SerializedName("following_url")
  @Expose
  private String followingUrl;
  @SerializedName("gists_url")
  @Expose
  private String gistsUrl;
  @SerializedName("starred_url")
  @Expose
  private String starredUrl;
  @SerializedName("subscriptions_url")
  @Expose
  private String subscriptionsUrl;
  @SerializedName("organizations_url")
  @Expose
  private String organizationsUrl;
  @SerializedName("repos_url")
  @Expose
  private String reposUrl;
  @SerializedName("events_url")
  @Expose
  private String eventsUrl;
  @SerializedName("received_events_url")
  @Expose
  private String receivedEventsUrl;
  @SerializedName("type")
  @Expose
  private String type;
  @SerializedName("site_admin")
  @Expose
  private Boolean siteAdmin;
  @SerializedName("name")
  @Expose
  private Object name;
  @SerializedName("company")
  @Expose
  private Object company;
  @SerializedName("blog")
  @Expose
  private Object blog;
  @SerializedName("location")
  @Expose
  private Object location;
  @SerializedName("email")
  @Expose
  private Object email;
  @SerializedName("hireable")
  @Expose
  private Object hireable;
  @SerializedName("bio")
  @Expose
  private Object bio;
  @SerializedName("public_repos")
  @Expose
  private Integer publicRepos;
  @SerializedName("public_gists")
  @Expose
  private Integer publicGists;
  @SerializedName("followers")
  @Expose
  private Integer followers;
  @SerializedName("following")
  @Expose
  private Integer following;
  @SerializedName("created_at")
  @Expose
  private String createdAt;
  @SerializedName("updated_at")
  @Expose
  private String updatedAt;

}
