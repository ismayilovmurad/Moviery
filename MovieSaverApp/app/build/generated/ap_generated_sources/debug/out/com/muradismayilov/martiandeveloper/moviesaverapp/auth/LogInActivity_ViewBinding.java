// Generated code from Butter Knife. Do not modify!
package com.muradismayilov.martiandeveloper.moviesaverapp.auth;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LogInActivity_ViewBinding implements Unbinder {
  private LogInActivity target;

  @UiThread
  public LogInActivity_ViewBinding(LogInActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LogInActivity_ViewBinding(LogInActivity target, View source) {
    this.target = target;

    target.forgot_passwordTV = Utils.findRequiredViewAsType(source, R.id.forgot_passwordTV, "field 'forgot_passwordTV'", TextView.class);
    target.sign_upTV = Utils.findRequiredViewAsType(source, R.id.sign_upTV, "field 'sign_upTV'", TextView.class);
    target.mainContainer = Utils.findRequiredViewAsType(source, R.id.mainContainer, "field 'mainContainer'", ConstraintLayout.class);
    target.bottomInnerContainerCL = Utils.findRequiredViewAsType(source, R.id.bottomInnerContainerCL, "field 'bottomInnerContainerCL'", ConstraintLayout.class);
    target.innerContainerLL = Utils.findRequiredViewAsType(source, R.id.innerContainerLL, "field 'innerContainerLL'", LinearLayout.class);
    target.emailET = Utils.findRequiredViewAsType(source, R.id.emailET, "field 'emailET'", AutoCompleteTextView.class);
    target.passwordET = Utils.findRequiredViewAsType(source, R.id.passwordET, "field 'passwordET'", TextInputEditText.class);
    target.log_inBTN = Utils.findRequiredViewAsType(source, R.id.log_inBTN, "field 'log_inBTN'", Button.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.progressBar, "field 'progressBar'", ProgressBar.class);

    Context context = source.getContext();
    Resources res = context.getResources();
    target.fillSTR = res.getString(R.string.fill);
    target.verify_your_emailSTR = res.getString(R.string.verify_your_email);
    target.errorSTR = res.getString(R.string.error);
    target.successSTR = res.getString(R.string.success);
    target.resetSTR = res.getString(R.string.reset);
    target.enterEmailSTR = res.getString(R.string.enter_email);
    target.went_wrongSTR = res.getString(R.string.went_wrong);
    target.no_internet_connectionSTR = res.getString(R.string.no_internet_connection);
  }

  @Override
  @CallSuper
  public void unbind() {
    LogInActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.forgot_passwordTV = null;
    target.sign_upTV = null;
    target.mainContainer = null;
    target.bottomInnerContainerCL = null;
    target.innerContainerLL = null;
    target.emailET = null;
    target.passwordET = null;
    target.log_inBTN = null;
    target.progressBar = null;
  }
}
