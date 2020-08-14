package com.muradismayilov.martiandeveloper.moviesaverapp.auth;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.common.activity.FeedActivity;

import java.util.HashSet;
import java.util.Set;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    // UI Components
    // TextView
    @BindView(R.id.forgot_passwordTV)
    TextView forgot_passwordTV;
    @BindView(R.id.sign_upTV)
    TextView sign_upTV;
    // Layout
    @BindView(R.id.mainContainer)
    ConstraintLayout mainContainer;
    @BindView(R.id.bottomInnerContainerCL)
    ConstraintLayout bottomInnerContainerCL;
    @BindView(R.id.innerContainerLL)
    LinearLayout innerContainerLL;
    // EditText
    @BindView(R.id.emailET)
    AutoCompleteTextView emailET;
    @BindView(R.id.passwordET)
    TextInputEditText passwordET;
    // Button
    @BindView(R.id.log_inBTN)
    Button log_inBTN;
    // ProgressBar
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    // Strings
    @BindString(R.string.fill)
    String fillSTR;
    @BindString(R.string.verify_your_email)
    String verify_your_emailSTR;
    @BindString(R.string.error)
    String errorSTR;
    @BindString(R.string.success)
    String successSTR;
    @BindString(R.string.reset)
    String resetSTR;
    @BindString(R.string.enter_email)
    String enterEmailSTR;
    @BindString(R.string.went_wrong)
    String went_wrongSTR;
    @BindString(R.string.no_internet_connection)
    String no_internet_connectionSTR;

    // Variables
    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    // Firebase
    private FirebaseAnalytics mFirebaseAnalytics;
    // String
    private String email, password, LOG;
    private final String PREFS_NAME = "PingBusPrefs";
    private final String PREFS_SEARCH_HISTORY = "SearchHistory";
    private final String PREFS_NOTE = "Note";
    // List
    private Set<String> history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.auth_activity_log_in);
        ButterKnife.bind(this);
        initialMethods();
    }

    private void initialMethods() {
        declareVariables();
        getHistory();
        setListeners();

        progressBar.setVisibility(View.INVISIBLE);

        showNoteDialog();
    }

    private void declareVariables() {
        // Firebase
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        // String
        LOG = "MartianDeveloper";
    }

    private void getHistory() {
        try {
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            history = new HashSet<>(settings.getStringSet(PREFS_SEARCH_HISTORY, new HashSet<>()));
            setAutoCompleteSource();
        } catch (Exception e) {
            if (e.getLocalizedMessage() != null) {
                Log.d(LOG, errorSTR + e.getLocalizedMessage());
            } else {
                Log.d(LOG, "Exception is null! Cause is in the getHistory, SplashActivity");
            }
        }
    }

    private void setListeners() {
        forgot_passwordTV.setOnClickListener(this);
        log_inBTN.setOnClickListener(this);
        sign_upTV.setOnClickListener(this);
    }

    private void showNoteDialog() {
        SharedPreferences sharedPreferences =
                getSharedPreferences(PREFS_NOTE, MODE_PRIVATE);
        String first_time = sharedPreferences.getString("first_time", "yes");

        assert first_time != null;
        if (first_time.equals("yes")) {

            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                final AlertDialog dialog_note = new AlertDialog.Builder(LogInActivity.this).create();
                @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.auth_dialog_note, null);

                final Button okayBTN = view.findViewById(R.id.okayBTN);

                okayBTN.setOnClickListener(v -> dialog_note.dismiss());

                dialog_note.setView(view);
                dialog_note.setCanceledOnTouchOutside(false);
                dialog_note.show();

                SharedPreferences.Editor editor =
                        getSharedPreferences(PREFS_NOTE, MODE_PRIVATE).edit();
                editor.putString("first_time", "no");
                editor.apply();
            }, 1000);
        }
    }

    @Override
    public void onClick(View v) {
        Bundle params = new Bundle();
        params.putInt("ButtonID", v.getId());
        String buttonName;

        switch (v.getId()) {
            case R.id.log_inBTN:
                buttonName = "log_inBTN";
                checkInternetConnectionForLogIn();
                break;
            case R.id.forgot_passwordTV:
                buttonName = "forgot_passwordTV";
                showPasswordDialog();
                break;
            case R.id.sign_upTV:
                buttonName = "sign_upTV";
                go(SignUpActivity.class);
                break;
            default:
                buttonName = "";
                break;
        }

        mFirebaseAnalytics.logEvent(buttonName, params);
    }

    private void checkInternetConnectionForLogIn() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            logIn();

        } else {
            showSnackBar(errorSTR + no_internet_connectionSTR);
        }
    }

    private void logIn() {
        showProgress();

        try {
            getTexts();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                hideProgress();
                showSnackBar(fillSTR);
            } else {
                mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
                    mUser = authResult.getUser();

                    assert mUser != null;
                    if (mUser.isEmailVerified()) {
                        hideProgress();
                        addSearchInput(emailET.getText().toString());
                        go(FeedActivity.class);
                    } else {
                        hideProgress();
                        showSnackBar(verify_your_emailSTR);
                    }
                }).addOnFailureListener(e -> {
                    hideProgress();
                    if (e.getLocalizedMessage() != null) {
                        Log.d(LOG, errorSTR + e.getLocalizedMessage());
                        showSnackBar(errorSTR + e.getLocalizedMessage());
                    } else {
                        Log.d(LOG, "Exception is null! Cause is in the logIn, SplashActivity");
                        showSnackBar(errorSTR + went_wrongSTR);
                    }
                });
            }
        } catch (Exception e) {
            hideProgress();
            if (e.getLocalizedMessage() != null) {
                Log.d(LOG, errorSTR + e.getLocalizedMessage());
            } else {
                Log.d(LOG, "Exception is null! Cause is in the logIn, SplashActivity");
            }
            showSnackBar(errorSTR + went_wrongSTR);
        }
    }

    private void showProgress() {
        innerContainerLL.setAlpha(0.5f);
        bottomInnerContainerCL.setAlpha(0.5f);
        emailET.setFocusable(false);
        passwordET.setFocusable(false);
        forgot_passwordTV.setClickable(false);
        log_inBTN.setClickable(false);
        sign_upTV.setClickable(false);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        innerContainerLL.setAlpha(1.0f);
        bottomInnerContainerCL.setAlpha(1.0f);
        emailET.setFocusableInTouchMode(true);
        passwordET.setFocusableInTouchMode(true);
        forgot_passwordTV.setClickable(true);
        log_inBTN.setClickable(true);
        sign_upTV.setClickable(true);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void showSnackBar(String message) {
        try {
            Snackbar snackbar = Snackbar.make(mainContainer, message + "", Snackbar.LENGTH_LONG);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(ContextCompat.getColor(LogInActivity.this, R.color.colorError));
            snackbar.show();
        } catch (Exception e) {
            if (e.getLocalizedMessage() != null) {
                Log.d(LOG, errorSTR + e.getLocalizedMessage());
            } else {
                Log.d(LOG, "Exception is null! Cause is in the showSnackBar, SplashActivity");
            }
        }
    }

    private void getTexts() {
        if (emailET.getText() != null) {
            email = emailET.getText().toString().trim();
        }
        if (passwordET.getText() != null) {
            password = passwordET.getText().toString().trim();
        }
    }

    private void showPasswordDialog() {
        final AlertDialog dialog_forgot_password = new AlertDialog.Builder(LogInActivity.this).create();
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.auth_dialog_forgot_password, null);

        final LinearLayout innerContainer = view.findViewById(R.id.innerContainer);
        final TextInputEditText emailET = view.findViewById(R.id.emailET);
        final Button sendBTN = view.findViewById(R.id.sendBTN);
        final Button cancelBTN = view.findViewById(R.id.cancelBTN);
        final ProgressBar dialog_progress = view.findViewById(R.id.progressBar);

        dialog_progress.setVisibility(View.INVISIBLE);

        cancelBTN.setOnClickListener(v -> dialog_forgot_password.dismiss());

        sendBTN.setOnClickListener(v -> {

            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                showProgressForDialog(innerContainer, dialog_progress);

                String email = "";

                try {
                    if (emailET.getText() != null) {
                        email = emailET.getText().toString().trim();
                    }

                    if (TextUtils.isEmpty(email)) {
                        hideProgressForDialog(innerContainer, dialog_progress);
                        showErrorForDialog(enterEmailSTR, emailET);
                    } else {
                        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                hideProgressForDialog(innerContainer, dialog_progress);
                                dialog_forgot_password.dismiss();
                                Toast.makeText(LogInActivity.this, successSTR + resetSTR, Toast.LENGTH_SHORT).show();
                            } else {
                                hideProgressForDialog(innerContainer, dialog_progress);

                                if (task.getException() != null) {
                                    Log.d(LOG, errorSTR + task.getException().getLocalizedMessage());
                                    Toast.makeText(LogInActivity.this, errorSTR + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.d(LOG, "Exception is null! Cause is in the showPasswordDialog, SplashActivity");
                                    Toast.makeText(LogInActivity.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    hideProgressForDialog(innerContainer, dialog_progress);

                    if (e.getLocalizedMessage() != null) {
                        Log.d(LOG, errorSTR + e.getLocalizedMessage());
                    } else {
                        Log.d(LOG, "Exception is null! Cause is in the showPasswordDialog, SplashActivity");
                    }
                    Toast.makeText(LogInActivity.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

            } else {

                Toast.makeText(LogInActivity.this, no_internet_connectionSTR, Toast.LENGTH_SHORT).show();
            }
        });

        dialog_forgot_password.setView(view);
        dialog_forgot_password.show();
    }

    private void showErrorForDialog(String message, TextInputEditText emailET) {
        emailET.setError(message);
    }

    private void hideProgressForDialog(LinearLayout innerContainer, ProgressBar dialog_progress) {
        dialog_progress.setVisibility(View.INVISIBLE);
        innerContainer.setAlpha(1.0f);
    }

    private void showProgressForDialog(LinearLayout innerContainer, ProgressBar dialog_progress) {
        dialog_progress.setVisibility(View.VISIBLE);
        innerContainer.setAlpha(0.5f);
    }

    private void go(Class activity) {
        Intent go_intent = new Intent(LogInActivity.this, activity);
        startActivity(go_intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        LogInActivity.this.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();

        savePrefs();
    }

    private void setAutoCompleteSource() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, history.toArray(new String[0]));
        emailET.setAdapter(adapter);
    }

    private void addSearchInput(String input) {
        if (!history.contains(input)) {
            history.add(input);
            setAutoCompleteSource();
        }
    }

    private void savePrefs() {
        try {
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putStringSet(PREFS_SEARCH_HISTORY, history);

            editor.apply();
        } catch (Exception e) {
            if (e.getLocalizedMessage() != null) {
                Log.d(LOG, errorSTR + e.getLocalizedMessage());
            } else {
                Log.d(LOG, "Exception is null! Cause is in the savePrefs, SplashActivity");
            }
        }
    }
}
