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

import com.sample.githubusers.domain.features.movie.Movie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Mapper class used to transform {@link MovieEntity} (in the data layer) to {@link Movie} in the
 * domain layer.
 */
public class MovieEntityDataMapper {

  public MovieEntityDataMapper() {}

  /**
   * Transform a {@link MovieEntity} into an {@link Movie}.
   *
   * @param movieEntity Object to be transformed.
   * @return {@link Movie} if valid {@link MovieEntity} otherwise null.
   */
  public Movie transformToMovie(MovieEntity movieEntity) {
    Movie movie = null;
    if (movieEntity != null) {
      movie = new Movie();
      movie.setTitle(movieEntity.getTitle());
      movie.setYear(movieEntity.getYear());
    }
    return movie;
  }

  /**
   * Transform a {@link RealmMovieEntity} into an {@link MovieEntity}.
   *
   * @param realmMovieEntity Object to be transformed.
   * @return {@link MovieEntity} if valid {@link RealmMovieEntity} otherwise null.
   */
  public MovieEntity transformToMovieEntity(RealmMovieEntity realmMovieEntity) {
    MovieEntity movieEntity = null;
    if (realmMovieEntity != null) {
      movieEntity = new MovieEntity();
      movieEntity.setTitle(realmMovieEntity.getTitle());
      movieEntity.setYear(realmMovieEntity.getYear());
    }
    return movieEntity;
  }

  /**
   * Transform a List of {@link MovieEntity} into a Collection of {@link Movie}.
   *
   * @param movieEntities Object Collection to be transformed.
   * @return {@link Movie} if valid {@link MovieEntity} otherwise null.
   */
  public List<Movie> transformToMovie(Collection<MovieEntity> movieEntities) {
    final List<Movie> movies = new ArrayList<>();
    for (MovieEntity movieEntity : movieEntities) {
      final Movie movie = transformToMovie(movieEntity);
      if (movie != null) {
        movies.add(movie);
      }
    }
    return movies;
  }
}
