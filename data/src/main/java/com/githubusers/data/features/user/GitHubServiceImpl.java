package com.githubusers.data.features.user;

import com.sample.githubusers.domain.executor.ThreadExecutor;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 */
public class GitHubServiceImpl {
  private final Retrofit retrofit;
  private final ThreadExecutor threadExecutor;

  public GitHubServiceImpl(Retrofit retrofit, ThreadExecutor threadExecutor){
    this.retrofit = retrofit;
    this.threadExecutor = threadExecutor;
  }

  public Observable<UserEntity> getUsers(String userId){
    GitHubService service = retrofit.create(GitHubService.class);
    Observable<UserEntity> result = service.getUser(userId);
    result.subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(Schedulers.from(threadExecutor))
            .subscribe(
                    userEntity -> EventBus.getDefault().postSticky(userEntity),
                    throwable -> { }
            );
    return result;
  }
}
