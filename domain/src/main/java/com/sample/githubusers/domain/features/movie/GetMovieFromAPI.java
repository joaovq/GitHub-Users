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
package com.sample.githubusers.domain.features.movie;

import com.fernandocejas.arrow.checks.Preconditions;
import com.sample.githubusers.domain.executor.PostExecutionThread;
import com.sample.githubusers.domain.executor.ThreadExecutor;
import com.sample.githubusers.domain.features.UseCase;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving data related to an specific {@link Movie}.
 */
public class GetMovieFromAPI extends UseCase<Movie, GetMovieFromAPI.Params> {
  public static final String NAME = "MOVIE_API";

  private final MovieRepository repository;

  @Inject
  public GetMovieFromAPI(MovieRepository repository,
                         ThreadExecutor threadExecutor,
                         PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.repository = repository;
  }

  @Override
  public Observable<Movie> buildUseCaseObservable(Params params) {
    Preconditions.checkNotNull(params);
    return this.repository.movieFromAPI(params.title);
  }

  public static final class Params {

    private final String title;

    private Params(String title) {
      this.title = title;
    }

    public static Params forMovie(String userId) {
      return new Params(userId);
    }
  }
}
