package com.githubusers.data.di.modules;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;
import com.birbit.android.jobqueue.di.DependencyInjector;
import com.githubusers.data.DataManager;
import com.githubusers.data.executor.JobExecutor;
import com.githubusers.data.features.movie.OMDbServiceImpl;
import com.githubusers.data.features.user.GitHubServiceImpl;
import com.githubusers.data.utils.job.BaseJob;
import com.githubusers.data.utils.network.NetworkInfoUtils;
import com.sample.githubusers.domain.executor.ThreadExecutor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 */
@Module
public class DataModule {
  private final Context context;

  public DataModule(Context context){
    this.context = context;
  }

  @Provides @Singleton
  Retrofit providesRetrofitService(){
    Retrofit retrofit = new Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl("https://api.github.com/")
            .baseUrl("http://www.omdbapi.com")
            .build();

    return retrofit;
  }

  @Provides @Singleton
  JobManager providesJobManager(){
    Configuration.Builder builder = new Configuration.Builder(context)
            .minConsumerCount(1) // always keep at least one consumer alive
            .maxConsumerCount(3) // up to 3 consumers at a time
            .loadFactor(3) // 3 jobs per consumer
            .injector(job -> {
              if (job instanceof BaseJob) {
                ((BaseJob) job).inject(DataManager.getComponent());
              }
            })
            .consumerKeepAlive(120) ;// wait 2 minute
    return new JobManager(builder.build());
  }

  @Provides @Singleton
  ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides @Singleton
  NetworkInfoUtils providesNetworkInfo(){
    ConnectivityManager connectivityManager =
            (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

    NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
    NetworkInfoUtils networkInfoUtils = new NetworkInfoUtils(activeNetwork);
    return networkInfoUtils;
  }

  @Provides @Singleton
  GitHubServiceImpl providesGitHubServiceImpl(Retrofit retrofit, ThreadExecutor threadExecutor){
    return new GitHubServiceImpl(retrofit,threadExecutor);
  }

  @Provides @Singleton
  OMDbServiceImpl providesOMDbServiceImpl(Retrofit retrofit, ThreadExecutor threadExecutor){
    return new OMDbServiceImpl(retrofit,threadExecutor);
  }
}
