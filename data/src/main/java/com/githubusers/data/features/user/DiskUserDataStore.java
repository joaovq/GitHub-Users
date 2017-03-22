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

import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * {@link UserDataStore} implementation based on file system data store.
 */
class DiskUserDataStore implements UserDataStore {
  private final Realm realm;
  private final UserEntityDataMapper userEntityDataMapper;

  /**
   * Construct a {@link UserDataStore} based file system data store.
   */
  DiskUserDataStore(UserEntityDataMapper userEntityDataMapper) {
    this.realm = Realm.getDefaultInstance();
    this.userEntityDataMapper = userEntityDataMapper;
  }

  @Override
  public Observable<List<UserEntity>> userEntityList() {
    //TODO: implement simple cache for storing/retrieving collections of users.
    throw new UnsupportedOperationException("Operation is not available!!!");
  }

  @Override
  public Observable<UserEntity> userEntityDetails(final String userId) {
    RealmResults<RealmUserEntity> result = realm.where(RealmUserEntity.class)
                                                .equalTo("login",userId)
                                                .findAll();

    return Observable.just(userEntityDataMapper.transform(result.get(0)));
  }

  /**
   * Saves a new RealmUserEntity in cache
   * @param userEntity {@link UserEntity} that represents user's data fecthed from the cloud
   */
  public void addUser(UserEntity userEntity){
    realm.beginTransaction();
      RealmUserEntity realmObject = realm.createObject(RealmUserEntity.class);
      realmObject.setLogin(userEntity.getLogin());
      realmObject.setAvatarUrl(userEntity.getAvatarUrl());
      realmObject.setFollowers(userEntity.getFollowers());
      realmObject.setFollowing(userEntity.getFollowing());
      realmObject.setPublic_repos(userEntity.getPublicRepos());
    realm.commitTransaction();
  }

  /**
   * Check if user has information saved in cache
   * @param userId User's id
   * @return {@link Boolean}
   */
  public static Boolean isCached(String userId){
    return Realm.getDefaultInstance().where(RealmUserEntity.class).equalTo("login",userId).findAll().size() > 0;
  }
}
