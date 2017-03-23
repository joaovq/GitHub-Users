package com.githubusers.data.utils.network;

import android.net.NetworkInfo;

/**
 * Gives information about network connection
 */
public class NetworkInfoUtils {
  private NetworkInfo networkInfo;

  public NetworkInfoUtils(NetworkInfo networkInfo){
    this.networkInfo = networkInfo;
  }

  public Boolean isConnected(){
    Boolean isConnected = networkInfo != null &&
            networkInfo.isConnectedOrConnecting();
    return isConnected;
  }
}
