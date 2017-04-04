package com.githubusers.data.di.components;

import com.githubusers.data.di.PerRepository;
import com.githubusers.data.di.modules.UserDataModule;
import com.githubusers.data.features.user.UserDataRepository;

import dagger.Component;

/**
 */
@PerRepository
@Component(modules =  UserDataModule.class, dependencies =  DataComponent.class)
public interface UserDataComponent {
  void inject(UserDataRepository userRepository);
}
