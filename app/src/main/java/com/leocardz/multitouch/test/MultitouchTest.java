package com.leocardz.multitouch.test;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

@SuppressLint("NewApi")
public class MultitouchTest extends ActionBarActivity {
    static int screenHeight, screenWidth, screenDensity;
    int androidVersion = Build.VERSION.SDK_INT;
    static int lines = 1, rings = 0, colorChanging = 0, numberShowing = 1,
            coordinates = 0, density = 1, vibration = 1;
    static String densityText, centerMessage, currentTouches;
    public static String APP_SHARED_PREFS = "com.leocardz.multitouch.test.Preferences";
    public static SharedPreferences preferences, settings, getPreference;
    public static Editor editor;
    public static ActionBar ab;
    private MultiTouch mv;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

        setMetrics();

        centerMessage = getString(R.string.center_message);
        densityText = getString(R.string.density_text);
        currentTouches = getString(R.string.current_touches);

        setContentView(R.layout.main);

        ab = getSupportActionBar();

        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(R.string.app_name);

        mv = new MultiTouch(this);
        setContentView(mv);

    }

    private void setMetrics() {

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            screenHeight = metrics.heightPixels;
            screenWidth = metrics.widthPixels;
        } else {
            screenHeight = metrics.heightPixels;
            screenWidth = metrics.widthPixels;
        }

        screenDensity = metrics.densityDpi;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (lines == 1)
            menu.findItem(R.id.lines).setChecked(true);

        if (coordinates == 1)
            menu.findItem(R.id.coordinates).setChecked(true);

        if (numberShowing == 1)
            menu.findItem(R.id.number_showing).setChecked(true);

        if (density == 1)
            menu.findItem(R.id.density_menu).setChecked(true);

        if (vibration == 1)
            menu.findItem(R.id.vibration).setChecked(true);

        if (colorChanging == 1)
            menu.findItem(R.id.color_changing).setChecked(true);

        if (rings == 1)
            menu.findItem(R.id.rings).setChecked(true);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.lines:
                if (!item.isChecked())
                    lines = 1;
                else
                    lines = 0;
                item.setChecked(!item.isChecked());
                break;
            case R.id.coordinates:
                if (!item.isChecked())
                    coordinates = 1;
                else
                    coordinates = 0;
                item.setChecked(!item.isChecked());
                break;
            case R.id.number_showing:
                if (!item.isChecked())
                    numberShowing = 1;
                else
                    numberShowing = 0;
                item.setChecked(!item.isChecked());
                break;
            case R.id.density_menu:
                if (!item.isChecked())
                    density = 1;
                else
                    density = 0;
                mv.invalidate();
                item.setChecked(!item.isChecked());
                break;
            case R.id.color_changing:
                if (!item.isChecked())
                    colorChanging = 1;
                else
                    colorChanging = 0;
                item.setChecked(!item.isChecked());
                break;
            case R.id.vibration:
                if (!item.isChecked())
                    vibration = 1;
                else
                    vibration = 0;
                item.setChecked(!item.isChecked());
                break;
            case R.id.rings:
                if (!item.isChecked())
                    rings = 1;
                else
                    rings = 0;
                item.setChecked(!item.isChecked());
                break;
        }
        savePrefs();
        return super.onOptionsItemSelected(item);
    }

    public class DialogSelectionClickHandler implements
            DialogInterface.OnMultiChoiceClickListener {
        public void onClick(DialogInterface dialog, int position,
                            boolean selected) {

        }
    }

    public void redirectTo(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_out_instant,
                R.anim.fade_in_instant);
        finish();
    }

    @Override
    protected void onStop() {
        savePrefs();
        super.onStop();
    }

    @Override
    protected void onPause() {
        savePrefs();
        super.onPause();
    }

    private void savePrefs() {
        settings = getSharedPreferences(APP_SHARED_PREFS, 0);
        editor = settings.edit();

        editor.putInt("lines", lines);
        editor.putInt("coordinates", coordinates);
        editor.putInt("numberShowing", numberShowing);
        editor.putInt("density", density);
        editor.putInt("vibration", vibration);
        editor.putInt("colorChanging", colorChanging);
        editor.putInt("rings", rings);

        editor.commit();
    }

    @Override
    public void onResume() {
        getPreference = getSharedPreferences(APP_SHARED_PREFS, 0);

        lines = getPreference.getInt("lines", lines);
        coordinates = getPreference.getInt("coordinates", coordinates);
        numberShowing = getPreference.getInt("numberShowing", numberShowing);
        density = getPreference.getInt("density", density);
        vibration = getPreference.getInt("vibration", vibration);
        colorChanging = getPreference.getInt("colorChanging", colorChanging);
        rings = getPreference.getInt("rings", rings);

        super.onResume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        setMetrics();
        mv.invalidate();
        super.onConfigurationChanged(newConfig);
    }
}