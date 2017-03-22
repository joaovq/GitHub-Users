package com.githubusers.presentation.features.users;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.githubusers.presentation.R;
import com.githubusers.presentation.di.HasComponent;
import com.githubusers.presentation.di.components.DaggerUserComponent;
import com.githubusers.presentation.di.components.UserComponent;
import com.githubusers.presentation.view.activity.BaseActivity;

public class UserDetailsActivity extends BaseActivity implements HasComponent<UserComponent> {

  public static Intent getCallingIntent(Context context) {
    Intent callingIntent = new Intent(context, UserDetailsActivity.class);
    return callingIntent;
  }

  private UserComponent userComponent;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    setContentView(R.layout.activity_layout);

    this.initializeActivity();
    this.initializeInjector();

    getActionBar().setTitle(getString(R.string.activity_user_details));
  }

  /**
   * Initializes this activity.
   */
  private void initializeActivity() {
    addFragment(R.id.fragmentContainer, new UserDetailsFragment());
  }

  private void initializeInjector() {
    this.userComponent = DaggerUserComponent.builder()
            .applicationComponent(getApplicationComponent())
            .activityModule(getActivityModule())
            .build();
  }

  @Override public UserComponent getComponent() {
    return userComponent;
  }
}
