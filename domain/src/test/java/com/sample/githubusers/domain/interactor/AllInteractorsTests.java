package com.sample.githubusers.domain.interactor;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Run all interactors tests
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        GetUserDetailsTest.class,
        GetUserListTest.class,
        UseCaseTest.class
        })
public class AllInteractorsTests {
}
