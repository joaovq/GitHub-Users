package com.sample.githubusers.domain.interactor;

import com.sample.githubusers.domain.executor.PostExecutionThread;
import com.sample.githubusers.domain.executor.ThreadExecutor;
import com.sample.githubusers.domain.features.user.GetUserDetails;
import com.sample.githubusers.domain.features.user.UserRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class GetUserDetailsTest {
  private static final String USER_NAME = "test";

  private GetUserDetails getUserDetails;

  @Mock UserRepository mockUserRepository;
  @Mock ThreadExecutor mockThreadExecutor;
  @Mock PostExecutionThread mockPostExecutionThread;

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setUp(){
    getUserDetails = new GetUserDetails(mockUserRepository, mockThreadExecutor, mockPostExecutionThread);
  }

  @Test
  public void testGetUserDetailsUseCaseObservableHappyCase() {
    getUserDetails.buildUseCaseObservable(GetUserDetails.Params.forUser(USER_NAME));

    verify(mockUserRepository).user(USER_NAME);
    verifyNoMoreInteractions(mockUserRepository);
    verifyZeroInteractions(mockThreadExecutor);
    verifyZeroInteractions(mockPostExecutionThread);
  }


  @Test
  public void testShouldFailWhenNoOrEmptyParameters() {
    expectedException.expect(NullPointerException.class);
    getUserDetails.buildUseCaseObservable(null);
  }

  }
