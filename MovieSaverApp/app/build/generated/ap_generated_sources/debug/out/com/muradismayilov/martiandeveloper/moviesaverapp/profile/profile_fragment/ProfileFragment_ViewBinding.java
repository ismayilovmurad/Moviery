// Generated code from Butter Knife. Do not modify!
package com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_fragment;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ProfileFragment_ViewBinding implements Unbinder {
  private ProfileFragment target;

  @UiThread
  public ProfileFragment_ViewBinding(ProfileFragment target, View source) {
    this.target = target;

    target.mainCL = Utils.findRequiredViewAsType(source, R.id.mainCL, "field 'mainCL'", ConstraintLayout.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeRefreshLayout, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    target.my_profile_no_internetCL = Utils.findRequiredViewAsType(source, R.id.my_profile_no_internetCL, "field 'my_profile_no_internetCL'", ConstraintLayout.class);
    target.my_profile_refreshBTN = Utils.findRequiredViewAsType(source, R.id.my_profile_refreshBTN, "field 'my_profile_refreshBTN'", Button.class);
    target.mainPB = Utils.findRequiredViewAsType(source, R.id.mainPB, "field 'mainPB'", ProgressBar.class);
    target.myMoviesRV = Utils.findRequiredViewAsType(source, R.id.myMoviesRV, "field 'myMoviesRV'", RecyclerView.class);
    target.myTvRV = Utils.findRequiredViewAsType(source, R.id.myTvRV, "field 'myTvRV'", RecyclerView.class);
    target.favoriteActorsRV = Utils.findRequiredViewAsType(source, R.id.favoriteActorsRV, "field 'favoriteActorsRV'", RecyclerView.class);
    target.myMoviesExpandBTN = Utils.findRequiredViewAsType(source, R.id.myMoviesExpandBTN, "field 'myMoviesExpandBTN'", Button.class);
    target.myTvExpandBTN = Utils.findRequiredViewAsType(source, R.id.myTvExpandBTN, "field 'myTvExpandBTN'", Button.class);
    target.favoriteActorsExpandBTN = Utils.findRequiredViewAsType(source, R.id.favoriteActorsExpandBTN, "field 'favoriteActorsExpandBTN'", Button.class);
    target.myMoviesRG = Utils.findRequiredViewAsType(source, R.id.myMoviesRG, "field 'myMoviesRG'", RadioGroup.class);
    target.myTvRG = Utils.findRequiredViewAsType(source, R.id.myTvRG, "field 'myTvRG'", RadioGroup.class);
    target.myMoviesWatchedRBTN = Utils.findRequiredViewAsType(source, R.id.myMoviesWatchedRBTN, "field 'myMoviesWatchedRBTN'", RadioButton.class);
    target.myTvWatchedRBTN = Utils.findRequiredViewAsType(source, R.id.myTvWatchedRBTN, "field 'myTvWatchedRBTN'", RadioButton.class);
    target.myMoviesTV = Utils.findRequiredViewAsType(source, R.id.myMoviesTV, "field 'myMoviesTV'", TextView.class);
    target.myTvTV = Utils.findRequiredViewAsType(source, R.id.myTvTV, "field 'myTvTV'", TextView.class);
    target.favoriteActorsTV = Utils.findRequiredViewAsType(source, R.id.favoriteActorsTV, "field 'favoriteActorsTV'", TextView.class);

    Context context = source.getContext();
    Resources res = context.getResources();
    target.no_internet_connectionSTR = res.getString(R.string.no_internet_connection);
    target.errorSTR = res.getString(R.string.error);
    target.went_wrongSTR = res.getString(R.string.went_wrong);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProfileFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mainCL = null;
    target.swipeRefreshLayout = null;
    target.my_profile_no_internetCL = null;
    target.my_profile_refreshBTN = null;
    target.mainPB = null;
    target.myMoviesRV = null;
    target.myTvRV = null;
    target.favoriteActorsRV = null;
    target.myMoviesExpandBTN = null;
    target.myTvExpandBTN = null;
    target.favoriteActorsExpandBTN = null;
    target.myMoviesRG = null;
    target.myTvRG = null;
    target.myMoviesWatchedRBTN = null;
    target.myTvWatchedRBTN = null;
    target.myMoviesTV = null;
    target.myTvTV = null;
    target.favoriteActorsTV = null;
  }
}
