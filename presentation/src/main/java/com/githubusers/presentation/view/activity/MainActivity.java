package com.githubusers.presentation.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.githubusers.presentation.R;
import com.githubusers.presentation.events.ArgumentEvent;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements Validator.ValidationListener {

  @NotEmpty @Bind(R.id.edit_search) EditText serachEditText;

  private Validator validator;
  private String currentButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    getActionBar().setTitle(getString(R.string.activity_main));

    validator = new Validator(this);
    validator.setValidationListener(this);

  }

  @OnClick(R.id.btn_search_api)
  public void onSearchAPIButtonClicked() {
    currentButton = "API";
    validator.validate();
  }

  @OnClick(R.id.btn_search_lod)
  public void onSearchALODButtonClicked() {
    currentButton = "LOD";
    validator.validate();
  }

  @OnClick(R.id.btn_history)
  public void onHistoryButtonClicked() {
    this.navigator.navigateToUserList(this);
  }

  /**
   * Clear all edit texts
   */
  private void clearEditTexts(){
    serachEditText.getText().clear();
  }

  @Override
  public void onValidationSucceeded() {
    String title = serachEditText.getText().toString();
    this.navigator.navigateToMovieDetails(this);
    EventBus.getDefault().postSticky(new ArgumentEvent(title, currentButton));
    clearEditTexts();

  }

  @Override
  public void onValidationFailed(List<ValidationError> errors) {
    for (ValidationError error : errors) {
      View view = error.getView();
      String message = error.getCollatedErrorMessage(this);

      if (view instanceof EditText) {
        ((EditText) view).setError(message);
      } else {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
      }
    }
  }
}
