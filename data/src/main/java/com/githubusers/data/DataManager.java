package com.githubusers.data;

import android.content.Context;

import com.githubusers.data.di.components.DaggerDataComponent;
import com.githubusers.data.di.components.DataComponent;
import com.githubusers.data.di.modules.DataModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 *
 */
public class DataManager {
  private static DataComponent dataComponent;
  private Context context;

  public DataManager(Context context){
    this.context = context;

    initializeInjector();
    initializaRealm();
  }

  private void initializaRealm(){
    Realm.init(context);
    RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build();
    Realm.setDefaultConfiguration(realmConfiguration);
  }

  public void initializeInjector(){
    this.dataComponent = DaggerDataComponent.builder()
            .dataModule(new DataModule(context))
            .build();
  }

  public static DataComponent getComponent(){
    return dataComponent;
  }
}
