package com.githubusers.data.features.movie;

import com.sample.githubusers.domain.executor.ThreadExecutor;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 */
public class OMDbServiceImpl {
  private final Retrofit retrofit;
  private final ThreadExecutor threadExecutor;
  private final String apiKey = "f18a62d7";

  public OMDbServiceImpl(Retrofit retrofit, ThreadExecutor threadExecutor){
    this.retrofit = retrofit;
    this.threadExecutor = threadExecutor;
  }

  public Observable<MovieEntity> getMovie(String title){
    OMDbService service = retrofit.create(OMDbService.class);
    Observable<MovieEntity> result = service.getMovie(title, apiKey);
    result.subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(Schedulers.from(threadExecutor))
            .subscribe(
                    movieEntity -> EventBus.getDefault().postSticky(movieEntity),
                    throwable -> {  }
            );
    return result;
  }
}
