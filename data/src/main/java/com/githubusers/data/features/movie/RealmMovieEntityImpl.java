package com.githubusers.data.features.movie;

import com.githubusers.data.features.user.RealmUserEntity;
import com.githubusers.data.features.user.UserEntity;
import com.githubusers.data.features.user.UserEntityDataMapper;

import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Manages user's data in cache
 */
public class RealmMovieEntityImpl {
  private final MovieEntityDataMapper dataMapper;

  public RealmMovieEntityImpl(MovieEntityDataMapper dataMapper){
    this.dataMapper = dataMapper;
  }

  public Observable<MovieEntity> getMovie(String title){
    Realm realm = Realm.getDefaultInstance();
    RealmResults<RealmMovieEntity> result = realm.where(RealmMovieEntity.class)
            .equalTo("Title",title)
            .findAll();
    realm.close();
    return Observable.just(dataMapper.transformToMovieEntity(result.get(0)));
  }

  public void addMovie(MovieEntity movieEntity) {
    Realm realm = Realm.getDefaultInstance();
    realm.beginTransaction();
      RealmMovieEntity realmObject = realm.createObject(RealmMovieEntity.class);
      realmObject.setTitle(movieEntity.getTitle());
      realmObject.setYear(movieEntity.getYear());
    realm.commitTransaction();
    realm.close();
  }

  public static Boolean isCached(String title){
    return Realm.getDefaultInstance().where(RealmMovieEntity.class).equalTo("Title",title).findAll().size() > 0;
  }
}
