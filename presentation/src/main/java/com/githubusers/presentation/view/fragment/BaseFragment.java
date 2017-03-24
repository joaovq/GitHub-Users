/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.githubusers.presentation.view.fragment;

import android.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.githubusers.presentation.di.HasComponent;
import com.mobsandgeeks.saripaar.ValidationError;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Base {@link android.app.Fragment} class for every fragment in this application.
 */
public abstract class BaseFragment extends Fragment {
  /**
   * Shows a {@link android.widget.Toast} message.
   *
   * @param message An string representing a message to be shown.
   */
  protected void showToastMessage(String message) {
    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
  }

  /**
   * Gets a component for dependency injection by its type.
   */
  @SuppressWarnings("unchecked")
  protected <C> C getComponent(Class<C> componentType) {
    return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
  }

  protected void showValidationErros(List<ValidationError> errors){
    for (ValidationError error : errors) {
      View view = error.getView();
      String message = error.getCollatedErrorMessage(getActivity());

      if (view instanceof EditText) {
        EditText editText = (EditText) view;
        if(!editText.hasFocus()){
          editText.requestFocus();
          editText.setError(message);
        }
      }
      else
        showToastMessage(message);
    }
  }
}
