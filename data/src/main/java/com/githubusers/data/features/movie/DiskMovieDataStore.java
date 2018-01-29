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
package com.githubusers.data.features.movie;

import com.githubusers.data.features.user.RealmUserEntityImpl;
import com.githubusers.data.features.user.UserDataStore;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * {@link MovieDataStore} implementation based on file system data store.
 */
public class DiskMovieDataStore implements MovieDataStore {
  private final RealmMovieEntityImpl realmMovieEntityImpl;

  /**
   * Construct a {@link UserDataStore} based file system data store.
   */
  @Inject
  public DiskMovieDataStore(RealmMovieEntityImpl realmUserEntityImpl) {
    this.realmMovieEntityImpl = realmUserEntityImpl;
    EventBus.getDefault().register(this);
  }

  @Override
  public Observable<MovieEntity> movieEntityDetailsFromAPI(final String title) {
    return realmMovieEntityImpl.getMovie(title);
  }

  @Override
  public Observable<MovieEntity> movieEntityDetailsFromLOD(String title) {
    return null;
  }

  @Override
  public Observable<MovieEntity> movieEntityDetailsFromWebsite(String title) {
    return null;
  }

  @Override
  public Observable<MovieEntity> movieEntityDetails(String title) {
    return null;
  }

  @Subscribe(sticky = true, threadMode = ThreadMode.POSTING)
  public void addMovie(MovieEntity movieEntity){
    realmMovieEntityImpl.addMovie(movieEntity);
  }

  /**
   * Check if movieFromAPI has information saved in cache
   * @param title User's id
   * @return {@link Boolean}
   */
  public static Boolean isCached(String title){
    return RealmMovieEntityImpl.isCached(title);
  }
}
