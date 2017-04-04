package com.githubusers.data.di.modules;

import com.birbit.android.jobqueue.JobManager;
import com.githubusers.data.di.PerRepository;
import com.githubusers.data.features.user.CloudUserDataStore;
import com.githubusers.data.features.user.DiskUserDataStore;
import com.githubusers.data.features.user.GitHubServiceImpl;
import com.githubusers.data.features.user.RealmUserEntityImpl;
import com.githubusers.data.features.user.UserDataStoreFactory;
import com.githubusers.data.features.user.UserEntityDataMapper;
import com.githubusers.data.utils.network.NetworkInfoUtils;

import dagger.Module;
import dagger.Provides;

/**
 */
@Module
public class UserDataModule {
  public UserDataModule(){}

  @Provides @PerRepository UserEntityDataMapper providesUserEntityDataMapper(){
    return new UserEntityDataMapper();
  }

  @Provides @PerRepository RealmUserEntityImpl providesRealmUserEntityImpl(UserEntityDataMapper userEntityDataMapper){
    return new RealmUserEntityImpl(userEntityDataMapper);
  }

  @Provides @PerRepository DiskUserDataStore providesDiskUserDataStore(RealmUserEntityImpl realmUserEntity){
    return new DiskUserDataStore(realmUserEntity);
  }

  @Provides @PerRepository CloudUserDataStore providesCloudUserDataStore(GitHubServiceImpl gitHubService, JobManager jobManager, NetworkInfoUtils networkInfoUtils){
    return new CloudUserDataStore(gitHubService,jobManager,networkInfoUtils);
  }

  @Provides @PerRepository UserDataStoreFactory providesUserDataStoreFactory(DiskUserDataStore diskUserDataStore,
                                                                             CloudUserDataStore cloudUserDataStore){
    return new UserDataStoreFactory(diskUserDataStore,cloudUserDataStore);
  }
}
