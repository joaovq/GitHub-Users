package com.githubusers.data.di.modules;

import com.birbit.android.jobqueue.JobManager;
import com.githubusers.data.di.PerRepository;
import com.githubusers.data.features.movie.CloudMovieDataStore;
import com.githubusers.data.features.movie.DiskMovieDataStore;
import com.githubusers.data.features.movie.MovieDataStoreFactory;
import com.githubusers.data.features.movie.MovieEntityDataMapper;
import com.githubusers.data.features.movie.MovieJoiner;
import com.githubusers.data.features.movie.MovieOkHttp;
import com.githubusers.data.features.movie.MovieWebExtractor;
import com.githubusers.data.features.movie.OMDbServiceImpl;
import com.githubusers.data.features.movie.RealmMovieEntityImpl;
import com.githubusers.data.utils.network.NetworkInfoUtils;

import dagger.Module;
import dagger.Provides;

/**
 */
@Module
public class MovieDataModule {
  public MovieDataModule(){}

  @Provides @PerRepository
  MovieWebExtractor providesWebExtractor(){
    return new MovieWebExtractor();
  }

  @Provides @PerRepository
  MovieJoiner providesMovieJoiner(){
    return new MovieJoiner();
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
  CloudMovieDataStore providesCloudMovieDataStore(OMDbServiceImpl omDbService, MovieWebExtractor movieWebExtractor, MovieJoiner movieJoiner, MovieOkHttp movieOkHttp, JobManager jobManager, NetworkInfoUtils networkInfoUtils){
    return new CloudMovieDataStore(omDbService,movieOkHttp, movieWebExtractor, jobManager,networkInfoUtils, movieJoiner);
  }

  @Provides @PerRepository
  MovieDataStoreFactory providesMovieDataStoreFactory(DiskMovieDataStore diskMovieDataStore,
                                                      CloudMovieDataStore cloudMovieDataStore){
    return new MovieDataStoreFactory(diskMovieDataStore,cloudMovieDataStore);
  }
}
