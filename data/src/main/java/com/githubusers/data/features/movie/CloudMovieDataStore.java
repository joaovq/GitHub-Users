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

import android.util.Log;

import com.birbit.android.jobqueue.JobManager;
import com.githubusers.data.features.user.UserDataStore;
import com.githubusers.data.utils.network.NetworkInfoUtils;

import java.util.Arrays;

import javax.inject.Inject;

import io.reactivex.Observable;
import lombok.Getter;

/**
 * {@link MovieDataStore} implementation based on connections to the api (Cloud).
 */
public class CloudMovieDataStore implements MovieDataStore {

  @Getter
  private String title;

  private final String TAG = CloudMovieDataStore.class.getCanonicalName();

  private final OMDbServiceImpl   omdbService;
  private final MovieOkHttp       movieOkHttp;
  private final WebExtractor      webExtractor;
  private final NetworkInfoUtils  networkInfoUtils;
  private final JobManager        jobManager;
  private final MovieJoiner       movieJoiner;

  /**
   * Construct a {@link UserDataStore} based on connections to the api (Cloud).
   *
   */
  @Inject
  public CloudMovieDataStore(OMDbServiceImpl omdbService,
                             MovieOkHttp movieOkHttp,
                             WebExtractor webExtractor, JobManager jobManager,
                             NetworkInfoUtils networkInfoUtils, MovieJoiner movieJoiner) {
    this.omdbService = omdbService;
    this.webExtractor = webExtractor;
    this.networkInfoUtils = networkInfoUtils;
    this.jobManager = jobManager;
    this.movieOkHttp = movieOkHttp;
    this.movieJoiner = movieJoiner;
  }

  /**
   * Adds job to be done when internet connection is back
   */
  private void movieEntityDetailsJobQueue(){
  }

  @Override
  public Observable<MovieEntity> movieEntityDetailsFromAPI(String title) {
    this.title = title;
    if(networkInfoUtils.isConnected())
      return omdbService.getMovie(title);
    else {
      movieEntityDetailsJobQueue();
      return Observable.empty();
    }
  }

  @Override
  public Observable<MovieEntity> movieEntityDetailsFromLOD(String title) {
    this.title = title;
    if(networkInfoUtils.isConnected())
      return movieOkHttp.getMovie(title, new MovieEntity());
    else {
      movieEntityDetailsJobQueue();
      return Observable.empty();
    }
  }

  @Override
  public Observable<MovieEntity> movieEntityDetailsFromWebsite(String title) {
    this.title = title;
    if (networkInfoUtils.isConnected())
      return webExtractor.getMovie(title, new MovieEntity());
    else {
      movieEntityDetailsJobQueue();
      return Observable.empty();
    }
  }

  @Override
  public Observable<MovieEntity> movieEntityDetails(String title) {
    this.title = title;
    if (networkInfoUtils.isConnected()) {
      return Observable.create(emitter -> {
        omdbService.getMovie(title).subscribe(movieFromOMDb -> {
          movieOkHttp.getMovie(title, movieFromOMDb).subscribe(movieFromLOD -> {
            webExtractor.getMovie(title,movieFromOMDb).subscribe(movieFromWeb -> {MovieEntity finalMovieEntity = movieJoiner.execute(Arrays.asList(movieFromOMDb,movieFromLOD,movieFromWeb));
              emitter.onNext(finalMovieEntity);
              emitter.onComplete();
            });
          });
        });
      });
    } else
      return Observable.empty();
  }
}
