package com.githubusers.data.di.components;

import com.birbit.android.jobqueue.JobManager;
import com.githubusers.data.di.modules.DataModule;
import com.githubusers.data.features.user.GetUsersJob;
import com.githubusers.data.utils.network.NetworkInfoUtils;
import com.sample.githubusers.domain.executor.ThreadExecutor;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 */
@Singleton
@Component(modules = DataModule.class)
public interface DataComponent {
  Retrofit retrofit();
  ThreadExecutor threadExecutor();
  JobManager jobManager();
  NetworkInfoUtils networkInfoUtils();

  void inject(GetUsersJob job);
}
