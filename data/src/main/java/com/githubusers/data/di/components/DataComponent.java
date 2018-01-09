package com.githubusers.data.di.components;

import com.birbit.android.jobqueue.JobManager;
import com.githubusers.data.di.modules.DataModule;
import com.githubusers.data.features.movie.MovieOkHttp;
import com.githubusers.data.features.movie.OMDbServiceImpl;
import com.githubusers.data.features.user.GetUsersJob;
import com.githubusers.data.features.user.GitHubServiceImpl;
import com.githubusers.data.utils.network.NetworkInfoUtils;

import javax.inject.Singleton;

import dagger.Component;

/**
 */
@Singleton
@Component(modules = DataModule.class)
public interface DataComponent {
  JobManager jobManager();
  GitHubServiceImpl gitHubService();
  OMDbServiceImpl omdbService();
  NetworkInfoUtils networkInfoUtils();
  MovieOkHttp movieOkHtpp();

  void inject(GetUsersJob job);
}
