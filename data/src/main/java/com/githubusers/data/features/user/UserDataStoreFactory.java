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

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.githubusers.domain.executor.ThreadExecutor;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Retrofit;

/**
 * Factory that creates different implementations of {@link UserDataStore}.
 */
@Singleton
public class UserDataStoreFactory {

  private final Retrofit retrofit;
  private final ThreadExecutor threadExecutor;
  private final UserEntityDataMapper userEntityDataMapper;

  @Inject
  UserDataStoreFactory(@NonNull Retrofit retrofit,
                       @NonNull ThreadExecutor threadExecutor,
                       UserEntityDataMapper userEntityDataMapper) {
    this.retrofit = retrofit;
    this.threadExecutor = threadExecutor;
    this.userEntityDataMapper = userEntityDataMapper;

    EventBus.getDefault().register(this);
  }

  @Subscribe(threadMode = ThreadMode.BACKGROUND)
  public void onNewEntityEvent(UserEntity userEntity){
    DiskUserDataStore userDataStore = new DiskUserDataStore(userEntityDataMapper);
    userDataStore.addUser(userEntity);
  }

  /**
   * Create {@link UserDataStore} from a user id.
   */
  public UserDataStore create(String userId) {
    UserDataStore userDataStore;
    if (DiskUserDataStore.isCached(userId))
        userDataStore = new DiskUserDataStore(userEntityDataMapper);
     else
        userDataStore = createCloudDataStore();

    return userDataStore;
  }

  /**
   * Create {@link UserDataStore} to retrieve data from the Cloud.
   */
  public UserDataStore createCloudDataStore() {
    return new CloudUserDataStore(retrofit, threadExecutor);
  }
}
