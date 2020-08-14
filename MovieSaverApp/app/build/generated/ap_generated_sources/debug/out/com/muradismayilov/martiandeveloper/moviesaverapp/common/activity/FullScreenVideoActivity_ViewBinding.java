// Generated code from Butter Knife. Do not modify!
package com.muradismayilov.martiandeveloper.moviesaverapp.common.activity;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FullScreenVideoActivity_ViewBinding implements Unbinder {
  private FullScreenVideoActivity target;

  @UiThread
  public FullScreenVideoActivity_ViewBinding(FullScreenVideoActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public FullScreenVideoActivity_ViewBinding(FullScreenVideoActivity target, View source) {
    this.target = target;

    target.youTubePlayerView = Utils.findRequiredViewAsType(source, R.id.youTubePlayerView, "field 'youTubePlayerView'", YouTubePlayerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FullScreenVideoActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.youTubePlayerView = null;
  }
}
