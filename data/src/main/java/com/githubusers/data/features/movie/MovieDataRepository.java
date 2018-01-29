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


import com.githubusers.data.DataManager;
import com.githubusers.data.di.components.DaggerMovieDataComponent;
import com.githubusers.data.di.components.MovieDataComponent;
import com.githubusers.data.di.modules.MovieDataModule;
import com.sample.githubusers.domain.features.movie.Movie;
import com.sample.githubusers.domain.features.movie.MovieRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * {@link MovieRepository} for retrieving user data.
 */
public class MovieDataRepository implements MovieRepository {

  @Inject public MovieDataStoreFactory dataStoreFactory;
  @Inject public MovieEntityDataMapper entityDataMapper;

  /**
   * Constructs a {@link MovieRepository}.
   *
   */
  public MovieDataRepository() {
    initializeInjector();
  }


  private void initializeInjector(){
    MovieDataComponent dataComponent = DaggerMovieDataComponent.builder()
            .dataComponent(DataManager.getComponent())
            .movieDataModule(new MovieDataModule())
            .build();
    dataComponent.inject(this);
  }

  @Override
  public Observable<Movie> movieFromAPI(String title) {
    final MovieDataStore dataStore = this.dataStoreFactory.create(title);
    return dataStore.movieEntityDetailsFromAPI(title).map(this.entityDataMapper::transformToMovie);
  }

  @Override
  public Observable<Movie> movieFromLOD(String title) {
    final MovieDataStore dataStore = this.dataStoreFactory.createCloudDataStore();
    return dataStore.movieEntityDetailsFromLOD(title).map(this.entityDataMapper::transformToMovie);
  }

  @Override
  public Observable<Movie> movieFromWebsite(String title) {
    final MovieDataStore dataStore = this.dataStoreFactory.createCloudDataStore();
    return dataStore.movieEntityDetailsFromWebsite(title).map(this.entityDataMapper::transformToMovie);
  }

  @Override
  public Observable<Movie> movie(String title) {
    final MovieDataStore dataStore = this.dataStoreFactory.create(title);
    return dataStore.movieEntityDetails(title).map(this.entityDataMapper::transformToMovie);
  }
}
