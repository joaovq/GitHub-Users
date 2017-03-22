/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.githubusers.presentation.features.users;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fernandocejas.arrow.checks.Preconditions;
import com.githubusers.presentation.R;
import com.githubusers.presentation.di.components.UserComponent;
import com.githubusers.presentation.view.fragment.BaseFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Fragment that shows details of a certain user.
 */
public class UserDetailsFragment extends BaseFragment implements UserDetailsView {
  private static final String PARAM_USER_ID = "param_user_id";

  @Inject
  UserDetailsPresenter userDetailsPresenter;

  public static UserDetailsFragment forUser(String userId) {
    final UserDetailsFragment userDetailsFragment = new UserDetailsFragment();
    final Bundle arguments = new Bundle();
    arguments.putString(PARAM_USER_ID, userId);
    userDetailsFragment.setArguments(arguments);
    return userDetailsFragment;
  }

  public UserDetailsFragment() {
    setRetainInstance(true);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getComponent(UserComponent.class).inject(this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    final View fragmentView = inflater.inflate(R.layout.fragment_user_details, container, false);
    ButterKnife.bind(this, fragmentView);
    return fragmentView;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    this.userDetailsPresenter.setView(this);
    if (savedInstanceState == null) {
      this.loadUserDetails();
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    this.userDetailsPresenter.resume();
  }

  @Override
  public void onPause() {
    super.onPause();
    this.userDetailsPresenter.pause();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    this.userDetailsPresenter.destroy();
  }

  @Override
  public void renderUser(UserModel user) {
    if (user != null) {

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
  }

  @Override
  public Context context() {
    return getActivity().getApplicationContext();
  }

  /**
   * Load user details.
   */
  private void loadUserDetails() {
    if (this.userDetailsPresenter != null) {
      this.userDetailsPresenter.initialize(currentUserId());
    }
  }

  /**
   * Get current user id from fragments arguments.
   */
  private String currentUserId() {
    final Bundle arguments = getArguments();
    Preconditions.checkNotNull(arguments, "Fragment arguments cannot be null");
    return arguments.getString(PARAM_USER_ID);
  }
}
