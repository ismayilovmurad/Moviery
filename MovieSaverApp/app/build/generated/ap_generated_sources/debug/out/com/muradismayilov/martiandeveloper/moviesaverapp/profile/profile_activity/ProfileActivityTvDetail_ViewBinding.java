// Generated code from Butter Knife. Do not modify!
package com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_activity;

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

public class ProfileActivityTvDetail_ViewBinding implements Unbinder {
  private ProfileActivityTvDetail target;

  @UiThread
  public ProfileActivityTvDetail_ViewBinding(ProfileActivityTvDetail target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ProfileActivityTvDetail_ViewBinding(ProfileActivityTvDetail target, View source) {
    this.target = target;

    target.section0_runtimeTV = Utils.findRequiredViewAsType(source, R.id.section0_runtimeTV, "field 'section0_runtimeTV'", TextView.class);
    target.section0_releasedTV = Utils.findRequiredViewAsType(source, R.id.section0_releasedTV, "field 'section0_releasedTV'", TextView.class);
    target.section0_genreTV = Utils.findRequiredViewAsType(source, R.id.section0_genreTV, "field 'section0_genreTV'", TextView.class);
    target.section0_voteAverageTV = Utils.findRequiredViewAsType(source, R.id.section0_voteAverageTV, "field 'section0_voteAverageTV'", TextView.class);
    target.section0_moreTV = Utils.findRequiredViewAsType(source, R.id.section0_moreTV, "field 'section0_moreTV'", TextView.class);
    target.section1_overviewTV = Utils.findRequiredViewAsType(source, R.id.section1_overviewTV, "field 'section1_overviewTV'", TextView.class);
    target.section1_lastAirDateTV = Utils.findRequiredViewAsType(source, R.id.section1_lastAirDateTV, "field 'section1_lastAirDateTV'", TextView.class);
    target.section1_originalLanguageTV = Utils.findRequiredViewAsType(source, R.id.section1_originalLanguageTV, "field 'section1_originalLanguageTV'", TextView.class);
    target.section1_popularityTV = Utils.findRequiredViewAsType(source, R.id.section1_popularityTV, "field 'section1_popularityTV'", TextView.class);
    target.section1_statusTV = Utils.findRequiredViewAsType(source, R.id.section1_statusTV, "field 'section1_statusTV'", TextView.class);
    target.section1_seasonsTV = Utils.findRequiredViewAsType(source, R.id.section1_seasonsTV, "field 'section1_seasonsTV'", TextView.class);
    target.section1_voteCountTV = Utils.findRequiredViewAsType(source, R.id.section1_voteCountTV, "field 'section1_voteCountTV'", TextView.class);
    target.section1_episodesTV = Utils.findRequiredViewAsType(source, R.id.section1_episodesTV, "field 'section1_episodesTV'", TextView.class);
    target.section2_trailerTV = Utils.findRequiredViewAsType(source, R.id.section2_trailerTV, "field 'section2_trailerTV'", TextView.class);
    target.section6_airDateTV = Utils.findRequiredViewAsType(source, R.id.section6_airDateTV, "field 'section6_airDateTV'", TextView.class);
    target.section6_episodeTV = Utils.findRequiredViewAsType(source, R.id.section6_episodeTV, "field 'section6_episodeTV'", TextView.class);
    target.section6_nameTV = Utils.findRequiredViewAsType(source, R.id.section6_nameTV, "field 'section6_nameTV'", TextView.class);
    target.section6_seasonTV = Utils.findRequiredViewAsType(source, R.id.section6_seasonTV, "field 'section6_seasonTV'", TextView.class);
    target.section6_overviewTV = Utils.findRequiredViewAsType(source, R.id.section6_overviewTV, "field 'section6_overviewTV'", TextView.class);
    target.section6_voteAverageTV = Utils.findRequiredViewAsType(source, R.id.section6_voteAverageTV, "field 'section6_voteAverageTV'", TextView.class);
    target.section6_voteCountTV = Utils.findRequiredViewAsType(source, R.id.section6_voteCountTV, "field 'section6_voteCountTV'", TextView.class);
    target.section7_airDateTV = Utils.findRequiredViewAsType(source, R.id.section7_airDateTV, "field 'section7_airDateTV'", TextView.class);
    target.section7_episodeTV = Utils.findRequiredViewAsType(source, R.id.section7_episodeTV, "field 'section7_episodeTV'", TextView.class);
    target.section7_nameTV = Utils.findRequiredViewAsType(source, R.id.section7_nameTV, "field 'section7_nameTV'", TextView.class);
    target.section7_seasonTV = Utils.findRequiredViewAsType(source, R.id.section7_seasonTV, "field 'section7_seasonTV'", TextView.class);
    target.section7_overviewTV = Utils.findRequiredViewAsType(source, R.id.section7_overviewTV, "field 'section7_overviewTV'", TextView.class);
    target.section7_voteAverageTV = Utils.findRequiredViewAsType(source, R.id.section7_voteAverageTV, "field 'section7_voteAverageTV'", TextView.class);
    target.section7_voteCountTV = Utils.findRequiredViewAsType(source, R.id.section7_voteCountTV, "field 'section7_voteCountTV'", TextView.class);
    target.backdropIV = Utils.findRequiredViewAsType(source, R.id.backdropIV, "field 'backdropIV'", ImageView.class);
    target.section0_posterIV = Utils.findRequiredViewAsType(source, R.id.section0_posterIV, "field 'section0_posterIV'", ImageView.class);
    target.section2_watchTvInfoIV = Utils.findRequiredViewAsType(source, R.id.section2_watchTvInfoIV, "field 'section2_watchTvInfoIV'", ImageView.class);
    target.section6_posterIV = Utils.findRequiredViewAsType(source, R.id.section6_posterIV, "field 'section6_posterIV'", ImageView.class);
    target.section7_posterIV = Utils.findRequiredViewAsType(source, R.id.section7_posterIV, "field 'section7_posterIV'", ImageView.class);
    target.section1_mainLL = Utils.findRequiredViewAsType(source, R.id.section1_mainLL, "field 'section1_mainLL'", LinearLayout.class);
    target.coordinatorLayout = Utils.findRequiredViewAsType(source, R.id.coordinatorLayout, "field 'coordinatorLayout'", CoordinatorLayout.class);
    target.section4_productionCompaniesRV = Utils.findRequiredViewAsType(source, R.id.section4_productionCompaniesRV, "field 'section4_productionCompaniesRV'", RecyclerView.class);
    target.section3_castRV = Utils.findRequiredViewAsType(source, R.id.section3_castRV, "field 'section3_castRV'", RecyclerView.class);
    target.section10_similarRV = Utils.findRequiredViewAsType(source, R.id.section10_similarRV, "field 'section10_similarRV'", RecyclerView.class);
    target.section5_createdByRV = Utils.findRequiredViewAsType(source, R.id.section5_createdByRV, "field 'section5_createdByRV'", RecyclerView.class);
    target.section8_seasonsRV = Utils.findRequiredViewAsType(source, R.id.section8_seasonsRV, "field 'section8_seasonsRV'", RecyclerView.class);
    target.section9_networksRV = Utils.findRequiredViewAsType(source, R.id.section9_networksRV, "field 'section9_networksRV'", RecyclerView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.mainPB = Utils.findRequiredViewAsType(source, R.id.mainPB, "field 'mainPB'", ProgressBar.class);
    target.section10_similarPB = Utils.findRequiredViewAsType(source, R.id.section10_similarPB, "field 'section10_similarPB'", ProgressBar.class);
    target.section11_picturesSV = Utils.findRequiredViewAsType(source, R.id.section11_picturesSV, "field 'section11_picturesSV'", SliderView.class);
    target.mainFAB = Utils.findRequiredViewAsType(source, R.id.mainFAB, "field 'mainFAB'", FloatingActionButton.class);
    target.adViewPlaceholderFL = Utils.findRequiredViewAsType(source, R.id.adViewPlaceholderFL, "field 'adViewPlaceholderFL'", FrameLayout.class);
    target.section2_comingSoonFL = Utils.findRequiredViewAsType(source, R.id.section2_comingSoonFL, "field 'section2_comingSoonFL'", FrameLayout.class);
    target.section2_view = Utils.findRequiredView(source, R.id.section2_view, "field 'section2_view'");
    target.section2_trailerOptionsRG = Utils.findRequiredViewAsType(source, R.id.section2_trailerOptionsRG, "field 'section2_trailerOptionsRG'", RadioGroup.class);
    target.section2_youTubePlayerView = Utils.findRequiredViewAsType(source, R.id.section2_youTubePlayerView, "field 'section2_youTubePlayerView'", YouTubePlayerView.class);

    Context context = source.getContext();
    Resources res = context.getResources();
    target.no_internet_connectionSTR = res.getString(R.string.no_internet_connection);
    target.errorSTR = res.getString(R.string.error);
    target.went_wrongSTR = res.getString(R.string.went_wrong);
    target.no_internet_connection = res.getString(R.string.no_internet_connection);
    target.banner_ad_for_tv_detail = res.getString(R.string.banner_ad_for_detail);
    target.interstitial_ad_for_add = res.getString(R.string.interstitial_ad_for_add_delete);
    target.delete_successful = res.getString(R.string.delete_successful);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProfileActivityTvDetail target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.section0_runtimeTV = null;
    target.section0_releasedTV = null;
    target.section0_genreTV = null;
    target.section0_voteAverageTV = null;
    target.section0_moreTV = null;
    target.section1_overviewTV = null;
    target.section1_lastAirDateTV = null;
    target.section1_originalLanguageTV = null;
    target.section1_popularityTV = null;
    target.section1_statusTV = null;
    target.section1_seasonsTV = null;
    target.section1_voteCountTV = null;
    target.section1_episodesTV = null;
    target.section2_trailerTV = null;
    target.section6_airDateTV = null;
    target.section6_episodeTV = null;
    target.section6_nameTV = null;
    target.section6_seasonTV = null;
    target.section6_overviewTV = null;
    target.section6_voteAverageTV = null;
    target.section6_voteCountTV = null;
    target.section7_airDateTV = null;
    target.section7_episodeTV = null;
    target.section7_nameTV = null;
    target.section7_seasonTV = null;
    target.section7_overviewTV = null;
    target.section7_voteAverageTV = null;
    target.section7_voteCountTV = null;
    target.backdropIV = null;
    target.section0_posterIV = null;
    target.section2_watchTvInfoIV = null;
    target.section6_posterIV = null;
    target.section7_posterIV = null;
    target.section1_mainLL = null;
    target.coordinatorLayout = null;
    target.section4_productionCompaniesRV = null;
    target.section3_castRV = null;
    target.section10_similarRV = null;
    target.section5_createdByRV = null;
    target.section8_seasonsRV = null;
    target.section9_networksRV = null;
    target.toolbar = null;
    target.mainPB = null;
    target.section10_similarPB = null;
    target.section11_picturesSV = null;
    target.mainFAB = null;
    target.adViewPlaceholderFL = null;
    target.section2_comingSoonFL = null;
    target.section2_view = null;
    target.section2_trailerOptionsRG = null;
    target.section2_youTubePlayerView = null;
  }
}
