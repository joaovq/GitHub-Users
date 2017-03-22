package com.githubusers.presentation.features.users;

import lombok.Data;

/**
 * Class that represents a user in the presentation layer.
 */
@Data
public class UserModel {
  private String login;
  private Integer public_repos;
  private Integer followers;
  private Integer following;
  private String  avatarUrl;
}
