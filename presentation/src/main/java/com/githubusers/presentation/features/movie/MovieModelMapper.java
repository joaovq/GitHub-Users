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
package com.githubusers.presentation.features.movie;


import com.githubusers.presentation.di.PerActivity;
import com.sample.githubusers.domain.features.movie.Movie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link Movie} (in the domain layer) to {@link MovieModel} in the
 * presentation layer.
 */
@PerActivity
public class MovieModelMapper {

    @Inject
    public MovieModelMapper() {}

    /**
     * Transform a {@link Movie} into an {@link MovieModel}.
     *
     * @param movie Object to be transformed.
     * @return {@link MovieModel}.
     */
    public MovieModel transform(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final MovieModel movieModel = new MovieModel();
        movieModel.setTitle(movie.getTitle());
        movieModel.setYear(movie.getYear());

        return movieModel;
    }

    /**
     * Transform a Collection of {@link Movie} into a Collection of {@link MovieModel}.
     *
     * @param movies Objects to be transformed.
     * @return List of {@link MovieModel}.
     */
    public Collection<MovieModel> transform(Collection<Movie> movies) {
        Collection<MovieModel> movieModels;

        if (movies != null && !movies.isEmpty()) {
            movieModels = new ArrayList<>();
            for (Movie movie : movies) {
                movieModels.add(transform(movie));
            }
        } else {
            movieModels = Collections.emptyList();
        }

        return movieModels;
    }
}
