package com.githubusers.data.features;

import android.net.NetworkInfo;

import javax.inject.Inject;

/**
 * Base Factory used to implement Data Store's
 */
public class BaseFactory {
  @Inject
  NetworkInfo networkInfo;
  protected Boolean isConnected(){
    Boolean isConnected = networkInfo != null &&
            networkInfo.isConnectedOrConnecting();
    return isConnected;
  }
}
