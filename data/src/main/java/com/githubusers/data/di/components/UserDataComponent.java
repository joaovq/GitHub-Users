package com.githubusers.data.di.components;

import com.githubusers.data.di.PerRepository;
import com.githubusers.data.di.modules.UserDataModule;
import com.githubusers.data.features.user.CloudUserDataStore;
import com.githubusers.data.features.user.DiskUserDataStore;
import com.githubusers.data.features.user.RealmUserEntityImpl;
import com.githubusers.data.features.user.UserDataRepository;
import com.githubusers.data.features.user.UserEntityDataMapper;

import dagger.Component;

/**
 */
@PerRepository
@Component(modules =  UserDataModule.class, dependencies =  DataComponent.class)
public interface UserDataComponent {
  UserEntityDataMapper userEntityDataMapper();
  RealmUserEntityImpl realmUserEntityImpl();
  DiskUserDataStore diskUserDataStore();
  CloudUserDataStore cloudUserDataStore();

  void inject(UserDataRepository userRepository);
}
