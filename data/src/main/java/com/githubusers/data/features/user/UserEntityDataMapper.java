/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.githubusers.data.features.user;

import com.githubusers.domain.features.user.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link UserEntity} (in the data layer) to {@link User} in the
 * domain layer.
 */
@Singleton
public class UserEntityDataMapper {

  @Inject
  UserEntityDataMapper() {}

  /**
   * Transform a {@link UserEntity} into an {@link User}.
   *
   * @param userEntity Object to be transformed.
   * @return {@link User} if valid {@link UserEntity} otherwise null.
   */
  public User transform(UserEntity userEntity) {
    User user = null;
    if (userEntity != null) {
      user = new User();
      user.setLogin(userEntity.getLogin());
      user.setRepos(userEntity.getPublicRepos());
      user.setAvatarUrl(userEntity.getAvatarUrl());
      user.setFollowers(userEntity.getFollowers());
      user.setFollowing(userEntity.getFollowing());
    }
    return user;
  }

  /**
   * Transform a {@link RealmUserEntity} into an {@link UserEntity}.
   *
   * @param realmUserEntity Object to be transformed.
   * @return {@link UserEntity} if valid {@link RealmUserEntity} otherwise null.
   */
  public UserEntity transform(RealmUserEntity realmUserEntity) {
    UserEntity userEntity = null;
    if (realmUserEntity != null) {
      userEntity = new UserEntity();
      userEntity.setLogin(realmUserEntity.getLogin());
      userEntity.setPublicRepos(realmUserEntity.getPublic_repos());
      userEntity.setAvatarUrl(realmUserEntity.getAvatarUrl());
      userEntity.setFollowers(realmUserEntity.getFollowers());
      userEntity.setFollowing(realmUserEntity.getFollowing());
    }
    return userEntity;
  }

  /**
   * Transform a List of {@link UserEntity} into a Collection of {@link User}.
   *
   * @param userEntityCollection Object Collection to be transformed.
   * @return {@link User} if valid {@link UserEntity} otherwise null.
   */
  public List<User> transform(Collection<UserEntity> userEntityCollection) {
    final List<User> userList = new ArrayList<>(20);
    for (UserEntity userEntity : userEntityCollection) {
      final User user = transform(userEntity);
      if (user != null) {
        userList.add(user);
      }
    }
    return userList;
  }
}
