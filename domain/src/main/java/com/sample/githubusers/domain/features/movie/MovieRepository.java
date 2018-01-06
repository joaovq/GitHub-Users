package com.sample.githubusers.domain.features.movie;

import com.sample.githubusers.domain.features.user.User;

import io.reactivex.Observable;

/**
 * Interface that represents a Repository for getting {@link Movie} related data.
 */
public interface MovieRepository {

  /**
   * Get an {@link Observable} which will emit a {@link Movie}.
   *
   * @param title The title of the wanted movie
   */
  Observable<Movie> movie(final String title);
}
