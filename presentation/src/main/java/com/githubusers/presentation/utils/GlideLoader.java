package com.githubusers.presentation.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.githubusers.presentation.di.PerActivity;

import javax.inject.Inject;

/**
 * Loads images from URL into an {@link android.widget.ImageView}
 */
@PerActivity
public class GlideLoader {
  private final Context context;

  @Inject
  public GlideLoader(Context context){
    this.context = context;
  }

  public void load(String url, ImageView imageView){
    Glide.with(context)
            .load(url)
            .into(imageView);
  }

  public void load(String url,Integer width,Integer lenght, ImageView imageView){
    Glide.with(context)
            .load(url)
            .override(width,lenght)
            .into(imageView);
  }
}
