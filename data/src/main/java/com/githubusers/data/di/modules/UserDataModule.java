package com.githubusers.data.di.modules;

import com.birbit.android.jobqueue.JobManager;
import com.githubusers.data.features.user.CloudUserDataStore;
import com.githubusers.data.features.user.DiskUserDataStore;
import com.githubusers.data.features.user.GitHubServiceImpl;
import com.githubusers.data.features.user.RealmUserEntityImpl;
import com.githubusers.data.features.user.UserDataStoreFactory;
import com.githubusers.data.features.user.UserEntityDataMapper;
import com.githubusers.data.utils.network.NetworkInfoUtils;
import com.sample.githubusers.domain.executor.ThreadExecutor;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 */
@Module
public class UserDataModule {
  @Provides UserEntityDataMapper providesUserEntityDataMapper(){
    return new UserEntityDataMapper();
  }

  @Provides RealmUserEntityImpl providesRealmUserEntityImpl(UserEntityDataMapper userEntityDataMapper){
    return new RealmUserEntityImpl(userEntityDataMapper);
  }

  @Provides DiskUserDataStore providesDiskUserDataStore(RealmUserEntityImpl realmUserEntity){
    return new DiskUserDataStore(realmUserEntity);
  }

  @Provides GitHubServiceImpl providesGitHubServiceImpl(Retrofit retrofit, ThreadExecutor threadExecutor){
    return new GitHubServiceImpl(retrofit,threadExecutor);
  }

  @Provides
  CloudUserDataStore providesCloudUserDataStore(GitHubServiceImpl gitHubService, JobManager jobManager, NetworkInfoUtils networkInfoUtils){
    return new CloudUserDataStore(gitHubService,jobManager,networkInfoUtils);
  }
}
