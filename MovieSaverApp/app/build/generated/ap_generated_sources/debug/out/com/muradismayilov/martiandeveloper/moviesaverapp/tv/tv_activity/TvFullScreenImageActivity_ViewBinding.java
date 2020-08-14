// Generated code from Butter Knife. Do not modify!
package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_activity;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.smarteist.autoimageslider.SliderView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TvFullScreenImageActivity_ViewBinding implements Unbinder {
  private TvFullScreenImageActivity target;

  @UiThread
  public TvFullScreenImageActivity_ViewBinding(TvFullScreenImageActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public TvFullScreenImageActivity_ViewBinding(TvFullScreenImageActivity target, View source) {
    this.target = target;

    target.enlarged_picturesSV = Utils.findRequiredViewAsType(source, R.id.enlarged_picturesSV, "field 'enlarged_picturesSV'", SliderView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TvFullScreenImageActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.enlarged_picturesSV = null;
  }
}
