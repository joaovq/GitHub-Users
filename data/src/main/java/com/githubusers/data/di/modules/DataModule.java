package com.githubusers.data.di.modules;

import android.content.Context;

import dagger.Module;

/**
 */
@Module
public class DataModule {
  private final Context context;

  public DataModule(Context context){
    this.context = context;
  }
}
