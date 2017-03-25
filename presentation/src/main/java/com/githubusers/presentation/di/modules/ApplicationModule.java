/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.githubusers.presentation.di.modules;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;
import com.githubusers.data.executor.JobExecutor;
import com.githubusers.data.features.user.UserDataRepository;
import com.githubusers.data.utils.network.NetworkInfoUtils;
import com.githubusers.domain.executor.PostExecutionThread;
import com.githubusers.domain.executor.ThreadExecutor;
import com.githubusers.domain.features.user.UserRepository;
import com.githubusers.presentation.AndroidApplication;
import com.githubusers.presentation.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
  private final AndroidApplication application;

  public ApplicationModule(AndroidApplication application) {
    this.application = application;
  }

  @Provides @Singleton
  Context provideApplicationContext() {
    return this.application;
  }

  @Provides @Singleton
  ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides @Singleton
  PostExecutionThread providePostExecutionThread(UIThread uiThread) {
    return uiThread;
  }

  @Provides @Singleton
  UserRepository provideUserRepository(UserDataRepository userDataRepository) {
    return userDataRepository;
  }

  @Provides @Singleton Retrofit providesRetrofitService(){
    Retrofit retrofit = new Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.github.com/")
            .build();

    return retrofit;
  }

  @Provides @Singleton JobManager providesJobManager(){
    Configuration.Builder builder = new Configuration.Builder(application)
            .minConsumerCount(1) // always keep at least one consumer alive
            .maxConsumerCount(3) // up to 3 consumers at a time
            .loadFactor(3) // 3 jobs per consumer
            .consumerKeepAlive(120) ;// wait 2 minute
    return new JobManager(builder.build());
  }

  @Provides @Singleton NetworkInfoUtils providesNetworkInfo(){
    ConnectivityManager connectivityManager =
            (ConnectivityManager)application.getSystemService(Context.CONNECTIVITY_SERVICE);

    NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
    NetworkInfoUtils networkInfoUtils = new NetworkInfoUtils(activeNetwork);
    return networkInfoUtils;
  }
}
