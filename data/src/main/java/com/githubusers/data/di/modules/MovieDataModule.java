package com.githubusers.data.di.modules;

import com.birbit.android.jobqueue.JobManager;
import com.githubusers.data.di.PerRepository;
import com.githubusers.data.features.movie.CloudMovieDataStore;
import com.githubusers.data.features.movie.DiskMovieDataStore;
import com.githubusers.data.features.movie.MovieDataStoreFactory;
import com.githubusers.data.features.movie.MovieEntityDataMapper;
import com.githubusers.data.features.movie.MovieOkHttp;
import com.githubusers.data.features.movie.OMDbServiceImpl;
import com.githubusers.data.features.movie.RealmMovieEntityImpl;
import com.githubusers.data.features.movie.WebExtractor;
import com.githubusers.data.utils.network.NetworkInfoUtils;

import dagger.Module;
import dagger.Provides;

/**
 */
@Module
public class MovieDataModule {
  public MovieDataModule(){}

  @Provides @PerRepository
  WebExtractor providesWebExtractor(){
    return new WebExtractor();
  }

  @Provides @PerRepository
  MovieEntityDataMapper providesMovieEntityDataMapper(){
    return new MovieEntityDataMapper();
  }

  @Provides @PerRepository
  RealmMovieEntityImpl providesRealmMovieEntityImpl(MovieEntityDataMapper dataMapper){
    return new RealmMovieEntityImpl(dataMapper);
  }

  @Provides @PerRepository
  DiskMovieDataStore providesDiskMovieDataStore(RealmMovieEntityImpl realmImpl){
    return new DiskMovieDataStore(realmImpl);
  }

  @Provides @PerRepository
  CloudMovieDataStore providesCloudMovieDataStore(OMDbServiceImpl omDbService, WebExtractor webExtractor, MovieOkHttp movieOkHttp, JobManager jobManager, NetworkInfoUtils networkInfoUtils){
    return new CloudMovieDataStore(omDbService,movieOkHttp, webExtractor, jobManager,networkInfoUtils);
  }

  @Provides @PerRepository
  MovieDataStoreFactory providesMovieDataStoreFactory(DiskMovieDataStore diskMovieDataStore,
                                                      CloudMovieDataStore cloudMovieDataStore){
    return new MovieDataStoreFactory(diskMovieDataStore,cloudMovieDataStore);
  }
}
