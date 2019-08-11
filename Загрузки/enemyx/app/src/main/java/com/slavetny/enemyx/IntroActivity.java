package com.slavetny.enemyx;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.data_security), getResources().getString(R.string.data_security_description), R.drawable.ic_protect, Color.rgb(255, 155, 0)));
        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.export_database), getResources().getString(R.string.export_database_description), R.drawable.ic_export, Color.rgb(177, 212, 0)));
        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.dark_theme), getResources().getString(R.string.dark_theme_description), R.drawable.ic_theme, Color.rgb(32, 32, 32)));
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent = new Intent(IntroActivity.this, MainActivity.class);
        startActivity(intent);
        onDestroy();
    }
}
