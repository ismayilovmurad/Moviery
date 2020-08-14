// Generated code from Butter Knife. Do not modify!
package com.muradismayilov.martiandeveloper.moviesaverapp.common.activity;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FeedActivity_ViewBinding implements Unbinder {
  private FeedActivity target;

  @UiThread
  public FeedActivity_ViewBinding(FeedActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public FeedActivity_ViewBinding(FeedActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.drawerLayout = Utils.findRequiredViewAsType(source, R.id.drawerLayout, "field 'drawerLayout'", DrawerLayout.class);
    target.navigationView = Utils.findRequiredViewAsType(source, R.id.navigationView, "field 'navigationView'", NavigationView.class);
    target.themoviedbIV = Utils.findRequiredViewAsType(source, R.id.themoviedbIV, "field 'themoviedbIV'", ImageView.class);
    target.adViewPlaceholderFL = Utils.findRequiredViewAsType(source, R.id.adViewPlaceholderFL, "field 'adViewPlaceholderFL'", FrameLayout.class);
    target.appbar = Utils.findRequiredViewAsType(source, R.id.appbar, "field 'appbar'", AppBarLayout.class);

    Context context = source.getContext();
    Resources res = context.getResources();
    target.movies = res.getString(R.string.movies);
    target.series_n_tv_shows = res.getString(R.string.series_n_tv_shows);
    target.actors = res.getString(R.string.actors);
    target.check_out = res.getString(R.string.check_out);
    target.share_via = res.getString(R.string.share_via);
    target.app_name = res.getString(R.string.app_name);
    target.errorSTR = res.getString(R.string.error);
    target.went_wrongSTR = res.getString(R.string.went_wrong);
    target.banner_ad_for_feed = res.getString(R.string.banner_ad_for_feed);
    target.no_internet_connectionSTR = res.getString(R.string.no_internet_connection);
    target.payment_canceled = res.getString(R.string.payment_canceled);
    target.payment_successful = res.getString(R.string.payment_successful);
    target.already_premium = res.getString(R.string.already_premium);
  }

  @Override
  @CallSuper
  public void unbind() {
    FeedActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.drawerLayout = null;
    target.navigationView = null;
    target.themoviedbIV = null;
    target.adViewPlaceholderFL = null;
    target.appbar = null;
  }
}
