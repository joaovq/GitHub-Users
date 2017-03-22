package com.githubusers.domain.features.user;

/**
 * Class that represents a GitHub's user in the domain layer
 */

public class User {
  private String login;
  private Integer publicRepos;

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
