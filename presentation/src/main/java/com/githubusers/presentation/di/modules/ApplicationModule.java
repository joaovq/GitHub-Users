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
package com.githubusers.presentation.di.modules;

import android.content.Context;

import com.githubusers.data.executor.JobExecutor;
import com.githubusers.data.features.movie.MovieDataRepository;
import com.githubusers.data.features.user.UserDataRepository;
import com.githubusers.presentation.AndroidApplication;
import com.githubusers.presentation.UIThread;
import com.sample.githubusers.domain.executor.PostExecutionThread;
import com.sample.githubusers.domain.executor.ThreadExecutor;
import com.sample.githubusers.domain.features.movie.MovieRepository;
import com.sample.githubusers.domain.features.user.UserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
  private final AndroidApplication application;

  public ApplicationModule(AndroidApplication application) {
    this.application = application;
  }

  @Provides @Singleton
  Context provideApplicationContext() {
    return this.application;
  }

  @Provides @Singleton
  ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides @Singleton
  PostExecutionThread providePostExecutionThread(UIThread uiThread) {
    return uiThread;
  }

  @Provides @Singleton
  UserRepository provideUserRepository() {
    UserDataRepository userDataRepository = new UserDataRepository();
    return userDataRepository;
  }

  @Provides @Singleton
  MovieRepository providesMovieRepository() {
    MovieRepository dataRepository = new MovieDataRepository();
    return dataRepository;
  }
}
