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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * {@link UserDataStore} implementation based on file system data store.
 */
@Singleton
class DiskUserDataStore implements UserDataStore {
  private final UserEntityDataMapper userEntityDataMapper;

  /**
   * Construct a {@link UserDataStore} based file system data store.
   */
  @Inject
  DiskUserDataStore(UserEntityDataMapper userEntityDataMapper) {
    this.userEntityDataMapper = userEntityDataMapper;
    EventBus.getDefault().register(this);
  }

  @Override
  public Observable<List<UserEntity>> userEntityList() {
    Realm realm = Realm.getDefaultInstance();
    RealmResults<RealmUserEntity> result = realm.where(RealmUserEntity.class)
            .findAll();
    List<UserEntity> userEntities = userEntityDataMapper.transformToUserEntity(result);
    realm.close();
    return Observable.just(userEntities);
  }

  @Override
  public Observable<UserEntity> userEntityDetails(final String userId) {
    Realm realm = Realm.getDefaultInstance();
    RealmResults<RealmUserEntity> result = realm.where(RealmUserEntity.class)
                                                .equalTo("login",userId)
                                                .findAll();
    realm.close();
    return Observable.just(userEntityDataMapper.transformToUserEntity(result.get(0)));
  }

  /**
   * Saves a new RealmUserEntity in cache
   * @param userEntity {@link UserEntity} that represents user's data fecthed from the cloud
   */
  @Subscribe(sticky = true, threadMode = ThreadMode.POSTING)
  public void onAddUserEvent(UserEntity userEntity){
    Realm realm = Realm.getDefaultInstance();
    realm.beginTransaction();
      RealmUserEntity realmObject = realm.createObject(RealmUserEntity.class);
      realmObject.setLogin(userEntity.getLogin());
      realmObject.setAvatarUrl(userEntity.getAvatarUrl());
      realmObject.setFollowers(userEntity.getFollowers());
      realmObject.setFollowing(userEntity.getFollowing());
      realmObject.setPublic_repos(userEntity.getPublicRepos());
    realm.commitTransaction();
    realm.close();
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
