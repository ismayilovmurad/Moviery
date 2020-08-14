// Generated code from Butter Knife. Do not modify!
package com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_activity;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.smarteist.autoimageslider.SliderView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MovieFullScreenImageActivity_ViewBinding implements Unbinder {
  private MovieFullScreenImageActivity target;

  @UiThread
  public MovieFullScreenImageActivity_ViewBinding(MovieFullScreenImageActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MovieFullScreenImageActivity_ViewBinding(MovieFullScreenImageActivity target,
      View source) {
    this.target = target;

    target.enlarged_picturesSV = Utils.findRequiredViewAsType(source, R.id.enlarged_picturesSV, "field 'enlarged_picturesSV'", SliderView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MovieFullScreenImageActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.enlarged_picturesSV = null;
  }
}
