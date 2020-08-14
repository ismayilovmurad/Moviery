package com.muradismayilov.martiandeveloper.moviesaverapp.common.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;

import org.apache.commons.io.FileUtils;

import java.util.Calendar;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    // UI Components
    // Toolbar
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    // ImageView
    @BindView(R.id.facebookIV)
    ImageView facebookIV;
    @BindView(R.id.instagramIV)
    ImageView instagramIV;
    @BindView(R.id.twitterIV)
    ImageView twitterIV;
    @BindView(R.id.whatsappIV)
    ImageView whatsappIV;
    @BindView(R.id.websiteIV)
    ImageView websiteIV;
    @BindView(R.id.emailIV)
    ImageView emailIV;
    @BindView(R.id.themoviedbIV)
    ImageView themoviedbIV;
    @BindView(R.id.flaticonIV)
    ImageView flaticonIV;
    // TextView
    @BindView(R.id.privacyPolicyTV)
    TextView privacyPolicyTV;
    @BindView(R.id.copyrightTV)
    TextView copyrightTV;

    // Strings
    @BindString(R.string.about)
    String about;
    @BindString(R.string.copyright_1)
    String copyright_1;
    @BindString(R.string.copyright_2)
    String copyright_2;

    // Variables
    // Firebase
    // Firebase
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.feed_activity_about);
        ButterKnife.bind(this);
        initialMethods();
    }

    private void initialMethods() {
        declareVariables();
        setToolbar();
        setImages();
        setTexts();
        setListeners();
    }

    private void declareVariables() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        AboutActivity.this.setTitle(about);
    }

    private void setImages() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            setGlide(R.drawable.facebook, facebookIV);
            setGlide(R.drawable.instagram, instagramIV);
            setGlide(R.drawable.twitter, twitterIV);
            setGlide(R.drawable.whatsapp, whatsappIV);
            setGlide(R.drawable.website, websiteIV);
            setGlide(R.drawable.email, emailIV);
            setGlide(R.drawable.themoviedb, themoviedbIV);
            setGlide(R.drawable.flaticon, flaticonIV);
        } else {
            setGlide(R.drawable.facebook_low, facebookIV);
            setGlide(R.drawable.instagram_low, instagramIV);
            setGlide(R.drawable.twitter_low, twitterIV);
            setGlide(R.drawable.whatsapp_low, whatsappIV);
            setGlide(R.drawable.website_low, websiteIV);
            setGlide(R.drawable.email_low, emailIV);
            setGlide(R.drawable.themoviedb, themoviedbIV);
            setGlide(R.drawable.flaticon, flaticonIV);
        }
    }

    private void setGlide(int source, ImageView imageView) {
        Glide.with(AboutActivity.this)
                .load(source)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.image_placeholder)
                .priority(Priority.IMMEDIATE)
                .into(imageView);
    }

    private void setTexts() {
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String copyright = copyright_1 + " " + year + " " + copyright_2;
        copyrightTV.setText(copyright);
    }

    private void setListeners() {
        facebookIV.setOnClickListener(this);
        instagramIV.setOnClickListener(this);
        twitterIV.setOnClickListener(this);
        whatsappIV.setOnClickListener(this);
        websiteIV.setOnClickListener(this);
        emailIV.setOnClickListener(this);
        themoviedbIV.setOnClickListener(this);
        flaticonIV.setOnClickListener(this);
        privacyPolicyTV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bundle params = new Bundle();
        params.putInt("ButtonID", v.getId());
        String buttonName;

        switch (v.getId()) {
            case R.id.facebookIV:
                buttonName = "facebookIV";
                open("https://www.facebook.com/Martian-Developer-112019446941119/");
                break;
            case R.id.instagramIV:
                buttonName = "instagramIV";
                open("https://www.instagram.com/martiandeveloper/");
                break;
            case R.id.twitterIV:
                buttonName = "twitterIV";
                open("https://twitter.com/Murad58349993/");
                break;
            case R.id.whatsappIV:
                buttonName = "whatsappIV";
                call();
                break;
            case R.id.websiteIV:
                buttonName = "websiteIV";
                open("http://martiandeveloper.com/");
                break;
            case R.id.emailIV:
                buttonName = "emailIV";
                openEmail();
                break;
            case R.id.themoviedbLL:
                buttonName = "themoviedbLL";
                open("https://themoviedb.org/");
                break;
            case R.id.flaticonLL:
                buttonName = "flaticonLL";
                open("https://flaticon.com/");
                break;
            case R.id.privacyPolicyTV:
                buttonName = "privacyPolicyTV";
                open("https://movieryprivacypolicy.blogspot.com/p/privacy-policy.html");
                break;
            default:
                buttonName = "";
                break;
        }

        mFirebaseAnalytics.logEvent(buttonName, params);
    }

    private void open(String url) {
        Uri open_uri = Uri.parse(url);
        Intent open_intent = new Intent(Intent.ACTION_VIEW, open_uri);
        startActivity(open_intent);
    }

    private void call() {
        Uri call_uri = Uri.parse("tel:+994554987507");
        Intent call_intent = new Intent(Intent.ACTION_DIAL, call_uri);
        startActivity(call_intent);
    }

    private void openEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "app.martiandeveloper@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Moviery");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(emailIntent, "Send via"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    protected void onStop() {
        super.onStop();
        String LOG = "MartianDeveloper";
        try {
            FileUtils.deleteQuietly(getApplicationContext().getCacheDir());
        } catch (Exception e) {
            if (e.getLocalizedMessage() != null) {
                Log.d(LOG, "Error: " + e.getLocalizedMessage());
            } else {
                Log.d(LOG, "Error: While Clearing Cache on FeedActivity");
            }
        }

        try {
            if (getApplicationContext().getExternalCacheDir() != null) {
                getApplicationContext().getExternalCacheDir().delete();
            }
        } catch (Exception e) {
            if (e.getLocalizedMessage() != null) {
                Log.d(LOG, "Error: " + e.getLocalizedMessage());
            } else {
                Log.d(LOG, "Error: While Clearing Cache on FeedActivity");
            }
        }
    }
}
