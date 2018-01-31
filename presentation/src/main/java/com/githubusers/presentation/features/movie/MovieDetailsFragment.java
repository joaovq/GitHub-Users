/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.githubusers.presentation.features.movie;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.githubusers.presentation.R;
import com.githubusers.presentation.di.components.MovieComponent;
import com.githubusers.presentation.events.ArgumentEvent;
import com.githubusers.presentation.utils.glide.GlideLoader;
import com.githubusers.presentation.view.fragment.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fragment that shows details of a certain user.
 */
public class MovieDetailsFragment extends BaseFragment implements MovieDetailsView {

  @Inject MovieDetailsPresenter detailsPresenter;
  @Inject GlideLoader           glideLoader;

  @Bind(R.id.iv_cover)                ImageView         coverImageView;
  @Bind(R.id.tv_movie_title)          TextView          titleTextView;
  @Bind(R.id.tv_movie_year)           TextView          yearTextView;
  @Bind(R.id.tv_movie_released)       TextView          releasedTextView;
  @Bind(R.id.tv_movie_runtime)        TextView          runtimeTextView;
  @Bind(R.id.tv_movie_awards)         TextView          awardsTextView;
  @Bind(R.id.tv_movie_director)       TextView          directorTextView;
  @Bind(R.id.tv_movie_actors)         TextView          actorsTextView;
  @Bind(R.id.tv_movie_budget)         TextView          budgetTextView;
  @Bind(R.id.tv_movie_distribuion)    TextView          distribuitonTextView;
  @Bind(R.id.tv_movie_cinematographers)TextView         cinematographersTextView;
  @Bind(R.id.tv_movie_production)     TextView          productionTextView;
  @Bind(R.id.tv_movie_imdb_votes)     TextView          imdbVotesTextView;
  @Bind(R.id.tv_movie_imdb_ratings)   TextView          imdbRatingsTextView;
  @Bind(R.id.tv_movie_boxoffice)      TextView          boxOfficeTextView;
  @Bind(R.id.tv_movie_genre)          TextView          genreTextView;
  @Bind(R.id.tv_movie_plot)          TextView           plotTextView;
  @Bind(R.id.tv_movie_writters)       TextView          writtersTextView;
  @Bind(R.id.tv_movie_country)        TextView          countryTextView;
  @Bind(R.id.rl_progress)             RelativeLayout    progressLayout;
  @Bind(R.id.rl_retry)                RelativeLayout    retryLayout;
  @Bind(R.id.rl_no_internet)          RelativeLayout    noInternetLayout;
  @Bind(R.id.bt_retry)                Button            retryButton;

  private String currentMovieTitle;
  private String currentButton;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getComponent(MovieComponent.class).inject(this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    final View fragmentView = inflater.inflate(R.layout.fragment_movie_details, container, false);
    ButterKnife.bind(this, fragmentView);
    return fragmentView;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    this.detailsPresenter.setView(this);
  }

  @Override
  public void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
  }

  @Override
  public void onStop() {
    super.onStop();
    EventBus.getDefault().unregister(this);
  }

  @Override
  public void onResume() {
    super.onResume();
    this.detailsPresenter.resume();
  }

  @Override
  public void onPause() {
    super.onPause();
    this.detailsPresenter.pause();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    this.detailsPresenter.destroy();
  }

  @Override
  public void onMovie(MovieModel movieModel) {
    if (movieModel != null) {
      setUserAvatar(movieModel.getPoster());
      yearTextView.setText(movieModel.getYear());
      titleTextView.setText(movieModel.getTitle());
      directorTextView.setText(movieModel.getDirector());
      releasedTextView.setText(movieModel.getReleased());
      runtimeTextView.setText(transforListToString(movieModel.getRuntimes()));
      awardsTextView.setText(movieModel.getAwards());
      actorsTextView.setText(transforListToString(movieModel.getActors()));
      budgetTextView.setText(movieModel.getBudget());
      distribuitonTextView.setText(transforListToString(movieModel.getDistribution()));
      cinematographersTextView.setText(transforListToString(movieModel.getCinematographers()));
      productionTextView.setText(movieModel.getProduction());
      imdbVotesTextView.setText(movieModel.getImdbVotes());
      imdbRatingsTextView.setText(movieModel.getImdbRating());
      boxOfficeTextView.setText(movieModel.getBoxOffice());
      writtersTextView.setText(transforListToString(movieModel.getWriters()));
      plotTextView.setText(movieModel.getPlot());
      countryTextView.setText(transforListToString(movieModel.getCountries()));
      genreTextView.setText(movieModel.getGenre());
    }
  }

  @Override
  public void showLoading() {
    this.getActivity().setProgressBarIndeterminateVisibility(true);
  }

  @Override
  public void hideLoading() {
    this.getActivity().setProgressBarIndeterminateVisibility(false);
  }

  @Override
  public void showRetry() {

  }

  @Override
  public void hideRetry() {

  }

  @Override
  public void showError(String message) {
    this.showToastMessage(message);
    getActivity().onBackPressed();
  }

  @Override
  public Context context() {
    return getActivity().getApplicationContext();
  }

  @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
  public void onMessageEvent(ArgumentEvent event) {
    currentMovieTitle = (String) event.getArgument1();
    currentButton = (String) event.getArgument2();
    this.loadMovieDetails();
  }

  private String transforListToString(List<String> strings) {
    if(strings == null || strings.isEmpty())
      return "";

    String finalString = strings.get(0);
    for(int i = 1; i < strings.size()-1;i++)
      finalString += (", " + strings.get(i));
    if(strings.size() >= 2)
      finalString += (", " + strings.get(strings.size()-1));
    return finalString;
  }


  /**
   * Load user details.
   */
  private void loadMovieDetails() {
    if (this.detailsPresenter != null) {
      currentMovieTitle = currentMovieTitle.replace(" ", "+");
        this.detailsPresenter.initialize(currentMovieTitle,currentButton);
    }
  }

  /**
   * Loads image from url
   * @param url Image's path
   */
  private void setUserAvatar(String url){
    glideLoader.load(url,coverImageView);
  }
}
