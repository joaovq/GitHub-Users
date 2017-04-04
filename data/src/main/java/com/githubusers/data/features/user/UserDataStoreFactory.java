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
package com.githubusers.data.features.user;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Factory that creates different implementations of {@link UserDataStore}.
 */
@Singleton
public class UserDataStoreFactory {

  @Inject public DiskUserDataStore   diskUserDataStore;
  @Inject public CloudUserDataStore  cloudUserDataStore;

  @Inject
  public UserDataStoreFactory() {
  }

  /**
   * Creates a {@link UserDataStore} from a user id.
   */
  public UserDataStore create(String userId) {
    UserDataStore userDataStore;
    if (DiskUserDataStore.isCached(userId))
        userDataStore = diskUserDataStore;
     else
        userDataStore = cloudUserDataStore;

    return userDataStore;
  }

  /**
   * Creates a {@link UserDataStore for local manipulation
   */
  public UserDataStore createLocalDataStore() {
    return diskUserDataStore;
  }

}
