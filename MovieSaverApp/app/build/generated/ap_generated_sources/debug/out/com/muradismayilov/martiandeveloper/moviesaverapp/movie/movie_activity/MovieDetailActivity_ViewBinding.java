// Generated code from Butter Knife. Do not modify!
package com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_activity;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.smarteist.autoimageslider.SliderView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MovieDetailActivity_ViewBinding implements Unbinder {
  private MovieDetailActivity target;

  @UiThread
  public MovieDetailActivity_ViewBinding(MovieDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MovieDetailActivity_ViewBinding(MovieDetailActivity target, View source) {
    this.target = target;

    target.section0_runtimeTV = Utils.findRequiredViewAsType(source, R.id.section0_runtimeTV, "field 'section0_runtimeTV'", TextView.class);
    target.section0_releasedTV = Utils.findRequiredViewAsType(source, R.id.section0_releasedTV, "field 'section0_releasedTV'", TextView.class);
    target.section0_genreTV = Utils.findRequiredViewAsType(source, R.id.section0_genreTV, "field 'section0_genreTV'", TextView.class);
    target.section0_voteAverageTV = Utils.findRequiredViewAsType(source, R.id.section0_voteAverageTV, "field 'section0_voteAverageTV'", TextView.class);
    target.moreTV = Utils.findRequiredViewAsType(source, R.id.moreTV, "field 'moreTV'", TextView.class);
    target.section1_overviewTV = Utils.findRequiredViewAsType(source, R.id.section1_overviewTV, "field 'section1_overviewTV'", TextView.class);
    target.section1_budgetTV = Utils.findRequiredViewAsType(source, R.id.section1_budgetTV, "field 'section1_budgetTV'", TextView.class);
    target.section1_originalLanguageTV = Utils.findRequiredViewAsType(source, R.id.section1_originalLanguageTV, "field 'section1_originalLanguageTV'", TextView.class);
    target.section1_popularityTV = Utils.findRequiredViewAsType(source, R.id.section1_popularityTV, "field 'section1_popularityTV'", TextView.class);
    target.section1_statusTV = Utils.findRequiredViewAsType(source, R.id.section1_statusTV, "field 'section1_statusTV'", TextView.class);
    target.section1_tagLineTV = Utils.findRequiredViewAsType(source, R.id.section1_tagLineTV, "field 'section1_tagLineTV'", TextView.class);
    target.section1_voteCountTV = Utils.findRequiredViewAsType(source, R.id.section1_voteCountTV, "field 'section1_voteCountTV'", TextView.class);
    target.favoriteTV = Utils.findRequiredViewAsType(source, R.id.favoriteTV, "field 'favoriteTV'", TextView.class);
    target.watch_laterTV = Utils.findRequiredViewAsType(source, R.id.watch_laterTV, "field 'watch_laterTV'", TextView.class);
    target.watchedTV = Utils.findRequiredViewAsType(source, R.id.watchedTV, "field 'watchedTV'", TextView.class);
    target.section1_revenueTV = Utils.findRequiredViewAsType(source, R.id.section1_revenueTV, "field 'section1_revenueTV'", TextView.class);
    target.section2_trailerTV = Utils.findRequiredViewAsType(source, R.id.section2_trailerTV, "field 'section2_trailerTV'", TextView.class);
    target.backdropIV = Utils.findRequiredViewAsType(source, R.id.backdropIV, "field 'backdropIV'", ImageView.class);
    target.section0_posterIV = Utils.findRequiredViewAsType(source, R.id.section0_posterIV, "field 'section0_posterIV'", ImageView.class);
    target.section2_watchMovieInfoIV = Utils.findRequiredViewAsType(source, R.id.section2_watchMovieInfoIV, "field 'section2_watchMovieInfoIV'", ImageView.class);
    target.section1_mainLL = Utils.findRequiredViewAsType(source, R.id.section1_mainLL, "field 'section1_mainLL'", LinearLayout.class);
    target.coordinatorLayout = Utils.findRequiredViewAsType(source, R.id.coordinatorLayout, "field 'coordinatorLayout'", CoordinatorLayout.class);
    target.adViewPlaceholderFL = Utils.findRequiredViewAsType(source, R.id.adViewPlaceholderFL, "field 'adViewPlaceholderFL'", FrameLayout.class);
    target.section2_comingSoonFL = Utils.findRequiredViewAsType(source, R.id.section2_comingSoonFL, "field 'section2_comingSoonFL'", FrameLayout.class);
    target.section2_view = Utils.findRequiredView(source, R.id.section2_view, "field 'section2_view'");
    target.section4_productionCompaniesRV = Utils.findRequiredViewAsType(source, R.id.section4_productionCompaniesRV, "field 'section4_productionCompaniesRV'", RecyclerView.class);
    target.section3_castRV = Utils.findRequiredViewAsType(source, R.id.section3_castRV, "field 'section3_castRV'", RecyclerView.class);
    target.section5_similarRV = Utils.findRequiredViewAsType(source, R.id.section5_similarRV, "field 'section5_similarRV'", RecyclerView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.mainPB = Utils.findRequiredViewAsType(source, R.id.mainPB, "field 'mainPB'", ProgressBar.class);
    target.section5_similarPB = Utils.findRequiredViewAsType(source, R.id.section5_similarPB, "field 'section5_similarPB'", ProgressBar.class);
    target.section6_picturesSV = Utils.findRequiredViewAsType(source, R.id.section6_picturesSV, "field 'section6_picturesSV'", SliderView.class);
    target.mainFAB = Utils.findRequiredViewAsType(source, R.id.mainFAB, "field 'mainFAB'", FloatingActionButton.class);
    target.favoriteFAB = Utils.findRequiredViewAsType(source, R.id.favoriteFAB, "field 'favoriteFAB'", FloatingActionButton.class);
    target.watch_laterFAB = Utils.findRequiredViewAsType(source, R.id.watch_laterFAB, "field 'watch_laterFAB'", FloatingActionButton.class);
    target.watchedFAB = Utils.findRequiredViewAsType(source, R.id.watchedFAB, "field 'watchedFAB'", FloatingActionButton.class);
    target.section2_trailerOptionsRG = Utils.findRequiredViewAsType(source, R.id.section2_trailerOptionsRG, "field 'section2_trailerOptionsRG'", RadioGroup.class);
    target.section2_youTubePlayerView = Utils.findRequiredViewAsType(source, R.id.section2_youTubePlayerView, "field 'section2_youTubePlayerView'", YouTubePlayerView.class);

    Context context = source.getContext();
    Resources res = context.getResources();
    target.no_internet_connectionSTR = res.getString(R.string.no_internet_connection);
    target.errorSTR = res.getString(R.string.error);
    target.went_wrongSTR = res.getString(R.string.went_wrong);
    target.added_to_favorites = res.getString(R.string.added_to_favorites);
    target.added_to_watch_later = res.getString(R.string.added_to_watch_later);
    target.added_to_watched = res.getString(R.string.added_to_watched);
    target.already_added = res.getString(R.string.already_added);
    target.no_internet_connection = res.getString(R.string.no_internet_connection);
    target.banner_ad_for_detail = res.getString(R.string.banner_ad_for_detail);
    target.interstitial_ad_for_add = res.getString(R.string.interstitial_ad_for_add_delete);
  }

  @Override
  @CallSuper
  public void unbind() {
    MovieDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.section0_runtimeTV = null;
    target.section0_releasedTV = null;
    target.section0_genreTV = null;
    target.section0_voteAverageTV = null;
    target.moreTV = null;
    target.section1_overviewTV = null;
    target.section1_budgetTV = null;
    target.section1_originalLanguageTV = null;
    target.section1_popularityTV = null;
    target.section1_statusTV = null;
    target.section1_tagLineTV = null;
    target.section1_voteCountTV = null;
    target.favoriteTV = null;
    target.watch_laterTV = null;
    target.watchedTV = null;
    target.section1_revenueTV = null;
    target.section2_trailerTV = null;
    target.backdropIV = null;
    target.section0_posterIV = null;
    target.section2_watchMovieInfoIV = null;
    target.section1_mainLL = null;
    target.coordinatorLayout = null;
    target.adViewPlaceholderFL = null;
    target.section2_comingSoonFL = null;
    target.section2_view = null;
    target.section4_productionCompaniesRV = null;
    target.section3_castRV = null;
    target.section5_similarRV = null;
    target.toolbar = null;
    target.mainPB = null;
    target.section5_similarPB = null;
    target.section6_picturesSV = null;
    target.mainFAB = null;
    target.favoriteFAB = null;
    target.watch_laterFAB = null;
    target.watchedFAB = null;
    target.section2_trailerOptionsRG = null;
    target.section2_youTubePlayerView = null;
  }
}
