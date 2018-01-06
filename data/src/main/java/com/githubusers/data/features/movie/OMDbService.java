package com.githubusers.data.features.movie;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit interface that communicates with OMDb REST API
 */
public interface OMDbService {
  @GET("/")
  Observable<MovieEntity> getMovie(@Query("t") String title, @Query("api_key") String apiKey);
}
