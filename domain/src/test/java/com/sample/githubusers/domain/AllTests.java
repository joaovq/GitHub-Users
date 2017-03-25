package com.sample.githubusers.domain;

import com.sample.githubusers.domain.exception.DefaultErrorBundleTest;
import com.sample.githubusers.domain.interactor.AllInteractorsTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Runs all tests in this layer
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        DefaultErrorBundleTest.class,
        AllInteractorsTests.class
})
public class AllTests {
}
