package com.githubusers.data.features.user;

import android.support.annotation.Nullable;
import android.util.Log;

import com.birbit.android.jobqueue.CancelReason;
import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

/**
 * Gets user from REST API when internet connection is back
 */
public class GetUsersJob extends Job {
  public static final int PRIORITY = 1;
  private final String userId;

  public GetUsersJob(String userId) {
    super(new Params(PRIORITY).requireNetwork().persist());
    this.userId = userId;
  }

  @Override
  public void onAdded() { }

  @Override
  public void onRun() throws Throwable {
    Log.e(GetUsersJob.class.getCanonicalName(),"Job will be done");
  }

  @Override
  protected RetryConstraint shouldReRunOnThrowable(Throwable throwable, int runCount,
                                                   int maxRunCount) {
    return RetryConstraint.createExponentialBackoff(runCount, 1000);
  }
  @Override
  protected void onCancel(@CancelReason int cancelReason, @Nullable Throwable throwable) { }
}
