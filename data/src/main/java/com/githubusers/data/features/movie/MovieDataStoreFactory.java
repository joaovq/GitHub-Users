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
package com.githubusers.data.features.movie;

import javax.inject.Inject;

/**
 * Factory that creates different implementations of {@link MovieDataStore}.
 */
public class MovieDataStoreFactory {

  private final DiskMovieDataStore diskMovieDataStore;
  private final CloudMovieDataStore cloudMovieDataStore;

  @Inject
  public MovieDataStoreFactory(DiskMovieDataStore diskMovieDataStore,
                               CloudMovieDataStore cloudMovieDataStore){
    this.diskMovieDataStore = diskMovieDataStore;
    this.cloudMovieDataStore = cloudMovieDataStore;
  }
  /**
   * Creates a {@link MovieDataStore} from a user id.
   */
  public MovieDataStore create(String title) {
    MovieDataStore dataStore;
    if (DiskMovieDataStore.isCached(title))
        dataStore = diskMovieDataStore;
     else
        dataStore = cloudMovieDataStore;

    return dataStore;
  }

  /**
   * Creates a {@link MovieDataStore for local manipulation
   */
  public MovieDataStore createLocalDataStore() {
    return diskMovieDataStore;
  }

}
