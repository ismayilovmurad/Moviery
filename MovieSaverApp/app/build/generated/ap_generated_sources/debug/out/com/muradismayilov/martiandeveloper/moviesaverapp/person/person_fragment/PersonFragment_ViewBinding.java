// Generated code from Butter Knife. Do not modify!
package com.muradismayilov.martiandeveloper.moviesaverapp.person.person_fragment;

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
import com.google.android.material.tabs.TabLayout;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PersonFragment_ViewBinding implements Unbinder {
  private PersonFragment target;

  @UiThread
  public PersonFragment_ViewBinding(PersonFragment target, View source) {
    this.target = target;

    target.viewPager = Utils.findRequiredViewAsType(source, R.id.viewpager, "field 'viewPager'", ViewPager.class);
    target.tabLayout = Utils.findRequiredViewAsType(source, R.id.sliding_tabs, "field 'tabLayout'", TabLayout.class);
    target.person_no_internetCL = Utils.findRequiredViewAsType(source, R.id.person_no_internetCL, "field 'person_no_internetCL'", ConstraintLayout.class);
    target.person_refreshBTN = Utils.findRequiredViewAsType(source, R.id.person_refreshBTN, "field 'person_refreshBTN'", Button.class);

    Context context = source.getContext();
    Resources res = context.getResources();
    target.trendingSTR = res.getString(R.string.trending);
    target.popularSTR = res.getString(R.string.popular);
    target.errorSTR = res.getString(R.string.error);
    target.went_wrongSTR = res.getString(R.string.went_wrong);
  }

  @Override
  @CallSuper
  public void unbind() {
    PersonFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.viewPager = null;
    target.tabLayout = null;
    target.person_no_internetCL = null;
    target.person_refreshBTN = null;
  }
}
