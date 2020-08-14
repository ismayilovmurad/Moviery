// Generated code from Butter Knife. Do not modify!
package com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_fragment;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.Button;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MovieFragment_ViewBinding implements Unbinder {
  private MovieFragment target;

  @UiThread
  public MovieFragment_ViewBinding(MovieFragment target, View source) {
    this.target = target;

    target.viewPager = Utils.findRequiredViewAsType(source, R.id.viewpager, "field 'viewPager'", ViewPager.class);
    target.tabLayout = Utils.findRequiredViewAsType(source, R.id.sliding_tabs, "field 'tabLayout'", TabLayout.class);
    target.movie_no_internetCL = Utils.findRequiredViewAsType(source, R.id.movie_no_internetCL, "field 'movie_no_internetCL'", ConstraintLayout.class);
    target.movie_refreshBTN = Utils.findRequiredViewAsType(source, R.id.movie_refreshBTN, "field 'movie_refreshBTN'", Button.class);
    target.filterFAB = Utils.findRequiredViewAsType(source, R.id.filterFAB, "field 'filterFAB'", FloatingActionButton.class);

    Context context = source.getContext();
    Resources res = context.getResources();
    target.trendingSTR = res.getString(R.string.trending);
    target.top_ratedSTR = res.getString(R.string.top_rated);
    target.popularSTR = res.getString(R.string.popular);
    target.up_comingSTR = res.getString(R.string.up_coming);
    target.errorSTR = res.getString(R.string.error);
    target.went_wrongSTR = res.getString(R.string.went_wrong);
    target.bad_format = res.getString(R.string.bad_format);
  }

  @Override
  @CallSuper
  public void unbind() {
    MovieFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.viewPager = null;
    target.tabLayout = null;
    target.movie_no_internetCL = null;
    target.movie_refreshBTN = null;
    target.filterFAB = null;
  }
}
