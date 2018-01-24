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
package com.githubusers.data.features.movie;

import com.birbit.android.jobqueue.JobManager;
import com.githubusers.data.features.user.GetUsersJob;
import com.githubusers.data.features.user.GitHubServiceImpl;
import com.githubusers.data.features.user.UserDataStore;
import com.githubusers.data.utils.network.NetworkInfoUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import lombok.Getter;

/**
 * {@link MovieDataStore} implementation based on connections to the api (Cloud).
 */
public class CloudMovieDataStore implements MovieDataStore {

  @Getter
  private String title;

  private final OMDbServiceImpl   omdbService;
  private final MovieOkHttp       movieOkHttp;
  private final WebExtractor      webExtractor;
  private final NetworkInfoUtils  networkInfoUtils;
  private final JobManager        jobManager;

  /**
   * Construct a {@link UserDataStore} based on connections to the api (Cloud).
   *
   */
  @Inject
  public CloudMovieDataStore(OMDbServiceImpl omdbService,
                             MovieOkHttp movieOkHttp,
                             WebExtractor webExtractor, JobManager jobManager,
                             NetworkInfoUtils networkInfoUtils) {
    this.omdbService = omdbService;
    this.webExtractor = webExtractor;
    this.networkInfoUtils = networkInfoUtils;
    this.jobManager = jobManager;
    this.movieOkHttp = movieOkHttp;
  }

  /**
   * Adds job to be done when internet connection is back
   */
  private void movieEntityDetailsJobQueue(){
  }

  /**
   * Makes connection with REST API to get users
   * @return {@link Observable} of {@link MovieEntity}
   */
  private Observable<MovieEntity> movieEntityDetailsRetrofit(){
    return omdbService.getMovie(title);
  }

  @Override
  public Observable<MovieEntity> movieEntityDetailsFromAPI(String title) {
    this.title = title;
    if(networkInfoUtils.isConnected())
      return movieEntityDetailsRetrofit();
    else {
      movieEntityDetailsJobQueue();
      return Observable.empty();
    }
  }

  @Override
  public Observable<MovieEntity> movieEntityDetailsFromLOD(String title) {
    this.title = title;
    if(networkInfoUtils.isConnected())
      return movieOkHttp.getMovie(title);
    else {
      movieEntityDetailsJobQueue();
      return Observable.empty();
    }
  }

  @Override
  public Observable<MovieEntity> movieEntityDetailsFromWebsite(String title) {
    this.title = title;
    if (networkInfoUtils.isConnected())
      return webExtractor.getMovie(title);
    else {
      movieEntityDetailsJobQueue();
      return Observable.empty();
    }
  }
}
