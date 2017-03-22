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

import com.githubusers.domain.executor.ThreadExecutor;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Retrofit;

/**
 * Factory that creates different implementations of {@link UserDataStore}.
 */
@Singleton
public class UserDataStoreFactory {

  private final Context context;
  private final Retrofit retrofit;
  private final ThreadExecutor threadExecutor;

  @Inject
  UserDataStoreFactory(@NonNull Context context, @NonNull Retrofit retrofit, @NonNull ThreadExecutor threadExecutor) {
    this.context = context.getApplicationContext();
    this.retrofit = retrofit;
    this.threadExecutor = threadExecutor;
  }

  /**
   * Create {@link UserDataStore} from a user id.
   */
  public UserDataStore create(String userId) {
    UserDataStore userDataStore;

//    if (!this.userCache.isExpired() && this.userCache.isCached(userId)) {
//      userDataStore = new DiskUserDataStore(this.userCache);
//    } else {
//      userDataStore = createCloudDataStore();
//    }

    return null;
  }

  /**
   * Create {@link UserDataStore} to retrieve data from the Cloud.
   */
  public UserDataStore createCloudDataStore() {
    return new CloudUserDataStore(retrofit, threadExecutor);
  }

  /**
   * Create {@link UserDataStore} to retrieve data from the Cache.
   */
  public UserDataStore createDiskDataStore() {
    return new DiskUserDataStore();
  }
}
