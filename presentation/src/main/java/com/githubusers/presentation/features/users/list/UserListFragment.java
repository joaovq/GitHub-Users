/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.githubusers.presentation.features.users.list;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.githubusers.presentation.R;
import com.githubusers.presentation.di.components.UserComponent;
import com.githubusers.presentation.features.users.UserModel;
import com.githubusers.presentation.view.fragment.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fragment that shows a list of Users.
 */
public class UserListFragment extends BaseFragment implements UserListView {

  @Inject UserListPresenter userListPresenter;

  @Bind(R.id.recycler_users) RecyclerView userRecyclerView;
  @Bind(R.id.rl_progress)   RelativeLayout rl_progress;

  @Inject UserDataAdapter usersAdapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getComponent(UserComponent.class).inject(this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    final View fragmentView = inflater.inflate(R.layout.fragment_user_list, container, false);
    ButterKnife.bind(this, fragmentView);
    setupRecyclerView();
    return fragmentView;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    this.userListPresenter.setView(this);
    this.loadUserList();
  }

  @Override
  public void onResume() {
    super.onResume();
    this.userListPresenter.resume();
  }

  @Override
  public void onPause() {
    super.onPause();
    this.userListPresenter.pause();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    userRecyclerView.setAdapter(null);
    ButterKnife.unbind(this);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    this.userListPresenter.destroy();
  }

  @Override
  public void showLoading() {
    this.rl_progress.setVisibility(View.VISIBLE);
    this.getActivity().setProgressBarIndeterminateVisibility(true);
  }

  @Override
  public void hideLoading() {
    this.rl_progress.setVisibility(View.GONE);
    this.getActivity().setProgressBarIndeterminateVisibility(false);
  }

  @Override
  public void showRetry() {

  }

  @Override
  public void hideRetry() {

  }

  @Override
  public void showUsersList(Collection<UserModel> userModelCollection) {
    if (userModelCollection != null) {
      this.usersAdapter.setUsers(userModelCollection);
    }
  }

  @Override
  public void viewUser(UserModel userModel) {
    EventBus.getDefault().postSticky(userModel);
  }

  @Override
  public void showError(String message) {
    this.showToastMessage(message);
  }

  @Override
  public Context context() {
    return this.getActivity().getApplicationContext();
  }

  private void setupRecyclerView() {
    this.userRecyclerView.setLayoutManager(new UsersLayoutManager(context()));
    this.userRecyclerView.setAdapter(usersAdapter);
  }

  /**
   * Loads all users.
   */
  private void loadUserList() {
    this.userListPresenter.initialize();
  }

//  private UserDataAdapter.OnItemClickListener onItemClickListener =
//      new UsersAdapter.OnItemClickListener() {
//        @Override
//        public void onUserItemClicked(UserModel userModel) {
//          if (UserListFragment.this.userListPresenter != null && userModel != null) {
//            UserListFragment.this.userListPresenter.onUserClicked(userModel);
//          }
//        }
//      };
}
