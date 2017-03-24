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

import com.birbit.android.jobqueue.JobManager;
import com.githubusers.data.utils.network.NetworkInfoUtils;
import com.githubusers.domain.executor.ThreadExecutor;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;
import retrofit2.Retrofit;

/**
 * {@link UserDataStore} implementation based on connections to the api (Cloud).
 */
@Singleton
class CloudUserDataStore implements UserDataStore {

  @Getter String userId;

  private final Retrofit          retrofit;
  private final ThreadExecutor    threadExecutor;
  private final JobManager        jobManager;
  private final NetworkInfoUtils  networkInfoUtils;

  /**
   * Construct a {@link UserDataStore} based on connections to the api (Cloud).
   *
   */
  @Inject
  CloudUserDataStore(Retrofit retrofit,
                     ThreadExecutor threadExecutor,
                     JobManager jobManager,
                     NetworkInfoUtils networkInfoUtils) {
    this.retrofit = retrofit;
    this.threadExecutor = threadExecutor;
    this.jobManager = jobManager;
    this.networkInfoUtils = networkInfoUtils;
  }

  @Override
  public Observable<List<UserEntity>> userEntityList() {
    return null;
  }

  @Override
  public Observable<UserEntity> userEntityDetails(final String userId) {
    this.userId = userId;
    if(networkInfoUtils.isConnected())
      return userEntityDetailsRetrofit();
    else {
      userEntityDetailsJobQueue();
      return Observable.empty();
    }
  }

  /**
   * Makes connection with REST API to get users
   * @return {@link Observable} of {@link UserEntity}
   */
  private Observable<UserEntity> userEntityDetailsRetrofit(){
    GitHubService service = retrofit.create(GitHubService.class);
    Observable<UserEntity> result = service.getUser(userId);
    result.subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(Schedulers.from(threadExecutor))
            .subscribe(userEntity -> {
              EventBus.getDefault().post(userEntity);
            });
    return result;
  }

  /**
   * Adds job to be done when internet connection is back
   */
  private void userEntityDetailsJobQueue(){
    jobManager.addJobInBackground(new GetUsersJob(userId));
  }
}
