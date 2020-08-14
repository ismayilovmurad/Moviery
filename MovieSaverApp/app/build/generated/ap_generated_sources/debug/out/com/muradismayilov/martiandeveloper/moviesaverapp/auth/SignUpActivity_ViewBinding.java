// Generated code from Butter Knife. Do not modify!
package com.muradismayilov.martiandeveloper.moviesaverapp.auth;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
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

public class SignUpActivity_ViewBinding implements Unbinder {
  private SignUpActivity target;

  @UiThread
  public SignUpActivity_ViewBinding(SignUpActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SignUpActivity_ViewBinding(SignUpActivity target, View source) {
    this.target = target;

    target.log_inTV = Utils.findRequiredViewAsType(source, R.id.log_inTV, "field 'log_inTV'", TextView.class);
    target.mainContainer = Utils.findRequiredViewAsType(source, R.id.mainContainer, "field 'mainContainer'", ConstraintLayout.class);
    target.bottomInnerContainerCL = Utils.findRequiredViewAsType(source, R.id.bottomInnerContainerCL, "field 'bottomInnerContainerCL'", ConstraintLayout.class);
    target.innerContainerLL = Utils.findRequiredViewAsType(source, R.id.innerContainerLL, "field 'innerContainerLL'", LinearLayout.class);
    target.nameET = Utils.findRequiredViewAsType(source, R.id.nameET, "field 'nameET'", TextInputEditText.class);
    target.emailET = Utils.findRequiredViewAsType(source, R.id.emailET, "field 'emailET'", TextInputEditText.class);
    target.passwordET = Utils.findRequiredViewAsType(source, R.id.passwordET, "field 'passwordET'", TextInputEditText.class);
    target.sign_upBTN = Utils.findRequiredViewAsType(source, R.id.sign_upBTN, "field 'sign_upBTN'", Button.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.progressBar, "field 'progressBar'", ProgressBar.class);

    Context context = source.getContext();
    Resources res = context.getResources();
    target.fillSTR = res.getString(R.string.fill);
    target.errorSTR = res.getString(R.string.error);
    target.successSTR = res.getString(R.string.success);
    target.went_wrongSTR = res.getString(R.string.went_wrong);
    target.verifySTR = res.getString(R.string.verify);
    target.long_name = res.getString(R.string.long_name);
    target.no_internet_connectionSTR = res.getString(R.string.no_internet_connection);
  }

  @Override
  @CallSuper
  public void unbind() {
    SignUpActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.log_inTV = null;
    target.mainContainer = null;
    target.bottomInnerContainerCL = null;
    target.innerContainerLL = null;
    target.nameET = null;
    target.emailET = null;
    target.passwordET = null;
    target.sign_upBTN = null;
    target.progressBar = null;
  }
}
