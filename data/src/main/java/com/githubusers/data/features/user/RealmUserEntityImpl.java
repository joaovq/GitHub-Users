package com.githubusers.data.features.user;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Manages user's data in cache
 */
public class RealmUserEntityImpl {
  private final UserEntityDataMapper userEntityDataMapper;

  public RealmUserEntityImpl(UserEntityDataMapper userEntityDataMapper){
    this.userEntityDataMapper = userEntityDataMapper;
  }

  public Observable<List<UserEntity>> getUsers(){
    Realm realm = Realm.getDefaultInstance();
    RealmResults<RealmUserEntity> result = realm.where(RealmUserEntity.class)
            .findAll();
    List<UserEntity> userEntities = userEntityDataMapper.transformToUserEntity(result);
    realm.close();
    return Observable.just(userEntities);
  }

  public Observable<UserEntity> getUser(String userId){
    Realm realm = Realm.getDefaultInstance();
    RealmResults<RealmUserEntity> result = realm.where(RealmUserEntity.class)
            .equalTo("login",userId)
            .findAll();
    realm.close();
    return Observable.just(userEntityDataMapper.transformToUserEntity(result.get(0)));
  }

  public void addUser(UserEntity userEntity) {
    Realm realm = Realm.getDefaultInstance();
    realm.beginTransaction();
    RealmUserEntity realmObject = realm.createObject(RealmUserEntity.class);
    realmObject.setLogin(userEntity.getLogin());
    realmObject.setAvatarUrl(userEntity.getAvatarUrl());
    realmObject.setFollowers(userEntity.getFollowers());
    realmObject.setFollowing(userEntity.getFollowing());
    realmObject.setPublic_repos(userEntity.getPublicRepos());
    realm.commitTransaction();
    realm.close();
  }

  public static Boolean isCached(String userId){
    return Realm.getDefaultInstance().where(RealmUserEntity.class).equalTo("login",userId).findAll().size() > 0;
  }
}
