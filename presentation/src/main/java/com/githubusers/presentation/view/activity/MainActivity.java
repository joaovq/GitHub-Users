package com.githubusers.presentation.view.activity;

import android.os.Bundle;

import com.githubusers.presentation.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    getActionBar().setTitle(getString(R.string.activity_main));
  }

  @OnClick(R.id.btn_search)
  public void onSearchButtonClicked() {
    this.navigator.navigateToUserDetails(this, "joaovq");
  }
}
