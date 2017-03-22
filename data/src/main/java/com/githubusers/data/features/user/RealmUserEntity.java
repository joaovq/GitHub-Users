package com.githubusers.data.features.user;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

/**
 * Class that represents a GitHub's user in the domain layer
 */
@Setter
@Getter
public class RealmUserEntity extends RealmObject {
  private String login;
  private Integer public_repos;
  private Integer followers;
  private Integer following;
  private String  avatarUrl;
}
