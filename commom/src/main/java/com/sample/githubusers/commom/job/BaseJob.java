package com.sample.githubusers.commom.job;


import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;

/**
 * Class used to have acess to DI inside a job
 */
public abstract class BaseJob extends Job {
  protected BaseJob(Params params) {
    super(params);
  }

  public void inject(ApplicationComponent appComponent) {

  }
}
