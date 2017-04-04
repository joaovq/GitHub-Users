package com.githubusers.data.di.components;

import com.githubusers.data.di.PerRepository;
import com.githubusers.data.di.modules.UserDataModule;
import com.githubusers.data.features.user.GetUsersJob;
import com.githubusers.data.features.user.UserDataStoreFactory;

import dagger.Component;

/**
 */
@PerRepository
@Component(dependencies =  DataComponent.class, modules =  {UserDataModule.class})
public interface UserDataComponent {
  void inject(GetUsersJob getUsersJob);
  void inject(UserDataStoreFactory userDataStoreFactory);
}
