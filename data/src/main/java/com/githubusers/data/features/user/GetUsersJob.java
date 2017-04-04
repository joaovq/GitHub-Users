package com.githubusers.data.features.user;

import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.CancelReason;
import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.githubusers.data.DataManager;
import com.githubusers.data.di.components.DataComponent;
import com.githubusers.data.di.components.UserDataComponent;
import com.githubusers.data.utils.job.BaseJob;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Gets user from REST API when internet connection is back
 */
public class GetUsersJob extends BaseJob {

  private final String userId;
  private static final String GROUP = "get_user";

  @Inject public GitHubServiceImpl gitHubService;

  public GetUsersJob(String userId) {
    super(new Params(BACKGROUND).groupBy(GROUP).requireNetwork().persist());
    this.userId = userId;
  }

  @Override
  public void onAdded() { }

  @Override
  public void onRun() throws Throwable {
    gitHubService.getUsers(userId);
  }

  @Override
  protected RetryConstraint shouldReRunOnThrowable(Throwable throwable, int runCount,
                                                   int maxRunCount) {
    return RetryConstraint.createExponentialBackoff(runCount, 1000);
  }
  @Override
  protected void onCancel(@CancelReason int cancelReason, @Nullable Throwable throwable) { }

  @Override
  public void inject(DataComponent dataComponent) {
    super.inject(dataComponent);
    dataComponent.inject(this);
  }
}
