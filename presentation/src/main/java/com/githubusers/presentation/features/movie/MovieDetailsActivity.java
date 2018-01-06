package com.githubusers.presentation.features.movie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.githubusers.presentation.R;
import com.githubusers.presentation.di.HasComponent;
import com.githubusers.presentation.di.components.DaggerMovieComponent;
import com.githubusers.presentation.di.components.DaggerUserComponent;
import com.githubusers.presentation.di.components.MovieComponent;
import com.githubusers.presentation.features.users.details.UserDetailsFragment;
import com.githubusers.presentation.view.activity.BaseActivity;

public class MovieDetailsActivity extends BaseActivity implements HasComponent<MovieComponent> {

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, MovieDetailsActivity.class);
  }

  private MovieComponent component;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    setContentView(R.layout.activity_layout);

    this.initializeActivity();
    this.initializeInjector();

    getActionBar().setTitle(getString(R.string.activity_movie_details));
  }

  /**
   * Initializes this activity.
   */
  private void initializeActivity() {
    addFragment(R.id.fragmentContainer, new MovieDetailsFragment());
  }

  private void initializeInjector() {
    this.component = DaggerMovieComponent.builder()
            .applicationComponent(getApplicationComponent())
            .activityModule(getActivityModule())
            .build();
  }

  @Override public MovieComponent getComponent() {
    return component;
  }
}
