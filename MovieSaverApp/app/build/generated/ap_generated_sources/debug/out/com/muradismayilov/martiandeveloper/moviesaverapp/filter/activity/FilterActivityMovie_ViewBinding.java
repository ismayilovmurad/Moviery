// Generated code from Butter Knife. Do not modify!
package com.muradismayilov.martiandeveloper.moviesaverapp.filter.activity;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class FilterActivityMovie_ViewBinding implements Unbinder {
  private FilterActivityMovie target;

  @UiThread
  public FilterActivityMovie_ViewBinding(FilterActivityMovie target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public FilterActivityMovie_ViewBinding(FilterActivityMovie target, View source) {
    this.target = target;

    target.filterRV = Utils.findRequiredViewAsType(source, R.id.filterRV, "field 'filterRV'", RecyclerView.class);
    target.filterToolbar = Utils.findRequiredViewAsType(source, R.id.filterToolbar, "field 'filterToolbar'", Toolbar.class);
    target.emptyLL = Utils.findRequiredViewAsType(source, R.id.emptyLL, "field 'emptyLL'", LinearLayout.class);
    target.filterMainPB = Utils.findRequiredViewAsType(source, R.id.filterMainPB, "field 'filterMainPB'", ProgressBar.class);
    target.filterMorePB = Utils.findRequiredViewAsType(source, R.id.filterMorePB, "field 'filterMorePB'", ProgressBar.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeRefreshLayout, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    target.adViewPlaceholderFL = Utils.findRequiredViewAsType(source, R.id.adViewPlaceholderFL, "field 'adViewPlaceholderFL'", FrameLayout.class);
    target.filterYearTV = Utils.findRequiredViewAsType(source, R.id.filterYearTV, "field 'filterYearTV'", TextView.class);
    target.filterTypeTV = Utils.findRequiredViewAsType(source, R.id.filterTypeTV, "field 'filterTypeTV'", TextView.class);
    target.filterGenresTV = Utils.findRequiredViewAsType(source, R.id.filterGenresTV, "field 'filterGenresTV'", TextView.class);

    Context context = source.getContext();
    Resources res = context.getResources();
    target.filter_result = res.getString(R.string.filter_result);
    target.errorSTR = res.getString(R.string.error);
    target.went_wrongSTR = res.getString(R.string.went_wrong);
    target.no_internet_connection = res.getString(R.string.no_internet_connection);
    target.banner_ad_for_filter = res.getString(R.string.banner_ad_for_filter);
    target.popularity = res.getString(R.string.popularity);
    target.highest_rated = res.getString(R.string.highest_rated);
    target.none = res.getString(R.string.none);
    target.interstitial_ad_for_more = res.getString(R.string.interstitial_ad_for_more);
  }

  @Override
  @CallSuper
  public void unbind() {
    FilterActivityMovie target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.filterRV = null;
    target.filterToolbar = null;
    target.emptyLL = null;
    target.filterMainPB = null;
    target.filterMorePB = null;
    target.swipeRefreshLayout = null;
    target.adViewPlaceholderFL = null;
    target.filterYearTV = null;
    target.filterTypeTV = null;
    target.filterGenresTV = null;
  }
}
