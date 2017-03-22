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

  private static final String INTENT_EXTRA_PARAM_USER_ID = "org.githubusers.INTENT_PARAM_USER_ID";
  private static final String INSTANCE_STATE_PARAM_USER_ID = "org.githubusers.STATE_PARAM_USER_ID";

  public static Intent getCallingIntent(Context context, String userId) {
    Intent callingIntent = new Intent(context, UserDetailsActivity.class);
    callingIntent.putExtra(INTENT_EXTRA_PARAM_USER_ID, userId);
    return callingIntent;
  }

  private String userId;
  private UserComponent userComponent;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    setContentView(R.layout.activity_layout);

    this.initializeActivity(savedInstanceState);
    this.initializeInjector();

    getActionBar().setTitle(getString(R.string.activity_user_details));
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    if (outState != null) {
      outState.putString(INSTANCE_STATE_PARAM_USER_ID, this.userId);
    }
    super.onSaveInstanceState(outState);
  }

  /**
   * Initializes this activity.
   */
  private void initializeActivity(Bundle savedInstanceState) {
    if (savedInstanceState == null) {
      this.userId = getIntent().getStringExtra(INTENT_EXTRA_PARAM_USER_ID);
      addFragment(R.id.fragmentContainer, UserDetailsFragment.forUser(userId));
    } else {
      this.userId = savedInstanceState.getString(INSTANCE_STATE_PARAM_USER_ID);
    }
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
