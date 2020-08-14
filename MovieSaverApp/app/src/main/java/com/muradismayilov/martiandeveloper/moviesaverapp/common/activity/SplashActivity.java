package com.muradismayilov.martiandeveloper.moviesaverapp.common.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.auth.LogInActivity;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    // UI Components
    // ImageView
    @BindView(R.id.movieryIV)
    ImageView movieryIV;
    @BindView(R.id.loadingTV)
    TextView loadingTV;

    // Strings
    @BindString(R.string.loading)
    String loading;
    @BindString(R.string.checking_for_credentials)
    String checking_for_credentials;
    @BindString(R.string.logging_you_in)
    String logging_you_in;

    // Variables
    // Firebase
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.feed_activity_splash);
        ButterKnife.bind(this);
        initialMethods();
    }

    private void initialMethods() {
        setImage();
        setText();
        declareVariables();
        checkInternetConnection();
    }

    private void setImage() {
        Glide.with(this)
                .load(R.drawable.moviery)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .priority(Priority.IMMEDIATE)
                .into(movieryIV);
    }

    private void setText() {
        loadingTV.setText(loading);
    }

    private void declareVariables() {
        // Firebase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    private void checkInternetConnection() {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                loadingTV.setText(checking_for_credentials);
                checkUser();

            } else {
                showNoInternetDialog();
            }
        }, 1000);

    }

    private void checkUser() {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (mUser != null) {
                loadingTV.setText(logging_you_in);
                go(FeedActivity.class);
            } else {
                go(LogInActivity.class);
            }
        }, 1000);
    }

    private void go(final Class activity) {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, activity);
            startActivity(intent);
            SplashActivity.this.finish();
        }, 2000);
    }

    private void showNoInternetDialog() {
        final AlertDialog dialog_no_internet = new AlertDialog.Builder(this).create();
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.feed_dialog_no_internet, null);

        final Button retryBTN = view.findViewById(R.id.retryBTN);
        final Button exitBTN = view.findViewById(R.id.exitBTN);

        retryBTN.setOnClickListener(v -> {
            dialog_no_internet.dismiss();
            checkInternetConnection();
        });

        exitBTN.setOnClickListener(v -> {
            dialog_no_internet.dismiss();
            SplashActivity.this.finish();
        });

        dialog_no_internet.setView(view);
        dialog_no_internet.setCanceledOnTouchOutside(false);
        dialog_no_internet.show();
    }
}