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
import com.sample.githubusers.domain.executor.ThreadExecutor;
import com.sample.githubusers.domain.features.user.User;

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
public class CloudUserDataStore implements UserDataStore {

  @Getter String userId;

  private final GitHubServiceImpl gitHubService;
  private final NetworkInfoUtils  networkInfoUtils;
  private final JobManager        jobManager;

  /**
   * Construct a {@link UserDataStore} based on connections to the api (Cloud).
   *
   */
  @Inject
  public CloudUserDataStore(GitHubServiceImpl gitHubService,
                            JobManager jobManager,
                            NetworkInfoUtils networkInfoUtils) {
    this.gitHubService = gitHubService;
    this.networkInfoUtils = networkInfoUtils;
    this.jobManager = jobManager;
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
   * Adds job to be done when internet connection is back
   */
  private void userEntityDetailsJobQueue(){
    jobManager.addJobInBackground(new GetUsersJob(userId));
  }

  /**
   * Makes connection with REST API to get users
   * @return {@link Observable} of {@link UserEntity}
   */
  public Observable<UserEntity> userEntityDetailsRetrofit(){
    return gitHubService.getUsers(userId);
  }
}
