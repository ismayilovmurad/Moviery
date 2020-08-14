// Generated code from Butter Knife. Do not modify!
package com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_activity;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ProfileActivityTv_ViewBinding implements Unbinder {
  private ProfileActivityTv target;

  @UiThread
  public ProfileActivityTv_ViewBinding(ProfileActivityTv target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ProfileActivityTv_ViewBinding(ProfileActivityTv target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.mRecyclerView, "field 'mRecyclerView'", RecyclerView.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeRefreshLayout, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    target.adViewPlaceholderFL = Utils.findRequiredViewAsType(source, R.id.adViewPlaceholderFL, "field 'adViewPlaceholderFL'", FrameLayout.class);

    Context context = source.getContext();
    Resources res = context.getResources();
    target.no_internet_connectionSTR = res.getString(R.string.no_internet_connection);
    target.errorSTR = res.getString(R.string.error);
    target.went_wrongSTR = res.getString(R.string.went_wrong);
    target.banner_ad_for_my_profile_expand = res.getString(R.string.banner_ad_for_my_profile_expand);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProfileActivityTv target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.mRecyclerView = null;
    target.swipeRefreshLayout = null;
    target.adViewPlaceholderFL = null;
  }
}
