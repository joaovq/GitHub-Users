package com.githubusers.data.di.components;

import com.githubusers.data.di.PerRepository;
import com.githubusers.data.di.modules.DataModule;

import dagger.Component;

/**
 */
@PerRepository
@Component(modules = DataModule.class)
public interface DataComponent {
}
