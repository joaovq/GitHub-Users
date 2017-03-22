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
package com.githubusers.presentation.features.users;

import android.support.annotation.NonNull;
import android.util.Log;


import com.githubusers.domain.exception.DefaultErrorBundle;
import com.githubusers.domain.exception.ErrorBundle;
import com.githubusers.domain.features.DefaultObserver;
import com.githubusers.domain.features.UseCase;
import com.githubusers.domain.features.user.GetUserDetails;
import com.githubusers.domain.features.user.User;
import com.githubusers.presentation.exception.ErrorMessageFactory;
import com.githubusers.presentation.features.Presenter;
import com.githubusers.domain.features.user.GetUserDetails.Params;
import com.githubusers.presentation.di.PerActivity;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class UserDetailsPresenter implements Presenter {

  private UserDetailsView viewDetailsView;

  private final UseCase getUserDetailsUseCase;
  private final UserModelDataMapper userModelDataMapper;

  @Inject
  public UserDetailsPresenter(@Named(GetUserDetails.NAME) UseCase getUserDetailsUseCase,
                              UserModelDataMapper userModelDataMapper) {
    this.getUserDetailsUseCase = getUserDetailsUseCase;
    this.userModelDataMapper = userModelDataMapper;
  }

  public void setView(@NonNull UserDetailsView view) {
    this.viewDetailsView = view;
  }

  @Override
  public void resume() {}

  @Override
  public void pause() {}

  @Override
  public void destroy() {
    this.getUserDetailsUseCase.dispose();
    this.viewDetailsView = null;
  }

  /**
   * Initializes the presenter by showing/hiding proper views
   * and retrieving user details.
   */
  public void initialize(String userId) {
    this.hideViewRetry();
    this.showViewLoading();
    this.getUserDetails(userId);
  }

  private void getUserDetails(String userId) {
    this.getUserDetailsUseCase.execute(new UserDetailsObserver(), Params.forUser(userId));
  }

  private void showViewLoading() {
    this.viewDetailsView.showLoading();
  }

  private void hideViewLoading() {
    this.viewDetailsView.hideLoading();
  }

  private void showViewRetry() {
    this.viewDetailsView.showRetry();
  }

  private void hideViewRetry() {
    this.viewDetailsView.hideRetry();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.viewDetailsView.context(),
        errorBundle.getException());
    this.viewDetailsView.showError(errorMessage);
  }

  private void showUserDetailsInView(User user) {
    final UserModel userModel = this.userModelDataMapper.transform(user);
    this.viewDetailsView.renderUser(userModel);
  }

  private final class UserDetailsObserver extends DefaultObserver<User> {

    @Override
    public void onComplete() {
      UserDetailsPresenter.this.hideViewLoading();
    }

    @Override
    public void onError(Throwable e) {
      UserDetailsPresenter.this.hideViewLoading();
      UserDetailsPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
      UserDetailsPresenter.this.showViewRetry();
    }

    @Override
    public void onNext(User user) {
      Log.e(UserDetailsPresenter.class.getName(),user.getLogin());
//      UserDetailsPresenter.this.showUserDetailsInView(user);
    }
  }
}
