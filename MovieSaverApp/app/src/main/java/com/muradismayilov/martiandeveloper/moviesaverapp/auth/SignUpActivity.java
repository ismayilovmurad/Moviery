package com.muradismayilov.martiandeveloper.moviesaverapp.auth;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    // UI Components
    // TextView
    @BindView(R.id.log_inTV)
    TextView log_inTV;
    // Layout
    @BindView(R.id.mainContainer)
    ConstraintLayout mainContainer;
    @BindView(R.id.bottomInnerContainerCL)
    ConstraintLayout bottomInnerContainerCL;
    @BindView(R.id.innerContainerLL)
    LinearLayout innerContainerLL;
    // EditText
    @BindView(R.id.nameET)
    TextInputEditText nameET;
    @BindView(R.id.emailET)
    TextInputEditText emailET;
    @BindView(R.id.passwordET)
    TextInputEditText passwordET;
    // Button
    @BindView(R.id.sign_upBTN)
    Button sign_upBTN;
    // ProgressBar
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    // Strings
    @BindString(R.string.fill)
    String fillSTR;
    @BindString(R.string.error)
    String errorSTR;
    @BindString(R.string.success)
    String successSTR;
    @BindString(R.string.went_wrong)
    String went_wrongSTR;
    @BindString(R.string.verify)
    String verifySTR;
    @BindString(R.string.long_name)
    String long_name;
    @BindString(R.string.no_internet_connection)
    String no_internet_connectionSTR;

    // Variables
    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseAnalytics mFirebaseAnalytics;
    //String
    private String name, email, password, LOG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.auth_activity_sign_up);
        ButterKnife.bind(this);
        initialMethods();
    }

    private void initialMethods() {
        declareVariables();
        setListeners();

        progressBar.setVisibility(View.INVISIBLE);
    }

    private void declareVariables() {
        // Firebase
        mAuth = FirebaseAuth.getInstance();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        // String
        LOG = "MartianDeveloper";
    }

    private void setListeners() {
        sign_upBTN.setOnClickListener(this);
        log_inTV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bundle params = new Bundle();
        params.putInt("ButtonID", v.getId());
        String buttonName;

        switch (v.getId()) {
            case R.id.sign_upBTN:
                buttonName = "sign_upBTN";
                checkInternetConnectionForSignUp();
                break;
            case R.id.log_inTV:
                buttonName = "log_inTV";
                go();
                break;
            default:
                buttonName = "";
                break;
        }

        mFirebaseAnalytics.logEvent(buttonName, params);
    }

    private void showUserAgreementDialog() {
        final AlertDialog dialog_user_agreement = new AlertDialog.Builder(SignUpActivity.this).create();
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.auth_dialog_user_agreement, null);

        final Button closeBTN = view.findViewById(R.id.closeBTN);
        final Button agreeBTN = view.findViewById(R.id.agreeBTN);

        closeBTN.setOnClickListener(v -> {
            hideProgress();
            dialog_user_agreement.dismiss();
        });

        agreeBTN.setOnClickListener(view1 -> {
            dialog_user_agreement.dismiss();
            mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
                mUser = authResult.getUser();

                if (mUser != null) {
                    // Email verification
                    mUser.sendEmailVerification().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            saveUserData();
                        } else {
                            hideProgress();
                            if (task.getException() != null) {
                                Log.d(LOG, errorSTR + task.getException().getLocalizedMessage());
                                showSnackBar(errorSTR + task.getException().getLocalizedMessage(), R.color.colorError);
                            } else {
                                Log.d(LOG, "Exception is null! Cause is in the signUp, SignUpActivity");
                                showSnackBar(errorSTR + went_wrongSTR, R.color.colorError);
                            }
                        }

                    });
                } else {
                    hideProgress();
                    showSnackBar(errorSTR + went_wrongSTR, R.color.colorError);
                    Log.d(LOG, "User is null on the signUp, SignUpActivity");
                }

            }).addOnFailureListener(e -> {
                hideProgress();
                if (e.getLocalizedMessage() != null) {
                    Log.d(LOG, errorSTR + e.getLocalizedMessage());
                    showSnackBar(errorSTR + e.getLocalizedMessage(), R.color.colorError);
                } else {
                    Log.d(LOG, "Exception is null! Cause is in the signUp, SignUpActivity");
                    showSnackBar(errorSTR + went_wrongSTR, R.color.colorError);
                }
            });
        });

        dialog_user_agreement.setView(view);
        dialog_user_agreement.setCanceledOnTouchOutside(false);
        dialog_user_agreement.show();
    }

    private void checkInternetConnectionForSignUp() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            signUp();

        } else {
            showSnackBar(errorSTR + no_internet_connectionSTR, R.color.colorError);
        }
    }

    private void signUp() {
        showProgress();

        try {
            getTexts();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                hideProgress();
                showSnackBar(fillSTR, R.color.colorError);
            } else if (name.length() > 15) {
                hideProgress();
                showSnackBar(long_name, R.color.colorError);
            } else {
                showUserAgreementDialog();
            }
        } catch (Exception e) {
            hideProgress();
            if (e.getLocalizedMessage() != null) {
                Log.d(LOG, errorSTR + e.getLocalizedMessage());
            } else {
                Log.d(LOG, "Exception is null! Cause is in the signUp, SignUpActivity");
            }
            showSnackBar(errorSTR + went_wrongSTR, R.color.colorError);
        }
    }

    private void showProgress() {
        innerContainerLL.setAlpha(0.5f);
        bottomInnerContainerCL.setAlpha(0.5f);
        nameET.setFocusable(false);
        emailET.setFocusable(false);
        passwordET.setFocusable(false);
        sign_upBTN.setClickable(false);
        log_inTV.setClickable(false);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        innerContainerLL.setAlpha(1.0f);
        bottomInnerContainerCL.setAlpha(1.0f);
        nameET.setFocusableInTouchMode(true);
        emailET.setFocusableInTouchMode(true);
        passwordET.setFocusableInTouchMode(true);
        sign_upBTN.setClickable(true);
        log_inTV.setClickable(true);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void showSnackBar(String message, int color) {
        try {
            Snackbar snackbar = Snackbar.make(mainContainer, message + "", Snackbar.LENGTH_LONG);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(ContextCompat.getColor(SignUpActivity.this, color));
            snackbar.show();
        } catch (Exception e) {
            if (e.getLocalizedMessage() != null) {
                Log.d(LOG, errorSTR + e.getLocalizedMessage());
            } else {
                Log.d(LOG, "Exception is null! Cause is in the showSnackBar, SignUpActivity");
            }
        }
    }

    private void getTexts() {
        if (nameET.getText() != null) {
            name = nameET.getText().toString().trim();
        }
        if (emailET.getText() != null) {
            email = emailET.getText().toString().trim();
        }
        if (passwordET.getText() != null) {
            password = passwordET.getText().toString().trim();
        }
    }

    private void saveUserData() {

        if (mUser != null) {
            DatabaseReference mDatabaseReference = FirebaseDatabase
                    .getInstance()
                    .getReference()
                    .child("Users")
                    .child(mUser.getUid())
                    .child("User Information");

            mDatabaseReference.child("name").setValue(name);
            mDatabaseReference.child("email").setValue(email);
            mDatabaseReference.child("premium").setValue("false").addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    formatEditTexts();
                    hideProgress();
                    showSnackBar(successSTR + verifySTR, R.color.colorSuccess);
                } else {
                    hideProgress();
                    if (task.getException() != null) {
                        Log.d(LOG, errorSTR + task.getException().getLocalizedMessage());
                        showSnackBar(errorSTR + task.getException().getLocalizedMessage(), R.color.colorError);
                    } else {
                        Log.d(LOG, "Exception is null! Cause is in the saveUserData, SignUpActivity");
                        showSnackBar(errorSTR + went_wrongSTR, R.color.colorError);
                    }
                }
            });
        } else {
            hideProgress();
            showSnackBar(errorSTR + went_wrongSTR, R.color.colorError);
            Log.d(LOG, "User is null in the saveUserData, SignUpActivity");
        }
    }

    private void formatEditTexts() {
        nameET.setText("");
        emailET.setText("");
        passwordET.setText("");
    }

    private void go() {
        Intent go_intent = new Intent(SignUpActivity.this, LogInActivity.class);
        startActivity(go_intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        SignUpActivity.this.finish();
    }
}
