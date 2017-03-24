package com.githubusers.data.features.user;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Retrofit interface that communicates with GitHub REST API
 */
public interface GitHubService {
  @GET("users/{id}")
  Observable<UserEntity> getUser(@Path("id") String userId);
}
