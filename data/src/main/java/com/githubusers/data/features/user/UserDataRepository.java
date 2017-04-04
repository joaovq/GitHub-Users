/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.githubusers.data.features.user;


import com.githubusers.data.DataManager;
import com.githubusers.data.di.components.DaggerUserDataComponent;
import com.githubusers.data.di.components.DataComponent;
import com.githubusers.data.di.components.UserDataComponent;
import com.githubusers.data.di.modules.UserDataModule;
import com.sample.githubusers.domain.features.user.User;
import com.sample.githubusers.domain.features.user.UserRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * {@link UserRepository} for retrieving user data.
 */
@Singleton
public class UserDataRepository implements UserRepository {

  private final UserDataStoreFactory userDataStoreFactory;
  private final UserEntityDataMapper userEntityDataMapper;

  private static UserDataComponent userDataComponent;

  /**
   * Constructs a {@link UserRepository}.
   *
   */
  @Inject
  UserDataRepository() {
    this.userDataStoreFactory = new UserDataStoreFactory();
    this.userEntityDataMapper = new UserEntityDataMapper();
    initializeInjector();
  }

  @Override
  public Observable<List<User>> users() {
    final UserDataStore userDataStore = this.userDataStoreFactory.createLocalDataStore();
    return userDataStore.userEntityList().map(this.userEntityDataMapper::transformToUser);
  }

  @Override
  public Observable<User> user(String userId) {
    final UserDataStore userDataStore = this.userDataStoreFactory.create(userId);
    return userDataStore.userEntityDetails(userId).map(this.userEntityDataMapper::transformToUser);
  }

  private void initializeInjector(){
    this.userDataComponent = DaggerUserDataComponent.builder()
            .userDataModule(new UserDataModule())
            .dataComponent(DataManager.getComponent())
            .build();
    this.userDataComponent.inject(userDataStoreFactory);
  }

  public static UserDataComponent getUserDataComponent(){
    return userDataComponent;
  }
}
