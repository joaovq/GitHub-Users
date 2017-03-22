/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.githubusers.data.features.user;

import android.util.Log;

import com.githubusers.domain.executor.ThreadExecutor;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * {@link UserDataStore} implementation based on connections to the api (Cloud).
 */
class CloudUserDataStore implements UserDataStore {

  private final Retrofit retrofit;
  private final ThreadExecutor threadExecutor;
  private final UserDataStoreFactory userDataStoreFactory;

  /**
   * Construct a {@link UserDataStore} based on connections to the api (Cloud).
   *
   * @param retrofit Retrofit instance used to communicate with GitHub's REST API
   */
  CloudUserDataStore(Retrofit retrofit, ThreadExecutor threadExecutor) {
    this.retrofit =retrofit;
    this.threadExecutor = threadExecutor;
    userDataStoreFactory = null;
  }

  @Override
  public Observable<List<UserEntity>> userEntityList() {
    return null;
  }

  @Override
  public Observable<UserEntity> userEntityDetails(final String userId) {
    GitHubService service = retrofit.create(GitHubService.class);
    Observable<UserEntity> result = service.getUser(userId);
    result.subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(Schedulers.from(threadExecutor))
            .subscribe(userEntity -> {
                EventBus.getDefault().postSticky(userEntity);
            });
    return result;
  }
}
