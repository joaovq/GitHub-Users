package com.sample.githubusers.domain.features.user;

/**
 * Class that represents a GitHub's user in the domain layer
 */

public class User {
  private String login;
  private Integer publicRepos;
  private Integer followers;
  private Integer following;
  private String  avatarUrl;

  public Integer getFollowers() {
    return followers;
  }

  public void setFollowers(Integer followers) {
    this.followers = followers;
  }

  public Integer getFollowing() {
    return following;
  }

  public void setFollowing(Integer following) {
    this.following = following;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public Integer getRepos() {
    return publicRepos;
  }

  public void setRepos(Integer repos) {
    this.publicRepos = repos;
  }

}
