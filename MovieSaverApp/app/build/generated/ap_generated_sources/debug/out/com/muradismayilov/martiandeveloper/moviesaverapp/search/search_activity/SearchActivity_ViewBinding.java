// Generated code from Butter Knife. Do not modify!
package com.muradismayilov.martiandeveloper.moviesaverapp.search.search_activity;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SearchActivity_ViewBinding implements Unbinder {
  private SearchActivity target;

  @UiThread
  public SearchActivity_ViewBinding(SearchActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SearchActivity_ViewBinding(SearchActivity target, View source) {
    this.target = target;

    target.searchRV = Utils.findRequiredViewAsType(source, R.id.searchRV, "field 'searchRV'", RecyclerView.class);
    target.floatingSearchView = Utils.findRequiredViewAsType(source, R.id.floatingSearchView, "field 'floatingSearchView'", FloatingSearchView.class);
    target.emptyLL = Utils.findRequiredViewAsType(source, R.id.emptyLL, "field 'emptyLL'", LinearLayout.class);
    target.searchMainPB = Utils.findRequiredViewAsType(source, R.id.searchMainPB, "field 'searchMainPB'", ProgressBar.class);
    target.searchMorePB = Utils.findRequiredViewAsType(source, R.id.searchMorePB, "field 'searchMorePB'", ProgressBar.class);
    target.searchMovieRB = Utils.findRequiredViewAsType(source, R.id.searchMovieRB, "field 'searchMovieRB'", RadioButton.class);
    target.searchTvRB = Utils.findRequiredViewAsType(source, R.id.searchTvRB, "field 'searchTvRB'", RadioButton.class);
    target.searchActorRB = Utils.findRequiredViewAsType(source, R.id.searchActorRB, "field 'searchActorRB'", RadioButton.class);
    target.searchRG = Utils.findRequiredViewAsType(source, R.id.searchRG, "field 'searchRG'", RadioGroup.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeRefreshLayout, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    target.adViewPlaceholderFL = Utils.findRequiredViewAsType(source, R.id.adViewPlaceholderFL, "field 'adViewPlaceholderFL'", FrameLayout.class);

    Context context = source.getContext();
    Resources res = context.getResources();
    target.errorSTR = res.getString(R.string.error);
    target.went_wrongSTR = res.getString(R.string.went_wrong);
    target.no_internet_connection = res.getString(R.string.no_internet_connection);
    target.banner_ad_for_search = res.getString(R.string.banner_ad_for_search);
    target.interstitial_ad_for_more = res.getString(R.string.interstitial_ad_for_more);
  }

  @Override
  @CallSuper
  public void unbind() {
    SearchActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.searchRV = null;
    target.floatingSearchView = null;
    target.emptyLL = null;
    target.searchMainPB = null;
    target.searchMorePB = null;
    target.searchMovieRB = null;
    target.searchTvRB = null;
    target.searchActorRB = null;
    target.searchRG = null;
    target.swipeRefreshLayout = null;
    target.adViewPlaceholderFL = null;
  }
}
