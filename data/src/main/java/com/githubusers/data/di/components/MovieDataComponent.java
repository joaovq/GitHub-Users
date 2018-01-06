package com.githubusers.data.di.components;

import com.githubusers.data.di.PerRepository;
import com.githubusers.data.di.modules.MovieDataModule;
import com.githubusers.data.di.modules.UserDataModule;
import com.githubusers.data.features.movie.MovieDataRepository;
import com.githubusers.data.features.user.UserDataRepository;

import dagger.Component;

/**
 */
@PerRepository
@Component(modules =  MovieDataModule.class, dependencies =  DataComponent.class)
public interface MovieDataComponent {
  void inject(MovieDataRepository movieRepository);
}
