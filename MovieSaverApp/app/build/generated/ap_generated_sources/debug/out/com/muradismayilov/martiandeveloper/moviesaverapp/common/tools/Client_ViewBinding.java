// Generated code from Butter Knife. Do not modify!
package com.muradismayilov.martiandeveloper.moviesaverapp.common.tools;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import java.lang.Deprecated;
import java.lang.Override;

public class Client_ViewBinding implements Unbinder {
  /**
   * @deprecated Use {@link #Client_ViewBinding(Client, Context)} for direct creation.
   *     Only present for runtime invocation through {@code ButterKnife.bind()}.
   */
  @Deprecated
  @UiThread
  public Client_ViewBinding(Client target, View source) {
    this(target, source.getContext());
  }

  @UiThread
  public Client_ViewBinding(Client target, Context context) {
    Resources res = context.getResources();
    target.BASE_URL = res.getString(R.string.base_url);
  }

  @Override
  @CallSuper
  public void unbind() {
  }
}
