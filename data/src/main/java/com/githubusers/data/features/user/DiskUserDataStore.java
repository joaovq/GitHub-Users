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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * {@link UserDataStore} implementation based on file system data store.
 */
@Singleton
class DiskUserDataStore implements UserDataStore {
  private final RealmUserEntityImpl realmUserEntityImpl;

  /**
   * Construct a {@link UserDataStore} based file system data store.
   */
  @Inject
  DiskUserDataStore(RealmUserEntityImpl realmUserEntityImpl) {
    this.realmUserEntityImpl = realmUserEntityImpl;
    EventBus.getDefault().register(this);
  }

  @Override
  public Observable<List<UserEntity>> userEntityList() {
    return realmUserEntityImpl.getUsers();
  }

  @Override
  public Observable<UserEntity> userEntityDetails(final String userId) {
    return realmUserEntityImpl.getUser(userId);
  }

  @Subscribe(sticky = true, threadMode = ThreadMode.POSTING)
  public void addUser(UserEntity userEntity){
    realmUserEntityImpl.addUser(userEntity);
  }

  /**
   * Check if user has information saved in cache
   * @param userId User's id
   * @return {@link Boolean}
   */
  public static Boolean isCached(String userId){
    return RealmUserEntityImpl.isCached(userId);
  }
}
