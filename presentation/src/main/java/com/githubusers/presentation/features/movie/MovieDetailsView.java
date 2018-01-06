/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.githubusers.presentation.features.movie;

import com.githubusers.presentation.view.LoadDataView;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a movie info.
 */
public interface MovieDetailsView extends LoadDataView {
  /**
   * Render a movie in the UI.
   *
   * @param movie The {@link MovieModel} that will be shown.
   */
  void onMovie(MovieModel movie);
}
