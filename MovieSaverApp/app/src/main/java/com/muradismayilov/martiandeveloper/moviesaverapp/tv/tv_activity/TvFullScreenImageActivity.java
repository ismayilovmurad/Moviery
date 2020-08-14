package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.common.tools.FullScreenHelper;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvImage;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_tools.TvEnlargedSliderAdapter;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvFullScreenImageActivity extends AppCompatActivity {

    // UI Components
    @BindView(R.id.enlarged_picturesSV)
    SliderView enlarged_picturesSV;

    // Variables
    // List
    private ArrayList<TvImage> tvImageList;
    // FullScreenHelper
    private FullScreenHelper fullScreenHelper;
    // Final
    public static final String VIEW_NAME_HEADER_IMAGE = "detail:image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.tv_activity_full_screen_image);
        ButterKnife.bind(this);

        initialFunctions();
    }

    private void initialFunctions() {
        ViewCompat.setTransitionName(enlarged_picturesSV, VIEW_NAME_HEADER_IMAGE);
        supportPostponeEnterTransition();
        supportStartPostponedEnterTransition();

        declareVariables();
        setImageSlider();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        fullScreenHelper.enterFullScreen();

        enlarged_picturesSV.setIndicatorAnimation(IndicatorAnimations.WORM);
        enlarged_picturesSV.setSliderTransformAnimation(SliderAnimations.ZOOMOUTTRANSFORMATION);
    }

    private void declareVariables() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        tvImageList = bundle.getParcelableArrayList("myList");
        fullScreenHelper = new FullScreenHelper(this);
    }

    private void setImageSlider() {
        // ImageSlider Adapter
        TvEnlargedSliderAdapter tvEnlargedSliderAdapter = new TvEnlargedSliderAdapter(this, tvImageList);
        enlarged_picturesSV.setSliderAdapter(tvEnlargedSliderAdapter);
    }
}
