// Generated code from Butter Knife. Do not modify!
package com.muradismayilov.martiandeveloper.moviesaverapp.common.activity;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SplashActivity_ViewBinding implements Unbinder {
  private SplashActivity target;

  @UiThread
  public SplashActivity_ViewBinding(SplashActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SplashActivity_ViewBinding(SplashActivity target, View source) {
    this.target = target;

    target.movieryIV = Utils.findRequiredViewAsType(source, R.id.movieryIV, "field 'movieryIV'", ImageView.class);
    target.loadingTV = Utils.findRequiredViewAsType(source, R.id.loadingTV, "field 'loadingTV'", TextView.class);

    Context context = source.getContext();
    Resources res = context.getResources();
    target.loading = res.getString(R.string.loading);
    target.checking_for_credentials = res.getString(R.string.checking_for_credentials);
    target.logging_you_in = res.getString(R.string.logging_you_in);
  }

  @Override
  @CallSuper
  public void unbind() {
    SplashActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.movieryIV = null;
    target.loadingTV = null;
  }
}
