// Generated code from Butter Knife. Do not modify!
package com.muradismayilov.martiandeveloper.moviesaverapp.common.activity;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AboutActivity_ViewBinding implements Unbinder {
  private AboutActivity target;

  @UiThread
  public AboutActivity_ViewBinding(AboutActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AboutActivity_ViewBinding(AboutActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.facebookIV = Utils.findRequiredViewAsType(source, R.id.facebookIV, "field 'facebookIV'", ImageView.class);
    target.instagramIV = Utils.findRequiredViewAsType(source, R.id.instagramIV, "field 'instagramIV'", ImageView.class);
    target.twitterIV = Utils.findRequiredViewAsType(source, R.id.twitterIV, "field 'twitterIV'", ImageView.class);
    target.whatsappIV = Utils.findRequiredViewAsType(source, R.id.whatsappIV, "field 'whatsappIV'", ImageView.class);
    target.websiteIV = Utils.findRequiredViewAsType(source, R.id.websiteIV, "field 'websiteIV'", ImageView.class);
    target.emailIV = Utils.findRequiredViewAsType(source, R.id.emailIV, "field 'emailIV'", ImageView.class);
    target.themoviedbIV = Utils.findRequiredViewAsType(source, R.id.themoviedbIV, "field 'themoviedbIV'", ImageView.class);
    target.flaticonIV = Utils.findRequiredViewAsType(source, R.id.flaticonIV, "field 'flaticonIV'", ImageView.class);
    target.privacyPolicyTV = Utils.findRequiredViewAsType(source, R.id.privacyPolicyTV, "field 'privacyPolicyTV'", TextView.class);
    target.copyrightTV = Utils.findRequiredViewAsType(source, R.id.copyrightTV, "field 'copyrightTV'", TextView.class);

    Context context = source.getContext();
    Resources res = context.getResources();
    target.about = res.getString(R.string.about);
    target.copyright_1 = res.getString(R.string.copyright_1);
    target.copyright_2 = res.getString(R.string.copyright_2);
  }

  @Override
  @CallSuper
  public void unbind() {
    AboutActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.facebookIV = null;
    target.instagramIV = null;
    target.twitterIV = null;
    target.whatsappIV = null;
    target.websiteIV = null;
    target.emailIV = null;
    target.themoviedbIV = null;
    target.flaticonIV = null;
    target.privacyPolicyTV = null;
    target.copyrightTV = null;
  }
}
