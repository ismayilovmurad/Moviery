// Generated code from Butter Knife. Do not modify!
package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_fragment;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TvTrendingFragment_ViewBinding implements Unbinder {
  private TvTrendingFragment target;

  @UiThread
  public TvTrendingFragment_ViewBinding(TvTrendingFragment target, View source) {
    this.target = target;

    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.mRecyclerView, "field 'mRecyclerView'", RecyclerView.class);
    target.mainPB = Utils.findRequiredViewAsType(source, R.id.mainPB, "field 'mainPB'", ProgressBar.class);
    target.morePB = Utils.findRequiredViewAsType(source, R.id.morePB, "field 'morePB'", ProgressBar.class);
    target.emptyLL = Utils.findRequiredViewAsType(source, R.id.emptyLL, "field 'emptyLL'", LinearLayout.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeRefreshLayout, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    target.backToTopIBTN = Utils.findRequiredViewAsType(source, R.id.backToTopIBTN, "field 'backToTopIBTN'", ImageButton.class);

    Context context = source.getContext();
    Resources res = context.getResources();
    target.errorSTR = res.getString(R.string.error);
    target.went_wrongSTR = res.getString(R.string.went_wrong);
    target.no_internet_connection = res.getString(R.string.no_internet_connection);
    target.interstitial_ad_for_more = res.getString(R.string.interstitial_ad_for_more);
    target.reached = res.getString(R.string.reached);
  }

  @Override
  @CallSuper
  public void unbind() {
    TvTrendingFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecyclerView = null;
    target.mainPB = null;
    target.morePB = null;
    target.emptyLL = null;
    target.swipeRefreshLayout = null;
    target.backToTopIBTN = null;
  }
}
