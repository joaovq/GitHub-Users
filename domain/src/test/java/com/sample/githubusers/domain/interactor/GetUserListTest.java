package com.sample.githubusers.domain.interactor;

import com.sample.githubusers.domain.executor.PostExecutionThread;
import com.sample.githubusers.domain.executor.ThreadExecutor;
import com.sample.githubusers.domain.features.user.GetUserDetails;
import com.sample.githubusers.domain.features.user.GetUserList;
import com.sample.githubusers.domain.features.user.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class GetUserListTest {
  private GetUserList getUserList;

  @Mock UserRepository      mockUserRepository;
  @Mock ThreadExecutor      mockThreadExecutor;
  @Mock PostExecutionThread mockPostExecutionThread;

  @Before
  public void setUp(){
    getUserList = new GetUserList(mockUserRepository, mockThreadExecutor, mockPostExecutionThread);
  }

  @Test
  public void testGetUserListUseCaseObservableHappyCase(){
      getUserList.buildUseCaseObservable(null);

      verify(mockUserRepository).users();
      verifyNoMoreInteractions(mockUserRepository);
      verifyZeroInteractions(mockPostExecutionThread);
      verifyZeroInteractions(mockThreadExecutor);
  }
}
