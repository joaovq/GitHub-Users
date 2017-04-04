/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.githubusers.presentation.di.modules;

import com.githubusers.presentation.di.PerActivity;
import com.sample.githubusers.domain.executor.PostExecutionThread;
import com.sample.githubusers.domain.executor.ThreadExecutor;
import com.sample.githubusers.domain.features.UseCase;
import com.sample.githubusers.domain.features.user.GetUserDetails;
import com.sample.githubusers.domain.features.user.GetUserList;
import com.sample.githubusers.domain.features.user.UserRepository;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides user related collaborators.
 */
@Module
public class UserModule {

  public UserModule() {}

  @Provides @PerActivity @Named(GetUserDetails.NAME)
  UseCase provideGetUserDetailsUseCase(
          UserRepository userRepository,
          ThreadExecutor threadExecutor,
          PostExecutionThread postExecutionThread) {
    return new GetUserDetails(userRepository, threadExecutor, postExecutionThread);
  }

  @Provides @PerActivity @Named(GetUserList.NAME) UseCase provideGetUserListUseCase(
          UserRepository userRepository,
          ThreadExecutor threadExecutor,
          PostExecutionThread postExecutionThread) {
    return new GetUserList(userRepository, threadExecutor, postExecutionThread);
  }
}
