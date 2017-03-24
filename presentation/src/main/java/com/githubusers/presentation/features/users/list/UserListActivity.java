/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.githubusers.presentation.features.users.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.githubusers.presentation.R;
import com.githubusers.presentation.di.HasComponent;
import com.githubusers.presentation.di.components.DaggerUserComponent;
import com.githubusers.presentation.di.components.UserComponent;
import com.githubusers.presentation.features.users.UserModel;
import com.githubusers.presentation.view.activity.BaseActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Activity that shows a list of Users.
 */
public class UserListActivity extends BaseActivity implements HasComponent<UserComponent> {

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, UserListActivity.class);
  }

  private UserComponent userComponent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    setContentView(R.layout.activity_layout);

    this.initializeInjector();
    if (savedInstanceState == null) {
      addFragment(R.id.fragmentContainer, new UserListFragment());
    }

    getActionBar().setTitle(getString(R.string.activity_user_list));
  }

  private void initializeInjector() {
    this.userComponent = DaggerUserComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
  }

  @Override
  public UserComponent getComponent() {
    return userComponent;
  }

  @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
  public void onUserClickedEvent(UserModel userModel){
  }
}
