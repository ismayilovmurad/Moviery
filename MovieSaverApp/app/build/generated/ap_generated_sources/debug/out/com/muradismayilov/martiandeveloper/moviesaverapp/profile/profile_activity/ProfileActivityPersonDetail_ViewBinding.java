// Generated code from Butter Knife. Do not modify!
package com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_activity;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.smarteist.autoimageslider.SliderView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ProfileActivityPersonDetail_ViewBinding implements Unbinder {
  private ProfileActivityPersonDetail target;

  @UiThread
  public ProfileActivityPersonDetail_ViewBinding(ProfileActivityPersonDetail target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ProfileActivityPersonDetail_ViewBinding(ProfileActivityPersonDetail target, View source) {
    this.target = target;

    target.section0_moreTV = Utils.findRequiredViewAsType(source, R.id.section0_moreTV, "field 'section0_moreTV'", TextView.class);
    target.section0_birthdayTV = Utils.findRequiredViewAsType(source, R.id.section0_birthdayTV, "field 'section0_birthdayTV'", TextView.class);
    target.section0_knownForTV = Utils.findRequiredViewAsType(source, R.id.section0_knownForTV, "field 'section0_knownForTV'", TextView.class);
    target.section0_popularityTV = Utils.findRequiredViewAsType(source, R.id.section0_popularityTV, "field 'section0_popularityTV'", TextView.class);
    target.section1_placeOfBirthTV = Utils.findRequiredViewAsType(source, R.id.section1_placeOfBirthTV, "field 'section1_placeOfBirthTV'", TextView.class);
    target.section1_biographyTV = Utils.findRequiredViewAsType(source, R.id.section1_biographyTV, "field 'section1_biographyTV'", TextView.class);
    target.backdropIV = Utils.findRequiredViewAsType(source, R.id.backdropIV, "field 'backdropIV'", ImageView.class);
    target.section0_posterIV = Utils.findRequiredViewAsType(source, R.id.section0_posterIV, "field 'section0_posterIV'", ImageView.class);
    target.section1_mainLL = Utils.findRequiredViewAsType(source, R.id.section1_mainLL, "field 'section1_mainLL'", LinearLayout.class);
    target.coordinatorLayout = Utils.findRequiredViewAsType(source, R.id.coordinatorLayout, "field 'coordinatorLayout'", CoordinatorLayout.class);
    target.section2_moviesRV = Utils.findRequiredViewAsType(source, R.id.section2_moviesRV, "field 'section2_moviesRV'", RecyclerView.class);
    target.section3_tvRV = Utils.findRequiredViewAsType(source, R.id.section3_tvRV, "field 'section3_tvRV'", RecyclerView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.mainPB = Utils.findRequiredViewAsType(source, R.id.mainPB, "field 'mainPB'", ProgressBar.class);
    target.section4_picturesSV = Utils.findRequiredViewAsType(source, R.id.section4_picturesSV, "field 'section4_picturesSV'", SliderView.class);
    target.mainFAB = Utils.findRequiredViewAsType(source, R.id.mainFAB, "field 'mainFAB'", FloatingActionButton.class);
    target.adViewPlaceholderFL = Utils.findRequiredViewAsType(source, R.id.adViewPlaceholderFL, "field 'adViewPlaceholderFL'", FrameLayout.class);

    Context context = source.getContext();
    Resources res = context.getResources();
    target.no_internet_connectionSTR = res.getString(R.string.no_internet_connection);
    target.errorSTR = res.getString(R.string.error);
    target.went_wrongSTR = res.getString(R.string.went_wrong);
    target.delete_successful = res.getString(R.string.delete_successful);
    target.banner_ad_for_person_detail = res.getString(R.string.banner_ad_for_detail);
    target.interstitial_ad_for_add = res.getString(R.string.interstitial_ad_for_add_delete);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProfileActivityPersonDetail target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.section0_moreTV = null;
    target.section0_birthdayTV = null;
    target.section0_knownForTV = null;
    target.section0_popularityTV = null;
    target.section1_placeOfBirthTV = null;
    target.section1_biographyTV = null;
    target.backdropIV = null;
    target.section0_posterIV = null;
    target.section1_mainLL = null;
    target.coordinatorLayout = null;
    target.section2_moviesRV = null;
    target.section3_tvRV = null;
    target.toolbar = null;
    target.mainPB = null;
    target.section4_picturesSV = null;
    target.mainFAB = null;
    target.adViewPlaceholderFL = null;
  }
}
