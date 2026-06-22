package com.zoey.simpleclocklauncher;

import android.app.Activity;
import android.app.Dialog;
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.provider.Settings;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.util.Xml;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.net.URL;
import java.security.MessageDigest;
import java.text.Collator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

public class MainActivity extends Activity {
    private static final String PREFS_NAME = "launcher_settings";
    private static final String PREF_USE_24_HOUR = "use_24_hour";
    private static final String PREF_ANALOG_CLOCK = "analog_clock";
    private static final String PREF_DRAWER_COLUMNS = "drawer_columns";
    private static final String PREF_CLOCK_FONT_KEY = "clock_font_key";
    private static final String PREF_CLOCK_OUTLINE = "clock_outline";
    private static final String PREF_CLOCK_NUMBER_COLOR = "clock_number_color";
    private static final String PREF_CLOCK_OUTLINE_COLOR = "clock_outline_color";
    private static final String PREF_CLOCK_MARGIN_VERTICAL = "clock_margin_vertical";
    private static final String PREF_CLOCK_MARGIN_SIDE = "clock_margin_side";
    private static final String PREF_CLOCK_OFFSET_X = "clock_offset_x";
    private static final String PREF_CLOCK_OFFSET_Y = "clock_offset_y";
    private static final String PREF_CLOCK_ALIGN_HORIZONTAL = "clock_align_horizontal";
    private static final String PREF_CLOCK_ALIGN_VERTICAL = "clock_align_vertical";
    private static final String PREF_TINT_COLOR = "tint_color";
    private static final String PREF_TINT_OPACITY = "tint_opacity";
    private static final String PREF_BACKGROUND_STYLE = "background_style";
    private static final String PREF_SHOW_SWIPE_HINT = "show_swipe_hint";
    private static final String PREF_SHOW_DRAWER_WATERMARK = "show_drawer_watermark";
    private static final String PREF_SHADE_FIX = "shade_fix";
    private static final String PREF_FULLSCREEN_MODE = "fullscreen_mode";
    private static final String PREF_CUSTOM_BACKGROUND_URI = "custom_background_uri";
    private static final String PREF_HIDDEN_APPS = "hidden_apps";
    private static final String PREF_ICON_STYLE = "icon_style";
    private static final String PREF_ICON_PACK_PACKAGE = "icon_pack_package";
    private static final String PREF_DRAWER_ICON_SIZE_DP = "drawer_icon_size_dp";
    private static final String PREF_DRAWER_LABEL_SIZE_SP = "drawer_label_size_sp";
    private static final String PREF_DRAWER_LOCK_ENABLED = "drawer_lock_enabled";
    private static final String PREF_DRAWER_PASS_HASH = "drawer_pass_hash";
    private static final String PREF_DRAWER_LOCKOUT_SECONDS = "drawer_lockout_seconds";
    private static final String PREF_DRAWER_RELOCK_SECONDS = "drawer_relock_seconds";
    private static final String PREF_DRAWER_UNLOCKED_UNTIL = "drawer_unlocked_until";
    private static final String PREF_DRAWER_FAILED_ATTEMPTS = "drawer_failed_attempts";
    private static final String PREF_DRAWER_LOCKOUT_UNTIL = "drawer_lockout_until";
    private static final String PREF_SHOW_BATTERY_PERCENT = "show_battery_percent";
    private static final String PREF_SHOW_UNREAD_COUNTER = "show_unread_counter";
    private static final String PREF_SHOW_WEATHER = "show_weather";
    private static final String PREF_WEATHER_COUNTRY = "weather_country";
    private static final String PREF_WEATHER_POSTAL_CODE = "weather_postal_code";
    private static final String PREF_WEATHER_UNIT = "weather_unit";
    private static final String PREF_WEATHER_TEXT = "weather_text";
    private static final String PREF_WEATHER_UPDATED = "weather_updated";
    private static final String PREF_NOTIFICATION_SMS_COUNT = "notification_sms_count";
    private static final String PREF_NOTIFICATION_EMAIL_COUNT = "notification_email_count";
    private static final String ACTION_COUNTERS_UPDATED = "com.zoey.simpleclocklauncher.COUNTERS_UPDATED";

    private static final int CLOCK_ALIGN_START = 0;
    private static final int CLOCK_ALIGN_CENTER = 1;
    private static final int CLOCK_ALIGN_END = 2;
    private static final int DEFAULT_CLOCK_MARGIN_VERTICAL = 10;
    private static final int DEFAULT_CLOCK_MARGIN_SIDE = 10;
    private static final int DEFAULT_CLOCK_OFFSET = 0;
    private static final int DEFAULT_CLOCK_NUMBER_COLOR = Color.rgb(245, 232, 235);
    private static final int DEFAULT_CLOCK_OUTLINE_COLOR = Color.rgb(70, 0, 10);
    private static final int DEFAULT_TINT_COLOR = Color.rgb(82, 0, 16);
    private static final int DEFAULT_TINT_OPACITY = 70;
    private static final int BG_STYLE_SOLID = 0;
    private static final int BG_STYLE_RADIAL = 1;
    private static final int BG_STYLE_VERTICAL = 2;
    private static final int BG_STYLE_DIAGONAL = 3;
    private static final int BG_STYLE_SQUARE = 4;
    private static final int BG_STYLE_SPIRAL = 5;
    private static final int DEFAULT_BACKGROUND_STYLE = BG_STYLE_RADIAL;
    private static final int ICON_STYLE_SYSTEM = 0;
    private static final int ICON_STYLE_BUILTIN_MINIMAL = 1;
    private static final int ICON_STYLE_ADW_PACK = 2;
    private static final int DEFAULT_DRAWER_LOCKOUT_SECONDS = 60;
    private static final int DEFAULT_DRAWER_RELOCK_SECONDS = 0;
    private static final int DRAWER_LOCKOUT_AFTER_FAILS = 5;
    private static final String DEFAULT_CLOCK_FONT_KEY = "family:sans-serif-thin";
    private static final long SETTINGS_LONG_PRESS_MS = 1000L;
    private static final int REQUEST_PICK_BACKGROUND = 2407;
    private static final int REQUEST_READ_SMS = 2408;
    private static final long WEATHER_REFRESH_MS = 30L * 60L * 1000L;

    private FrameLayout root;
    private FrameLayout drawer;
    private ImageView backgroundImageView;
    private GradientOverlayView gradientOverlayView;
    private DrawerBackgroundView drawerBackgroundView;
    private TextView swipeHintView;
    private TextView drawerWatermarkView;
    private View notificationShadeTintView;
    private GridLayout appGrid;
    private TextView drawerTitle;
    private TextView appCountView;
    private EditText searchBar;
    private ScrollView appDrawerScrollView;
    private ClockFaceView clockFaceView;

    private SharedPreferences prefs;
    private boolean use24Hour = true;
    private boolean analogClock = false;
    private int drawerColumns = 0;
    private boolean clockOutline = false;
    private int clockNumberColor = DEFAULT_CLOCK_NUMBER_COLOR;
    private int clockOutlineColor = DEFAULT_CLOCK_OUTLINE_COLOR;
    private int clockMarginVertical = DEFAULT_CLOCK_MARGIN_VERTICAL;
    private int clockMarginSide = DEFAULT_CLOCK_MARGIN_SIDE;
    private int clockOffsetX = DEFAULT_CLOCK_OFFSET;
    private int clockOffsetY = DEFAULT_CLOCK_OFFSET;
    private int clockAlignHorizontal = CLOCK_ALIGN_CENTER;
    private int clockAlignVertical = CLOCK_ALIGN_CENTER;
    private int tintColor = DEFAULT_TINT_COLOR;
    private int tintOpacity = DEFAULT_TINT_OPACITY;
    private int backgroundStyle = DEFAULT_BACKGROUND_STYLE;
    private boolean showSwipeHint = true;
    private boolean showDrawerWatermark = true;
    private boolean shadeFixEnabled = true;
    private boolean fullscreenMode = true;
    private String customBackgroundUri = "";
    private int iconStyle = ICON_STYLE_SYSTEM;
    private String iconPackPackage = "";
    private int drawerIconSizeDp = 0;
    private int drawerLabelSizeSp = 0;
    private boolean drawerLockEnabled = false;
    private boolean showBatteryPercent = true;
    private boolean showUnreadCounter = false;
    private boolean showWeather = false;
    private String weatherCountry = "US";
    private String weatherPostalCode = "";
    private String weatherUnit = "F";
    private String weatherText = "";
    private long weatherUpdated = 0L;
    private boolean weatherFetchInProgress = false;
    private String drawerPassHash = "";
    private int drawerLockoutSeconds = DEFAULT_DRAWER_LOCKOUT_SECONDS;
    private int drawerRelockSeconds = DEFAULT_DRAWER_RELOCK_SECONDS;
    private int drawerFailedAttempts = 0;
    private long drawerLockoutUntil = 0L;
    private long drawerUnlockedUntil = 0L;
    private boolean unlockDialogShowing = false;
    private boolean counterReceiverRegistered = false;
    private String clockFontKey = DEFAULT_CLOCK_FONT_KEY;
    private Typeface clockTypeface = Typeface.create("sans-serif-thin", Typeface.NORMAL);

    private final List<AppEntry> allApps = new ArrayList<>();
    private final List<ClockFontOption> availableFonts = new ArrayList<>();
    private final List<AppEntry> filteredApps = new ArrayList<>();
    private final Set<String> hiddenAppKeys = new HashSet<>();
    private String currentSearch = "";

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable clockTick = new Runnable() {
        @Override
        public void run() {
            updateClock();
            scheduleNextMinuteTick();
        }
    };

    private final BroadcastReceiver counterUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateClock();
        }
    };

    private final Runnable weatherRefreshRunnable = new Runnable() {
        @Override
        public void run() {
            refreshWeatherIfNeeded(false);
            handler.postDelayed(this, WEATHER_REFRESH_MS);
        }
    };

    private float gestureStartX;
    private float gestureStartY;
    private boolean drawerVisible = false;
    private boolean possibleSettingsLongPress = false;
    private boolean settingsLongPressTriggered = false;
    private Runnable settingsLongPressRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        loadClockFonts();
        loadSettings();

        settingsLongPressRunnable = new Runnable() {
            @Override
            public void run() {
                if (possibleSettingsLongPress && !drawerVisible) {
                    settingsLongPressTriggered = true;
                    possibleSettingsLongPress = false;
                    requestOpenSettings();
                }
            }
        };

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER, WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER);
        applyFullscreenMode();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        buildHomeScreen();
        applyTintSettings();
        applyResponsiveSizing();
        loadApps();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerCounterReceiver();
        hideSystemUi();
        updateClock();
        scheduleNextMinuteTick();
        refreshWeatherIfNeeded(false);
        handler.removeCallbacks(weatherRefreshRunnable);
        handler.postDelayed(weatherRefreshRunnable, WEATHER_REFRESH_MS);
        loadApps();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(clockTick);
        handler.removeCallbacks(weatherRefreshRunnable);
        unregisterCounterReceiver();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(clockTick);
        handler.removeCallbacks(weatherRefreshRunnable);
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        applyResponsiveSizing();
        populateAppGrid(filteredApps);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        closeDrawer(false);
        hideSystemUi();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICK_BACKGROUND && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            int flags = data.getFlags() & Intent.FLAG_GRANT_READ_URI_PERMISSION;
            try {
                getContentResolver().takePersistableUriPermission(uri, flags);
            } catch (Exception ignored) {
            }
            customBackgroundUri = uri.toString();
            saveSettings();
            applyCustomBackground();
            Toast.makeText(this, "Custom background set", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "SMS counter access granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "SMS permission denied; notification access can still count visible SMS alerts", Toast.LENGTH_LONG).show();
            }
            updateClock();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUi();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerVisible) {
            closeDrawer(true);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                gestureStartX = event.getX();
                gestureStartY = event.getY();
                settingsLongPressTriggered = false;
                beginSettingsLongPressWatch();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveDx = event.getX() - gestureStartX;
                float moveDy = event.getY() - gestureStartY;
                if (Math.hypot(moveDx, moveDy) > dp(18)) {
                    cancelSettingsLongPressWatch();
                }
                break;
            case MotionEvent.ACTION_UP:
                cancelSettingsLongPressWatch();
                if (settingsLongPressTriggered) {
                    settingsLongPressTriggered = false;
                    return true;
                }
                float dx = event.getX() - gestureStartX;
                float dy = event.getY() - gestureStartY;
                int threshold = dp(72);
                boolean mostlyVertical = Math.abs(dy) > Math.abs(dx) * 1.25f;
                if (!drawerVisible && mostlyVertical && dy < -threshold) {
                    requestOpenDrawer(true);
                    return true;
                }
                if (drawerVisible && mostlyVertical && dy > threshold) {
                    // Do not treat normal app-list scrolling as a drawer-close gesture.
                    // The drawer can still be closed with Back, or by swiping down from the header/top area.
                    boolean startedInScrollableAppList = isPointInsideView(gestureStartX, gestureStartY, appDrawerScrollView);
                    boolean startedNearDrawerHeader = gestureStartY < dp(118);
                    if (!startedInScrollableAppList || startedNearDrawerHeader) {
                        closeDrawer(true);
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                cancelSettingsLongPressWatch();
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    private boolean isPointInsideView(float rawX, float rawY, View view) {
        if (view == null || view.getVisibility() != View.VISIBLE) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int[] rootLocation = new int[2];
        root.getLocationOnScreen(rootLocation);
        float screenX = rawX + rootLocation[0];
        float screenY = rawY + rootLocation[1];
        return screenX >= location[0]
                && screenX <= location[0] + view.getWidth()
                && screenY >= location[1]
                && screenY <= location[1] + view.getHeight();
    }

    private void beginSettingsLongPressWatch() {
        cancelSettingsLongPressWatch();
        if (drawerVisible || settingsLongPressRunnable == null) {
            return;
        }
        possibleSettingsLongPress = true;
        handler.postDelayed(settingsLongPressRunnable, SETTINGS_LONG_PRESS_MS);
    }

    private void cancelSettingsLongPressWatch() {
        possibleSettingsLongPress = false;
        if (settingsLongPressRunnable != null) {
            handler.removeCallbacks(settingsLongPressRunnable);
        }
    }

    private void loadSettings() {
        use24Hour = prefs.getBoolean(PREF_USE_24_HOUR, true);
        analogClock = prefs.getBoolean(PREF_ANALOG_CLOCK, false);
        drawerColumns = prefs.getInt(PREF_DRAWER_COLUMNS, 0);
        clockOutline = prefs.getBoolean(PREF_CLOCK_OUTLINE, false);
        clockNumberColor = prefs.getInt(PREF_CLOCK_NUMBER_COLOR, DEFAULT_CLOCK_NUMBER_COLOR);
        clockOutlineColor = prefs.getInt(PREF_CLOCK_OUTLINE_COLOR, DEFAULT_CLOCK_OUTLINE_COLOR);
        clockMarginVertical = clampInt(prefs.getInt(PREF_CLOCK_MARGIN_VERTICAL, DEFAULT_CLOCK_MARGIN_VERTICAL), 0, 40);
        clockMarginSide = clampInt(prefs.getInt(PREF_CLOCK_MARGIN_SIDE, DEFAULT_CLOCK_MARGIN_SIDE), 0, 40);
        clockOffsetX = clampInt(prefs.getInt(PREF_CLOCK_OFFSET_X, DEFAULT_CLOCK_OFFSET), -50, 50);
        clockOffsetY = clampInt(prefs.getInt(PREF_CLOCK_OFFSET_Y, DEFAULT_CLOCK_OFFSET), -50, 50);
        clockAlignHorizontal = clampInt(prefs.getInt(PREF_CLOCK_ALIGN_HORIZONTAL, CLOCK_ALIGN_CENTER), CLOCK_ALIGN_START, CLOCK_ALIGN_END);
        clockAlignVertical = clampInt(prefs.getInt(PREF_CLOCK_ALIGN_VERTICAL, CLOCK_ALIGN_CENTER), CLOCK_ALIGN_START, CLOCK_ALIGN_END);
        tintColor = prefs.getInt(PREF_TINT_COLOR, DEFAULT_TINT_COLOR);
        tintOpacity = clampInt(prefs.getInt(PREF_TINT_OPACITY, DEFAULT_TINT_OPACITY), 0, 100);
        backgroundStyle = clampInt(prefs.getInt(PREF_BACKGROUND_STYLE, DEFAULT_BACKGROUND_STYLE), BG_STYLE_SOLID, BG_STYLE_SPIRAL);
        showSwipeHint = prefs.getBoolean(PREF_SHOW_SWIPE_HINT, true);
        showDrawerWatermark = prefs.getBoolean(PREF_SHOW_DRAWER_WATERMARK, true);
        shadeFixEnabled = prefs.getBoolean(PREF_SHADE_FIX, true);
        fullscreenMode = prefs.getBoolean(PREF_FULLSCREEN_MODE, true);
        customBackgroundUri = prefs.getString(PREF_CUSTOM_BACKGROUND_URI, "");
        if (customBackgroundUri == null) customBackgroundUri = "";
        iconStyle = clampInt(prefs.getInt(PREF_ICON_STYLE, ICON_STYLE_SYSTEM), ICON_STYLE_SYSTEM, ICON_STYLE_ADW_PACK);
        iconPackPackage = prefs.getString(PREF_ICON_PACK_PACKAGE, "");
        if (iconPackPackage == null) iconPackPackage = "";
        if (iconStyle == ICON_STYLE_ADW_PACK && iconPackPackage.trim().length() == 0) {
            iconStyle = ICON_STYLE_SYSTEM;
        }
        drawerIconSizeDp = clampInt(prefs.getInt(PREF_DRAWER_ICON_SIZE_DP, 0), 0, 96);
        drawerLabelSizeSp = clampInt(prefs.getInt(PREF_DRAWER_LABEL_SIZE_SP, 0), 0, 24);
        hiddenAppKeys.clear();
        Set<String> savedHiddenApps = prefs.getStringSet(PREF_HIDDEN_APPS, null);
        if (savedHiddenApps != null) {
            hiddenAppKeys.addAll(savedHiddenApps);
        }
        drawerLockEnabled = prefs.getBoolean(PREF_DRAWER_LOCK_ENABLED, false);
        showBatteryPercent = prefs.getBoolean(PREF_SHOW_BATTERY_PERCENT, true);
        showUnreadCounter = prefs.getBoolean(PREF_SHOW_UNREAD_COUNTER, false);
        showWeather = prefs.getBoolean(PREF_SHOW_WEATHER, false);
        weatherCountry = prefs.getString(PREF_WEATHER_COUNTRY, "US");
        weatherPostalCode = prefs.getString(PREF_WEATHER_POSTAL_CODE, "");
        weatherUnit = prefs.getString(PREF_WEATHER_UNIT, "F");
        weatherText = prefs.getString(PREF_WEATHER_TEXT, "");
        weatherUpdated = prefs.getLong(PREF_WEATHER_UPDATED, 0L);
        if (weatherCountry == null || weatherCountry.trim().length() == 0) weatherCountry = "US";
        weatherCountry = normalizeWeatherCountry(weatherCountry);
        if (weatherPostalCode == null) weatherPostalCode = "";
        if (weatherUnit == null || !(weatherUnit.equals("F") || weatherUnit.equals("C"))) weatherUnit = "F";
        if (weatherText == null) weatherText = "";
        drawerPassHash = prefs.getString(PREF_DRAWER_PASS_HASH, "");
        drawerLockoutSeconds = clampInt(prefs.getInt(PREF_DRAWER_LOCKOUT_SECONDS, DEFAULT_DRAWER_LOCKOUT_SECONDS), 0, 1800);
        drawerRelockSeconds = clampInt(prefs.getInt(PREF_DRAWER_RELOCK_SECONDS, DEFAULT_DRAWER_RELOCK_SECONDS), 0, 1800);
        drawerFailedAttempts = clampInt(prefs.getInt(PREF_DRAWER_FAILED_ATTEMPTS, 0), 0, DRAWER_LOCKOUT_AFTER_FAILS);
        drawerLockoutUntil = prefs.getLong(PREF_DRAWER_LOCKOUT_UNTIL, 0L);
        drawerUnlockedUntil = prefs.getLong(PREF_DRAWER_UNLOCKED_UNTIL, 0L);
        if (drawerPassHash == null) drawerPassHash = "";
        clockFontKey = prefs.getString(PREF_CLOCK_FONT_KEY, DEFAULT_CLOCK_FONT_KEY);

        if (drawerLockEnabled && drawerPassHash.trim().length() == 0) {
            drawerLockEnabled = false;
        }
        if (drawerColumns < 0 || drawerColumns > 8) {
            drawerColumns = 0;
        }
        if (findClockFontIndex(clockFontKey) < 0) {
            clockFontKey = DEFAULT_CLOCK_FONT_KEY;
        }
        if (analogClock && use24Hour) {
            // Keep clock style mutually exclusive: analog, digital 24-hour, or digital 12-hour.
            use24Hour = false;
        }
        clockTypeface = makeTypefaceForKey(clockFontKey);
    }

    private void saveSettings() {
        prefs.edit()
                .putBoolean(PREF_USE_24_HOUR, use24Hour)
                .putBoolean(PREF_ANALOG_CLOCK, analogClock)
                .putInt(PREF_DRAWER_COLUMNS, drawerColumns)
                .putBoolean(PREF_CLOCK_OUTLINE, clockOutline)
                .putInt(PREF_CLOCK_NUMBER_COLOR, clockNumberColor)
                .putInt(PREF_CLOCK_OUTLINE_COLOR, clockOutlineColor)
                .putInt(PREF_CLOCK_MARGIN_VERTICAL, clockMarginVertical)
                .putInt(PREF_CLOCK_MARGIN_SIDE, clockMarginSide)
                .putInt(PREF_CLOCK_OFFSET_X, clockOffsetX)
                .putInt(PREF_CLOCK_OFFSET_Y, clockOffsetY)
                .putInt(PREF_CLOCK_ALIGN_HORIZONTAL, clockAlignHorizontal)
                .putInt(PREF_CLOCK_ALIGN_VERTICAL, clockAlignVertical)
                .putInt(PREF_TINT_COLOR, tintColor)
                .putInt(PREF_TINT_OPACITY, tintOpacity)
                .putInt(PREF_BACKGROUND_STYLE, backgroundStyle)
                .putBoolean(PREF_SHOW_SWIPE_HINT, showSwipeHint)
                .putBoolean(PREF_SHOW_DRAWER_WATERMARK, showDrawerWatermark)
                .putBoolean(PREF_SHADE_FIX, shadeFixEnabled)
                .putBoolean(PREF_FULLSCREEN_MODE, fullscreenMode)
                .putString(PREF_CUSTOM_BACKGROUND_URI, customBackgroundUri == null ? "" : customBackgroundUri)
                .putStringSet(PREF_HIDDEN_APPS, new HashSet<String>(hiddenAppKeys))
                .putInt(PREF_ICON_STYLE, iconStyle)
                .putString(PREF_ICON_PACK_PACKAGE, iconPackPackage == null ? "" : iconPackPackage)
                .putInt(PREF_DRAWER_ICON_SIZE_DP, drawerIconSizeDp)
                .putInt(PREF_DRAWER_LABEL_SIZE_SP, drawerLabelSizeSp)
                .putBoolean(PREF_DRAWER_LOCK_ENABLED, drawerLockEnabled)
                .putBoolean(PREF_SHOW_BATTERY_PERCENT, showBatteryPercent)
                .putBoolean(PREF_SHOW_UNREAD_COUNTER, showUnreadCounter)
                .putBoolean(PREF_SHOW_WEATHER, showWeather)
                .putString(PREF_WEATHER_COUNTRY, normalizeWeatherCountry(weatherCountry))
                .putString(PREF_WEATHER_POSTAL_CODE, weatherPostalCode == null ? "" : weatherPostalCode.trim())
                .putString(PREF_WEATHER_UNIT, weatherUnit == null ? "F" : weatherUnit)
                .putString(PREF_WEATHER_TEXT, weatherText == null ? "" : weatherText)
                .putLong(PREF_WEATHER_UPDATED, weatherUpdated)
                .putString(PREF_DRAWER_PASS_HASH, drawerPassHash == null ? "" : drawerPassHash)
                .putInt(PREF_DRAWER_LOCKOUT_SECONDS, drawerLockoutSeconds)
                .putInt(PREF_DRAWER_RELOCK_SECONDS, drawerRelockSeconds)
                .putInt(PREF_DRAWER_FAILED_ATTEMPTS, drawerFailedAttempts)
                .putLong(PREF_DRAWER_LOCKOUT_UNTIL, drawerLockoutUntil)
                .putLong(PREF_DRAWER_UNLOCKED_UNTIL, drawerUnlockedUntil)
                .putString(PREF_CLOCK_FONT_KEY, clockFontKey)
                .apply();
    }

    private void buildHomeScreen() {
        root = new FrameLayout(this);
        root.setBackgroundColor(Color.TRANSPARENT);
        setContentView(root, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        backgroundImageView = new ImageView(this);
        backgroundImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        backgroundImageView.setBackgroundColor(Color.TRANSPARENT);
        root.addView(backgroundImageView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        applyCustomBackground();

        gradientOverlayView = new GradientOverlayView(this);
        gradientOverlayView.setTintSettings(tintColor, tintOpacity, backgroundStyle);
        root.addView(gradientOverlayView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        notificationShadeTintView = new View(this);
        root.addView(notificationShadeTintView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                getNotificationShadeTintHeight(),
                Gravity.TOP
        ));

        clockFaceView = new ClockFaceView(this);
        clockFaceView.setClockSettings(analogClock, use24Hour);
        clockFaceView.setClockAppearance(clockTypeface, clockNumberColor, clockOutline, clockOutlineColor);
        clockFaceView.setShadowTint(tintColor, tintOpacity);
        clockFaceView.setBatteryVisible(showBatteryPercent);
        clockFaceView.setTopInfoText(buildTopInfoText());
        clockFaceView.setClockLayout(clockMarginVertical, clockMarginSide, clockOffsetX, clockOffsetY, clockAlignHorizontal, clockAlignVertical);
        clockFaceView.setLongClickable(false);
        root.addView(clockFaceView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        TextView hint = new TextView(this);
        hint.setText("swipe up");
        hint.setTextColor(Color.argb(130, 238, 220, 224));
        hint.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
        hint.setGravity(Gravity.CENTER);
        hint.setAllCaps(true);
        hint.setLetterSpacing(0.12f);
        FrameLayout.LayoutParams hintParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL
        );
        hintParams.setMargins(dp(12), 0, dp(12), dp(24));
        root.addView(hint, hintParams);

        buildDrawer();
    }

    private void applyResponsiveSizing() {
        if (clockFaceView != null) {
            clockFaceView.invalidate();
        }
    }

    private void buildDrawer() {
        drawer = new FrameLayout(this);
        drawer.setVisibility(View.GONE);
        drawer.setClickable(true);
        drawer.setFocusable(true);
        root.addView(drawer, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        drawerBackgroundView = new DrawerBackgroundView(this);
        drawerBackgroundView.setTintSettings(tintColor, tintOpacity, backgroundStyle);
        drawer.addView(drawerBackgroundView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(10), dp(22), dp(10), dp(30));
        drawer.addView(content, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        drawerTitle = new TextView(this);
        drawerTitle.setText("Apps");
        drawerTitle.setTextColor(Color.rgb(250, 232, 236));
        drawerTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        drawerTitle.setGravity(Gravity.CENTER);
        drawerTitle.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        content.addView(drawerTitle, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        appCountView = new TextView(this);
        appCountView.setTextColor(Color.argb(145, 238, 220, 224));
        appCountView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        appCountView.setGravity(Gravity.CENTER);
        appCountView.setAllCaps(true);
        appCountView.setLetterSpacing(0.10f);
        LinearLayout.LayoutParams countParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        countParams.setMargins(0, 0, 0, dp(8));
        content.addView(appCountView, countParams);

        searchBar = new EditText(this);
        searchBar.setSingleLine(true);
        searchBar.setHint("Search apps");
        searchBar.setHintTextColor(Color.argb(125, 238, 220, 224));
        searchBar.setTextColor(Color.rgb(250, 232, 236));
        searchBar.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        searchBar.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        searchBar.setPadding(dp(14), 0, dp(14), 0);
        searchBar.setBackground(makeSearchBackground());
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentSearch = s == null ? "" : s.toString();
                filterApps();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        LinearLayout.LayoutParams searchParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dp(42)
        );
        searchParams.setMargins(dp(2), 0, dp(2), dp(10));
        content.addView(searchBar, searchParams);

        ScrollView scrollView = new ScrollView(this);
        appDrawerScrollView = scrollView;
        scrollView.setFillViewport(false);
        scrollView.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
        scrollView.setClipToPadding(false);

        appGrid = new GridLayout(this);
        appGrid.setUseDefaultMargins(false);
        appGrid.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        appGrid.setPadding(0, dp(4), 0, dp(32));
        scrollView.addView(appGrid, new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        content.addView(scrollView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1f
        ));

        drawerWatermarkView = new TextView(this);
        drawerWatermarkView.setText("Made by ZoeyKL");
        drawerWatermarkView.setTextColor(Color.argb(120, 238, 220, 224));
        drawerWatermarkView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        drawerWatermarkView.setGravity(Gravity.LEFT);
        drawerWatermarkView.setIncludeFontPadding(false);
        drawerWatermarkView.setVisibility(showDrawerWatermark ? View.VISIBLE : View.GONE);
        FrameLayout.LayoutParams watermarkParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM | Gravity.LEFT
        );
        watermarkParams.setMargins(dp(12), 0, 0, dp(9));
        drawer.addView(drawerWatermarkView, watermarkParams);
    }

    private void applyTintSettings() {
        if (gradientOverlayView != null) {
            gradientOverlayView.setTintSettings(tintColor, tintOpacity, backgroundStyle);
        }
        if (drawerBackgroundView != null) {
            drawerBackgroundView.setTintSettings(tintColor, tintOpacity, backgroundStyle);
        }
        if (clockFaceView != null) {
            clockFaceView.setShadowTint(tintColor, tintOpacity);
        }
        if (notificationShadeTintView != null) {
            // Do not draw a fake notification-shade tint strip inside the launcher.
            // On Android 15/16 that strip can show up under SystemUI as a second rectangular band.
            notificationShadeTintView.setVisibility(View.GONE);
            ViewGroup.LayoutParams params = notificationShadeTintView.getLayoutParams();
            if (params != null) {
                params.height = 0;
                notificationShadeTintView.setLayoutParams(params);
            }
        }
        if (Build.VERSION.SDK_INT >= 21) {
            int barColor = shadeFixEnabled ? withOpacity(tintColor, tintOpacity) : Color.TRANSPARENT;
            getWindow().setStatusBarColor(barColor);
            getWindow().setNavigationBarColor(barColor);
        }
    }

    private int getNotificationShadeTintHeight() {
        return 0;
    }

    private int withOpacity(int color, int opacityPercent) {
        int percent = clampInt(opacityPercent, 0, 100);
        int alpha = Math.round(Color.alpha(color) * (percent / 100f));
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }

    private void updateClock() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int batteryPercent = showBatteryPercent ? readBatteryPercent() : -1;
        if (clockFaceView != null) {
            clockFaceView.setClockSettings(analogClock, use24Hour);
            clockFaceView.setClockAppearance(clockTypeface, clockNumberColor, clockOutline, clockOutlineColor);
            clockFaceView.setShadowTint(tintColor, tintOpacity);
            clockFaceView.setBatteryVisible(showBatteryPercent);
            clockFaceView.setTopInfoText(buildTopInfoText());
            clockFaceView.setClockLayout(clockMarginVertical, clockMarginSide, clockOffsetX, clockOffsetY, clockAlignHorizontal, clockAlignVertical);
            clockFaceView.setBatteryPercent(batteryPercent);
            clockFaceView.setTime(hour, minute);
        }
    }

    private int readBatteryPercent() {
        try {
            Intent batteryStatus = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            if (batteryStatus == null) {
                return -1;
            }
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            if (level < 0 || scale <= 0) {
                return -1;
            }
            return clampInt(Math.round((level * 100f) / scale), 0, 100);
        } catch (Exception ignored) {
            return -1;
        }
    }


    private void registerCounterReceiver() {
        if (counterReceiverRegistered) {
            return;
        }
        try {
            IntentFilter filter = new IntentFilter(ACTION_COUNTERS_UPDATED);
            if (Build.VERSION.SDK_INT >= 33) {
                try {
                    // Avoid referencing Context.RECEIVER_NOT_EXPORTED directly so the project
                    // still compiles on PCs that only have older Android SDK platforms installed.
                    // Numeric value 4 is RECEIVER_NOT_EXPORTED on Android 13+.
                    Context.class
                            .getMethod("registerReceiver", BroadcastReceiver.class, IntentFilter.class, int.class)
                            .invoke(this, counterUpdateReceiver, filter, 4);
                } catch (Exception reflectionFailed) {
                    registerReceiver(counterUpdateReceiver, filter);
                }
            } else {
                registerReceiver(counterUpdateReceiver, filter);
            }
            counterReceiverRegistered = true;
        } catch (Exception ignored) {
        }
    }

    private void unregisterCounterReceiver() {
        if (!counterReceiverRegistered) {
            return;
        }
        try {
            unregisterReceiver(counterUpdateReceiver);
        } catch (Exception ignored) {
        }
        counterReceiverRegistered = false;
    }

    private String buildUnreadCounterText() {
        if (!showUnreadCounter) {
            return "";
        }
        int smsCount = readUnreadSmsCount();
        boolean notificationAccess = isNotificationAccessEnabled();
        if (smsCount < 0 && notificationAccess) {
            smsCount = prefs.getInt(PREF_NOTIFICATION_SMS_COUNT, 0);
        }
        int emailCount = notificationAccess ? prefs.getInt(PREF_NOTIFICATION_EMAIL_COUNT, 0) : -1;
        ArrayList<String> parts = new ArrayList<String>();
        if (smsCount >= 0) {
            parts.add("SMS " + smsCount);
        }
        if (emailCount >= 0) {
            parts.add("EMAIL " + emailCount);
        }
        if (parts.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < parts.size(); i++) {
            if (i > 0) builder.append(" • ");
            builder.append(parts.get(i));
        }
        return builder.toString();
    }

    private String buildTopInfoText() {
        ArrayList<String> parts = new ArrayList<String>();
        String unread = buildUnreadCounterText();
        if (unread.length() > 0) {
            parts.add(unread);
        }
        String weather = buildWeatherDisplayText();
        if (weather.length() > 0) {
            parts.add(weather);
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < parts.size(); i++) {
            if (i > 0) builder.append(" • ");
            builder.append(parts.get(i));
        }
        return builder.toString();
    }

    private String buildWeatherDisplayText() {
        if (!showWeather) {
            return "";
        }
        String zip = weatherPostalCode == null ? "" : weatherPostalCode.trim();
        if (zip.length() == 0) {
            return "WEATHER: SET ZIP";
        }
        String text = weatherText == null ? "" : weatherText.trim();
        if (text.length() == 0) {
            return "WEATHER --";
        }
        return text;
    }

    private void refreshWeatherIfNeeded(boolean force) {
        if (!showWeather || weatherFetchInProgress) {
            return;
        }
        if (weatherPostalCode == null || weatherPostalCode.trim().length() == 0) {
            return;
        }
        long now = System.currentTimeMillis();
        if (!force && weatherText != null && weatherText.trim().length() > 0 && now - weatherUpdated < WEATHER_REFRESH_MS) {
            return;
        }
        weatherFetchInProgress = true;
        final String country = normalizeWeatherCountry(weatherCountry);
        final String postal = weatherPostalCode.trim();
        final String unit = ("C".equals(weatherUnit)) ? "C" : "F";
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = null;
                try {
                    result = fetchWeatherText(country, postal, unit);
                } catch (Exception ignored) {
                }
                final String finalResult = result;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        weatherFetchInProgress = false;
                        if (finalResult != null && finalResult.trim().length() > 0) {
                            weatherText = finalResult.trim();
                            weatherUpdated = System.currentTimeMillis();
                            saveSettings();
                            updateClock();
                        } else if (weatherText == null || weatherText.trim().length() == 0) {
                            weatherText = "WEATHER --";
                            weatherUpdated = System.currentTimeMillis();
                            saveSettings();
                            updateClock();
                        }
                    }
                });
            }
        }).start();
    }

    private String fetchWeatherText(String country, String postalCode, String unit) throws Exception {
        String apiCountry = zippopotamusCountryCode(country);
        String normalizedPostal = normalizeWeatherPostalForLookup(country, postalCode);
        if (normalizedPostal.length() == 0) {
            return "";
        }
        JSONObject geo = fetchJson("https://api.zippopotam.us/" + apiCountry + "/" + urlEncode(normalizedPostal));
        if ((geo == null || !geo.has("places")) && "GB".equals(apiCountry) && normalizedPostal.indexOf(' ') > 0) {
            String outward = normalizedPostal.substring(0, normalizedPostal.indexOf(' ')).trim();
            if (outward.length() > 0) {
                geo = fetchJson("https://api.zippopotam.us/" + apiCountry + "/" + urlEncode(outward));
            }
        }
        if (geo == null) {
            return "";
        }
        JSONArray places = geo.optJSONArray("places");
        if (places == null || places.length() == 0) {
            return "";
        }
        JSONObject place = places.getJSONObject(0);
        String latitude = place.optString("latitude", "");
        String longitude = place.optString("longitude", "");
        if (latitude.length() == 0 || longitude.length() == 0) {
            return "";
        }
        String unitParam = "C".equals(unit) ? "celsius" : "fahrenheit";
        String url = "https://api.open-meteo.com/v1/forecast?latitude=" + urlEncode(latitude)
                + "&longitude=" + urlEncode(longitude)
                + "&current=temperature_2m,weather_code"
                + "&temperature_unit=" + unitParam
                + "&timezone=auto";
        JSONObject forecast = fetchJson(url);
        if (forecast == null) {
            return "";
        }
        JSONObject current = forecast.optJSONObject("current");
        if (current == null) {
            return "";
        }
        if (!current.has("temperature_2m")) {
            return "";
        }
        double temp = current.optDouble("temperature_2m", Double.NaN);
        if (Double.isNaN(temp)) {
            return "";
        }
        int code = current.optInt("weather_code", -1);
        String label = weatherLabelForCode(code);
        return label + " " + Math.round(temp) + "°" + unit;
    }

    private JSONObject fetchJson(String urlText) throws Exception {
        HttpURLConnection connection = null;
        InputStream stream = null;
        try {
            URL url = new URL(urlText);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(7000);
            connection.setReadTimeout(7000);
            connection.setRequestProperty("User-Agent", "SimpleClockLauncher/1.0");
            int code = connection.getResponseCode();
            stream = (code >= 200 && code < 300) ? connection.getInputStream() : connection.getErrorStream();
            if (stream == null || code < 200 || code >= 300) {
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return new JSONObject(builder.toString());
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (Exception ignored) {
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private String normalizeWeatherCountry(String country) {
        if (country == null) return "US";
        String c = country.trim().toUpperCase(Locale.US);
        if (c.equals("CA") || c.equals("CANADA")) return "CA";
        if (c.equals("MX") || c.equals("MEXICO")) return "MX";
        if (c.equals("UK") || c.equals("GB") || c.equals("UNITED KINGDOM")) return "UK";
        return "US";
    }

    private String zippopotamusCountryCode(String country) {
        String c = normalizeWeatherCountry(country);
        if ("UK".equals(c)) return "GB";
        return c;
    }

    private String normalizeWeatherPostalForLookup(String country, String postal) {
        if (postal == null) return "";
        String p = postal.trim().toUpperCase(Locale.US).replaceAll("\\s+", " ");
        String c = normalizeWeatherCountry(country);
        if ("CA".equals(c)) {
            p = p.replaceAll("[^A-Z0-9]", "");
            if (p.length() > 3) p = p.substring(0, 3);
        }
        return p;
    }

    private String urlEncode(String value) throws Exception {
        return URLEncoder.encode(value == null ? "" : value, "UTF-8").replace("+", "%20");
    }

    private String weatherLabelForCode(int code) {
        if (code == 0) return "SUN";
        if (code == 1 || code == 2) return "PARTLY";
        if (code == 3) return "CLOUD";
        if (code == 45 || code == 48) return "FOG";
        if ((code >= 51 && code <= 67) || (code >= 80 && code <= 82)) return "RAIN";
        if ((code >= 71 && code <= 77) || (code >= 85 && code <= 86)) return "SNOW";
        if (code >= 95 && code <= 99) return "STORM";
        return "WX";
    }

    private int readUnreadSmsCount() {
        if (!hasReadSmsPermission()) {
            return -1;
        }
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(
                    Uri.parse("content://sms/inbox"),
                    new String[]{"_id"},
                    "read=0",
                    null,
                    null
            );
            return cursor == null ? -1 : Math.max(0, cursor.getCount());
        } catch (Exception ignored) {
            return -1;
        } finally {
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    private boolean hasReadSmsPermission() {
        return Build.VERSION.SDK_INT < 23 || checkSelfPermission(Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadSmsPermissionIfNeeded() {
        if (hasReadSmsPermission()) {
            Toast.makeText(this, "SMS counter access already granted", Toast.LENGTH_SHORT).show();
            updateClock();
            return;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{Manifest.permission.READ_SMS}, REQUEST_READ_SMS);
        }
    }

    private boolean isNotificationAccessEnabled() {
        try {
            String enabled = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
            if (enabled == null) {
                return false;
            }
            String packageName = getPackageName().toLowerCase(Locale.US);
            return enabled.toLowerCase(Locale.US).contains(packageName);
        } catch (Exception ignored) {
            return false;
        }
    }

    private void openNotificationAccessSettings() {
        try {
            startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        } catch (Exception e) {
            try {
                startActivity(new Intent(Settings.ACTION_SETTINGS));
            } catch (Exception ignored) {
                Toast.makeText(this, "Could not open notification access settings", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void scheduleNextMinuteTick() {
        handler.removeCallbacks(clockTick);
        long now = System.currentTimeMillis();
        long delay = 60000L - (now % 60000L) + 100L;
        handler.postDelayed(clockTick, delay);
    }

    private void requestOpenDrawer(boolean animate) {
        if (drawerVisible) return;
        if (!drawerLockEnabled || drawerPassHash == null || drawerPassHash.trim().length() == 0) {
            openDrawer(animate);
            return;
        }
        if (isDrawerTemporarilyUnlocked()) {
            openDrawer(animate);
            return;
        }
        long remainingMs = getDrawerLockoutRemainingMs();
        if (remainingMs > 0L) {
            Toast.makeText(this, "App drawer locked for " + formatLockoutRemaining(remainingMs), Toast.LENGTH_SHORT).show();
            return;
        }
        showDrawerUnlockDialog(animate);
    }

    private void openDrawer(boolean animate) {
        if (drawerVisible) return;
        drawerVisible = true;
        drawer.setVisibility(View.VISIBLE);
        drawer.bringToFront();
        hideSystemUi();
        if (animate) {
            drawer.setAlpha(0f);
            drawer.setTranslationY(dp(42));
            drawer.animate().alpha(1f).translationY(0f).setDuration(180L).start();
        } else {
            drawer.setAlpha(1f);
            drawer.setTranslationY(0f);
        }
    }

    private void closeDrawer(boolean animate) {
        if (!drawerVisible && drawer.getVisibility() != View.VISIBLE) return;
        drawerVisible = false;
        hideKeyboard();
        if (searchBar != null) {
            searchBar.clearFocus();
        }
        hideSystemUi();
        if (animate) {
            drawer.animate()
                    .alpha(0f)
                    .translationY(dp(42))
                    .setDuration(150L)
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            drawer.setVisibility(View.GONE);
                            drawer.setAlpha(1f);
                            drawer.setTranslationY(0f);
                        }
                    }).start();
        } else {
            drawer.animate().cancel();
            drawer.setVisibility(View.GONE);
            drawer.setAlpha(1f);
            drawer.setTranslationY(0f);
        }
    }

    private void loadApps() {
        final PackageManager packageManager = getPackageManager();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent queryIntent = new Intent(Intent.ACTION_MAIN, null);
                queryIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(queryIntent, 0);
                final List<AppEntry> apps = new ArrayList<>();
                final IconPackData iconPackData = (iconStyle == ICON_STYLE_ADW_PACK)
                        ? loadIconPackData(packageManager, iconPackPackage)
                        : null;

                for (ResolveInfo resolveInfo : resolveInfos) {
                    ActivityInfo activityInfo = resolveInfo.activityInfo;
                    if (activityInfo == null) continue;
                    if (getPackageName().equals(activityInfo.packageName)) continue;

                    CharSequence label = resolveInfo.loadLabel(packageManager);
                    String labelText = label == null ? activityInfo.packageName : label.toString();
                    Drawable icon = makeIconForApp(packageManager, resolveInfo, labelText, activityInfo.packageName, activityInfo.name, iconPackData);
                    Intent launchIntent = new Intent(Intent.ACTION_MAIN);
                    launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                    launchIntent.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
                    launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

                    apps.add(new AppEntry(
                            labelText,
                            icon,
                            launchIntent,
                            activityInfo.packageName,
                            activityInfo.name
                    ));
                }

                final Collator collator = Collator.getInstance(Locale.getDefault());
                Collections.sort(apps, new java.util.Comparator<AppEntry>() {
                    @Override
                    public int compare(AppEntry a, AppEntry b) {
                        return collator.compare(a.label, b.label);
                    }
                });

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        allApps.clear();
                        allApps.addAll(apps);
                        filterApps();
                    }
                });
            }
        }, "AppLoader").start();
    }


    private Drawable makeIconForApp(PackageManager packageManager, ResolveInfo resolveInfo, String label, String packageName, String activityName, IconPackData iconPackData) {
        if (iconStyle == ICON_STYLE_ADW_PACK && iconPackData != null) {
            Drawable packed = iconPackData.loadDrawableFor(this, packageName, activityName);
            if (packed != null) {
                return packed;
            }
        }

        Drawable systemIcon = null;
        try {
            systemIcon = resolveInfo.loadIcon(packageManager);
        } catch (Exception ignored) {
            systemIcon = null;
        }

        if (iconStyle == ICON_STYLE_BUILTIN_MINIMAL) {
            int bundledIcon = findBundledMinimalIconResource(label, packageName);
            if (bundledIcon != 0) {
                try {
                    if (Build.VERSION.SDK_INT >= 21) {
                        return getResources().getDrawable(bundledIcon, getTheme());
                    }
                    return getResources().getDrawable(bundledIcon);
                } catch (Exception ignored) {
                    // Fall through to the actual app icon. Recognizable beats fake every time.
                }
            }
            if (systemIcon != null) {
                return systemIcon;
            }
        }

        if (systemIcon != null) {
            return systemIcon;
        }
        return new MinimalIconDrawable(null, label, tintColor, clockNumberColor, clockOutlineColor);
    }

    private int findBundledMinimalIconResource(String label, String packageName) {
        String appLabel = label == null ? "" : label.trim().toLowerCase(Locale.US);
        String pkg = packageName == null ? "" : packageName.trim().toLowerCase(Locale.US);
        String text = appLabel + " " + pkg;

        // Popular installed apps from the bundled Zoey Minimal sheet. Keep these first so
        // "Messenger" does not become generic Messages, and "YouTube Music" does not become YouTube.
        if (containsAny(pkg, "com.whatsapp.w4b") || containsAny(appLabel, "whatsapp business")) return R.drawable.zm_whatsapp_business;
        if (containsAny(pkg, "com.whatsapp") || appLabel.equals("whatsapp")) return R.drawable.zm_whatsapp;
        if (containsAny(pkg, "com.facebook.orca") || appLabel.equals("messenger")) return R.drawable.zm_messenger;
        if (containsAny(pkg, "org.telegram.messenger", "org.thunderdog.challegram") || containsAny(appLabel, "telegram")) return R.drawable.zm_telegram;
        if (containsAny(pkg, "com.discord") || containsAny(appLabel, "discord")) return R.drawable.zm_discord;
        if (containsAny(pkg, "com.reddit.frontpage") || appLabel.equals("reddit")) return R.drawable.zm_reddit;
        if (containsAny(pkg, "com.twitter.android")) return containsAny(appLabel, "twitter") ? R.drawable.zm_twitter : R.drawable.zm_x;
        if (appLabel.equals("x")) return R.drawable.zm_x;
        if (appLabel.equals("twitter")) return R.drawable.zm_twitter;
        if (containsAny(pkg, "com.facebook.katana", "com.facebook.lite") || appLabel.equals("facebook")) return R.drawable.zm_facebook;
        if (containsAny(pkg, "com.instagram.android") || containsAny(appLabel, "instagram")) return R.drawable.zm_instagram;
        if (containsAny(pkg, "com.snapchat.android") || containsAny(appLabel, "snapchat")) return R.drawable.zm_snapchat;
        if (containsAny(pkg, "com.zhiliaoapp.musically", "com.ss.android.ugc.trill") || containsAny(appLabel, "tiktok", "tik tok")) return R.drawable.zm_tiktok;
        if (containsAny(pkg, "com.pinterest") || containsAny(appLabel, "pinterest")) return R.drawable.zm_pinterest;
        if (containsAny(pkg, "com.linkedin.android") || containsAny(appLabel, "linkedin", "linked in")) return R.drawable.zm_linkedin;
        if (containsAny(pkg, "com.spotify.music") || appLabel.equals("spotify")) return R.drawable.zm_spotify;
        if (containsAny(pkg, "com.netflix.mediaclient") || appLabel.equals("netflix")) return R.drawable.zm_netflix;
        if (containsAny(pkg, "tv.twitch.android") || containsAny(appLabel, "twitch")) return R.drawable.zm_twitch;
        if (containsAny(pkg, "com.google.android.apps.youtube.music") || containsAny(appLabel, "youtube music", "yt music")) return R.drawable.zm_youtube_music;
        if (containsAny(pkg, "com.amazon.mshop", "com.amazon.windowshop") || appLabel.equals("amazon")) return R.drawable.zm_amazon;
        if (containsAny(pkg, "com.ubercab") || appLabel.equals("uber")) return R.drawable.zm_uber;
        if (containsAny(pkg, "com.airbnb.android") || containsAny(appLabel, "airbnb")) return R.drawable.zm_airbnb;
        if (containsAny(pkg, "com.slack") || appLabel.equals("slack")) return R.drawable.zm_slack;
        if (containsAny(pkg, "us.zoom.videomeetings") || appLabel.equals("zoom")) return R.drawable.zm_zoom;
        if (containsAny(pkg, "com.disney.disneyplus") || containsAny(appLabel, "disney+", "disney plus")) return R.drawable.zm_disneyplus;
        if (containsAny(pkg, "com.kiloo.subwaysurf", "com.sybo.subwaysurfers") || containsAny(appLabel, "subway surfers")) return R.drawable.zm_subway_surfers;

        // Android / Google defaults from the same sheet.
        if (containsAny(pkg, "com.android.vending") || appLabel.equals("play store") || appLabel.equals("google play store")) return R.drawable.zm_play_store;
        if (containsAny(pkg, "com.android.chrome") || appLabel.equals("chrome")) return R.drawable.zm_chrome;
        if (containsAny(pkg, "com.google.android.gm") || appLabel.equals("gmail")) return R.drawable.zm_gmail;
        if (containsAny(pkg, "com.google.android.apps.maps") || appLabel.equals("maps") || appLabel.equals("google maps")) return R.drawable.zm_maps;
        if (containsAny(pkg, "com.google.android.youtube") || appLabel.equals("youtube")) return R.drawable.zm_youtube;
        if (containsAny(pkg, "com.google.android.apps.docs") || appLabel.equals("drive") || appLabel.equals("google drive")) return R.drawable.zm_drive;
        if (containsAny(pkg, "com.google.android.apps.photos") || appLabel.equals("photos") || appLabel.equals("google photos")) return R.drawable.zm_photos;
        if (containsAny(pkg, "com.google.android.keep") || containsAny(appLabel, "keep", "notes keep", "google keep")) return R.drawable.zm_keep;
        if (containsAny(pkg, "com.google.android.videos") || containsAny(appLabel, "google tv", "play movies", "movies & tv")) return R.drawable.zm_google_tv;
        if (containsAny(pkg, "com.google.android.apps.walletnfcrel", "com.google.android.gms.tapandpay") || containsAny(appLabel, "wallet", "google wallet", "pay")) return R.drawable.zm_wallet;
        if (containsAny(pkg, "com.google.android.apps.magazines") || appLabel.equals("news") || appLabel.equals("google news")) return R.drawable.zm_news;
        if (containsAny(pkg, "com.google.android.apps.safety", "com.google.android.apps.safetycenter", "com.google.android.apps.safetyhub") || containsAny(appLabel, "safety", "personal safety")) return R.drawable.zm_safety;
        if (containsAny(pkg, "com.google.android.apps.googleassistant") || appLabel.equals("assistant") || appLabel.equals("google assistant")) return R.drawable.zm_assistant;
        if (containsAny(pkg, "com.google.android.googlequicksearchbox")) {
            return containsAny(appLabel, "assistant") ? R.drawable.zm_assistant : R.drawable.zm_google;
        }
        if (appLabel.equals("google")) return R.drawable.zm_google;

        // Generic system categories. These are deliberately conservative; if it is not a clear match, fall back to the app's own icon.
        if (containsAny(pkg, "com.google.android.dialer", "com.android.dialer", "com.samsung.android.dialer", "com.android.phone") || containsAny(appLabel, "phone", "dialer")) return R.drawable.zm_phone;
        if (containsAny(pkg, "com.google.android.apps.messaging", "com.android.mms", "com.samsung.android.messaging") || containsAny(appLabel, "messages", "messaging", "sms", "mms")) return R.drawable.zm_messages;
        if (containsAny(pkg, "com.google.android.contacts", "com.android.contacts", "com.samsung.android.app.contacts") || containsAny(appLabel, "contacts", "people")) return R.drawable.zm_contacts;
        if (containsAny(pkg, "camera", "googlecamera", "snapcam") || appLabel.equals("camera")) return R.drawable.zm_camera;
        if (containsAny(pkg, "com.google.android.apps.nbu.files", "com.google.android.documentsui", "com.android.documentsui", "com.sec.android.app.myfiles") || containsAny(appLabel, "files", "my files", "file manager")) return R.drawable.zm_files;
        if (containsAny(pkg, "com.google.android.calendar", "com.samsung.android.calendar", "com.android.calendar") || containsAny(appLabel, "calendar")) return R.drawable.zm_calendar;
        if (containsAny(pkg, "com.google.android.deskclock", "com.android.deskclock") || containsAny(appLabel, "clock", "timer", "stopwatch")) return R.drawable.zm_clock;
        if (containsAny(pkg, "calculator") || containsAny(appLabel, "calculator", "calc")) return R.drawable.zm_calculator;
        if (containsAny(pkg, "com.android.settings") || appLabel.equals("settings")) return R.drawable.zm_settings;
        if (containsAny(pkg, "recorder", "voicenote", "soundrecorder") || containsAny(appLabel, "recorder", "voice recorder", "sound recorder")) return R.drawable.zm_recorder;
        if (containsAny(pkg, "weather", "daemonapp") || containsAny(appLabel, "weather", "forecast")) return R.drawable.zm_weather;

        return 0;
    }

    private boolean containsAny(String text, String... needles) {
        if (text == null) return false;
        for (String needle : needles) {
            if (needle != null && needle.length() > 0 && text.contains(needle)) {
                return true;
            }
        }
        return false;
    }

    private List<IconPackOption> buildIconPackOptions() {
        ArrayList<IconPackOption> options = new ArrayList<IconPackOption>();
        options.add(new IconPackOption("System app icons", ICON_STYLE_SYSTEM, ""));
        options.add(new IconPackOption("Zoey Minimal (bundled icons)", ICON_STYLE_BUILTIN_MINIMAL, ""));
        for (IconPackOption option : findInstalledAdwIconPacks()) {
            options.add(option);
        }
        return options;
    }

    private int findIconPackOptionIndex(List<IconPackOption> options) {
        if (options == null || options.isEmpty()) return 0;
        for (int i = 0; i < options.size(); i++) {
            IconPackOption option = options.get(i);
            if (option.style == iconStyle) {
                if (iconStyle != ICON_STYLE_ADW_PACK || safeString(option.packageName).equals(safeString(iconPackPackage))) {
                    return i;
                }
            }
        }
        if (iconStyle == ICON_STYLE_BUILTIN_MINIMAL && options.size() > 1) return 1;
        return 0;
    }

    private List<IconPackOption> findInstalledAdwIconPacks() {
        PackageManager pm = getPackageManager();
        LinkedHashMap<String, IconPackOption> packs = new LinkedHashMap<String, IconPackOption>();
        String[] actions = new String[]{
                "org.adw.launcher.THEMES",
                "com.novalauncher.THEME",
                "com.teslacoilsw.launcher.THEME",
                "com.anddoes.launcher.THEME",
                "com.gau.go.launcherex.theme"
        };
        for (String action : actions) {
            try {
                Intent intent = new Intent(action);
                List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
                for (ResolveInfo info : infos) {
                    if (info == null || info.activityInfo == null) continue;
                    String pkg = info.activityInfo.packageName;
                    if (pkg == null || pkg.equals(getPackageName()) || packs.containsKey(pkg)) continue;
                    CharSequence label = info.loadLabel(pm);
                    String name = label == null ? pkg : label.toString();
                    packs.put(pkg, new IconPackOption("Pack: " + name, ICON_STYLE_ADW_PACK, pkg));
                }
            } catch (Exception ignored) {
            }
        }
        ArrayList<IconPackOption> result = new ArrayList<IconPackOption>(packs.values());
        final Collator collator = Collator.getInstance(Locale.getDefault());
        Collections.sort(result, new java.util.Comparator<IconPackOption>() {
            @Override
            public int compare(IconPackOption a, IconPackOption b) {
                return collator.compare(a.label, b.label);
            }
        });
        return result;
    }

    private IconPackData loadIconPackData(PackageManager pm, String packageName) {
        if (packageName == null || packageName.trim().length() == 0) return null;
        try {
            Resources res = pm.getResourcesForApplication(packageName);
            IconPackData data = new IconPackData(packageName, res);
            boolean parsed = false;
            int xmlId = res.getIdentifier("appfilter", "xml", packageName);
            if (xmlId != 0) {
                XmlResourceParser parser = null;
                try {
                    parser = res.getXml(xmlId);
                    parseIconPackAppFilter(parser, data);
                    parsed = true;
                } finally {
                    if (parser != null) parser.close();
                }
            }
            int rawId = res.getIdentifier("appfilter", "raw", packageName);
            if (rawId != 0) {
                InputStream in = null;
                try {
                    in = res.openRawResource(rawId);
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setInput(in, "UTF-8");
                    parseIconPackAppFilter(parser, data);
                    parsed = true;
                } finally {
                    if (in != null) {
                        try { in.close(); } catch (Exception ignored) {}
                    }
                }
            }
            if (!parsed) {
                String[] assetNames = new String[]{"appfilter.xml", "assets/appfilter.xml", "xml/appfilter.xml"};
                for (String name : assetNames) {
                    InputStream in = null;
                    try {
                        in = res.getAssets().open(name);
                        XmlPullParser parser = Xml.newPullParser();
                        parser.setInput(in, "UTF-8");
                        parseIconPackAppFilter(parser, data);
                        parsed = true;
                        break;
                    } catch (Exception ignored) {
                    } finally {
                        if (in != null) {
                            try { in.close(); } catch (Exception ignored) {}
                        }
                    }
                }
            }
            return parsed && !data.componentToDrawable.isEmpty() ? data : null;
        } catch (Exception ignored) {
            return null;
        }
    }

    private void parseIconPackAppFilter(XmlPullParser parser, IconPackData data) throws Exception {
        int type;
        while ((type = parser.next()) != XmlPullParser.END_DOCUMENT) {
            if (type != XmlPullParser.START_TAG) continue;
            String tag = parser.getName();
            if (!"item".equalsIgnoreCase(tag)) continue;
            String component = parser.getAttributeValue(null, "component");
            String drawable = parser.getAttributeValue(null, "drawable");
            if (component == null || drawable == null) continue;
            String key = normalizeIconPackComponent(component);
            if (key.length() > 0 && drawable.trim().length() > 0) {
                data.componentToDrawable.put(key, drawable.trim());
            }
        }
    }

    private String normalizeIconPackComponent(String component) {
        if (component == null) return "";
        String value = component.trim();
        if (value.startsWith("ComponentInfo{")) {
            value = value.substring("ComponentInfo{".length());
        }
        if (value.endsWith("}")) {
            value = value.substring(0, value.length() - 1);
        }
        return value.trim().toLowerCase(Locale.US);
    }

    private void filterApps() {
        filteredApps.clear();
        String query = currentSearch == null ? "" : currentSearch.trim().toLowerCase(Locale.US);
        for (AppEntry app : allApps) {
            if (isAppHidden(app)) {
                continue;
            }
            if (query.length() == 0) {
                filteredApps.add(app);
            } else {
                String label = app.label == null ? "" : app.label.toLowerCase(Locale.US);
                String packageName = app.packageName == null ? "" : app.packageName.toLowerCase(Locale.US);
                if (label.contains(query) || packageName.contains(query)) {
                    filteredApps.add(app);
                }
            }
        }
        populateAppGrid(filteredApps);
    }

    private void populateAppGrid(List<AppEntry> apps) {
        if (appGrid == null || appCountView == null) return;
        appGrid.removeAllViews();

        int width = getResources().getDisplayMetrics().widthPixels;
        float density = Math.max(1f, getResources().getDisplayMetrics().density);
        float widthDp = width / density;
        int columns;
        if (drawerColumns > 0) {
            columns = drawerColumns;
        } else {
            int minCellWidth = dp(widthDp < 260f ? 70 : 88);
            columns = Math.max(1, width / Math.max(1, minCellWidth));
            if (widthDp >= 320f) columns = Math.max(3, columns);
            if (widthDp >= 520f) columns = Math.max(5, columns);
        }
        columns = Math.max(1, Math.min(8, columns));
        appGrid.setColumnCount(columns);
        int cellWidth = Math.max(1, width / columns);
        int iconSize = drawerIconSizeDp > 0
                ? dp(drawerIconSizeDp)
                : Math.max(dp(22), Math.min(dp(48), cellWidth - dp(20)));
        iconSize = Math.max(dp(18), Math.min(dp(112), iconSize));
        int labelTextSize = drawerLabelSizeSp > 0 ? drawerLabelSizeSp : (widthDp < 210f ? 9 : 11);
        int cellHeight = Math.max(dp(58), Math.min(dp(150), iconSize + dp(widthDp < 210f ? 48 : 58)));

        int hiddenCount = countHiddenApps();
        int visibleTotal = Math.max(0, allApps.size() - hiddenCount);
        if (currentSearch != null && currentSearch.trim().length() > 0) {
            appCountView.setText(apps.size() + " of " + visibleTotal + " apps");
        } else {
            String columnText = drawerColumns > 0 ? " • " + drawerColumns + " columns" : " • auto columns";
            String hiddenText = hiddenCount > 0 ? " • " + hiddenCount + " hidden" : "";
            appCountView.setText(apps.size() + " visible apps" + columnText + hiddenText);
        }

        if (apps.isEmpty()) {
            TextView empty = new TextView(this);
            empty.setText(currentSearch == null || currentSearch.trim().length() == 0 ? "No launchable apps found" : "No matching apps");
            empty.setTextColor(Color.rgb(245, 232, 235));
            empty.setGravity(Gravity.CENTER);
            empty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            appGrid.addView(empty, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    dp(120)
            ));
            return;
        }

        for (final AppEntry app : apps) {
            LinearLayout cell = new LinearLayout(this);
            cell.setOrientation(LinearLayout.VERTICAL);
            cell.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            cell.setPadding(dp(5), dp(8), dp(5), dp(5));
            cell.setClickable(true);
            cell.setFocusable(true);
            cell.setBackground(makeSelectableBackground());
            cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchApp(app);
                }
            });
            cell.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showAppActionsDialog(app);
                    return true;
                }
            });

            ImageView icon = new ImageView(this);
            icon.setImageDrawable(app.icon);
            icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
            LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(iconSize, iconSize);
            iconParams.gravity = Gravity.CENTER_HORIZONTAL;
            cell.addView(icon, iconParams);

            TextView label = new TextView(this);
            label.setText(app.label);
            label.setTextColor(Color.rgb(242, 226, 230));
            label.setTextSize(TypedValue.COMPLEX_UNIT_SP, labelTextSize);
            label.setGravity(Gravity.CENTER);
            label.setMaxLines(2);
            label.setEllipsize(TextUtils.TruncateAt.END);
            label.setIncludeFontPadding(true);
            LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            labelParams.setMargins(0, dp(6), 0, 0);
            cell.addView(label, labelParams);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = cellWidth;
            params.height = cellHeight;
            params.setMargins(0, 0, 0, dp(2));
            appGrid.addView(cell, params);
        }
    }

    private void launchApp(AppEntry app) {
        if (app == null) return;
        try {
            startActivity(app.launchIntent);
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Could not open " + app.label, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isAppHidden(AppEntry app) {
        return app != null && app.key != null && hiddenAppKeys.contains(app.key);
    }

    private int countHiddenApps() {
        int count = 0;
        for (AppEntry app : allApps) {
            if (isAppHidden(app)) count++;
        }
        return count;
    }

    private void hideApp(AppEntry app) {
        if (app == null || app.key == null) return;
        hiddenAppKeys.add(app.key);
        saveSettings();
        filterApps();
        Toast.makeText(this, app.label + " hidden", Toast.LENGTH_SHORT).show();
    }

    private void unhideApp(AppEntry app) {
        if (app == null || app.key == null) return;
        hiddenAppKeys.remove(app.key);
        saveSettings();
        filterApps();
        Toast.makeText(this, app.label + " unhidden", Toast.LENGTH_SHORT).show();
    }

    private void showAppActionsDialog(final AppEntry app) {
        if (app == null) return;
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LinearLayout panel = new LinearLayout(this);
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setPadding(dp(18), dp(16), dp(18), dp(14));
        panel.setBackground(makePanelBackground());

        TextView title = new TextView(this);
        title.setText(app.label);
        title.setTextColor(Color.rgb(250, 232, 236));
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        title.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        title.setSingleLine(false);
        panel.addView(title, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        TextView pkg = new TextView(this);
        pkg.setText(app.packageName);
        pkg.setTextColor(Color.argb(145, 238, 220, 224));
        pkg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
        LinearLayout.LayoutParams pkgParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        pkgParams.setMargins(0, dp(1), 0, dp(10));
        panel.addView(pkg, pkgParams);

        Button launchButton = makeActionButton("Launch");
        Button infoButton = makeActionButton("App info");
        Button uninstallButton = makeActionButton("Uninstall");
        Button hideButton = makeActionButton("Hide from drawer");
        Button cancelButton = makeActionButton("Cancel");
        panel.addView(launchButton, actionButtonParams());
        panel.addView(infoButton, actionButtonParams());
        panel.addView(uninstallButton, actionButtonParams());
        panel.addView(hideButton, actionButtonParams());
        panel.addView(cancelButton, actionButtonParams());

        launchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                launchApp(app);
            }
        });
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                openAppInfo(app);
            }
        });
        uninstallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                requestUninstall(app);
            }
        });
        hideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                hideApp(app);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(panel);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.setOnDismissListener(new android.content.DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(android.content.DialogInterface dialogInterface) {
                hideSystemUi();
            }
        });
        dialog.show();
        Window shownWindow = dialog.getWindow();
        if (shownWindow != null) {
            shownWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(shownWindow.getAttributes());
            lp.width = Math.max(dp(170), Math.min(getResources().getDisplayMetrics().widthPixels - dp(24), dp(380)));
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            shownWindow.setAttributes(lp);
        }
    }

    private Button makeActionButton(String text) {
        Button button = new Button(this);
        button.setText(text);
        button.setTextColor(Color.rgb(60, 0, 10));
        return button;
    }

    private LinearLayout.LayoutParams actionButtonParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, dp(2), 0, dp(2));
        return params;
    }

    private void openAppInfo(AppEntry app) {
        if (app == null || app.packageName == null) return;
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + app.packageName));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Could not open app info", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestUninstall(AppEntry app) {
        if (app == null || app.packageName == null) return;
        try {
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:" + app.packageName));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Could not start uninstall", Toast.LENGTH_SHORT).show();
        }
    }

    private long getDrawerLockoutRemainingMs() {
        long now = System.currentTimeMillis();
        if (drawerLockoutUntil <= now) {
            if (drawerLockoutUntil != 0L) {
                drawerLockoutUntil = 0L;
                drawerFailedAttempts = 0;
                saveSettings();
            }
            return 0L;
        }
        return drawerLockoutUntil - now;
    }

    private String formatLockoutRemaining(long remainingMs) {
        long seconds = Math.max(1L, (remainingMs + 999L) / 1000L);
        if (seconds < 60L) {
            return seconds + "s";
        }
        long minutes = seconds / 60L;
        long rest = seconds % 60L;
        if (rest == 0L) {
            return minutes + "m";
        }
        return minutes + "m " + rest + "s";
    }

    private String makeDrawerLockStatusText() {
        boolean hasPass = drawerPassHash != null && drawerPassHash.length() > 0;
        if (!drawerLockEnabled) {
            return hasPass ? "Stored passcode is set, but app drawer lock is off." : "No passcode set.";
        }
        long remaining = getDrawerLockoutRemainingMs();
        if (remaining > 0L) {
            return "Locked out for " + formatLockoutRemaining(remaining) + ".";
        }
        if (hasPass && drawerUnlockedUntil > System.currentTimeMillis()) {
            return "Unlocked for " + formatLockoutRemaining(drawerUnlockedUntil - System.currentTimeMillis()) + ".";
        }
        return hasPass ? "App drawer is locked." : "Turned on, but no passcode has been set yet.";
    }

    private int findLockoutIndex(int seconds) {
        if (seconds <= 0) return 0;
        if (seconds <= 15) return 1;
        if (seconds <= 30) return 2;
        if (seconds <= 60) return 3;
        if (seconds <= 300) return 4;
        if (seconds <= 900) return 5;
        return 6;
    }

    private boolean isDrawerTemporarilyUnlocked() {
        if (drawerUnlockedUntil <= 0L) return false;
        long now = System.currentTimeMillis();
        if (drawerUnlockedUntil > now) return true;
        drawerUnlockedUntil = 0L;
        saveSettings();
        return false;
    }

    private void markDrawerTemporarilyUnlocked() {
        if (drawerRelockSeconds <= 0) {
            drawerUnlockedUntil = 0L;
        } else {
            drawerUnlockedUntil = System.currentTimeMillis() + drawerRelockSeconds * 1000L;
        }
        saveSettings();
    }

    private int findRelockIndex(int seconds) {
        if (seconds <= 0) return 0;
        if (seconds <= 15) return 1;
        if (seconds <= 30) return 2;
        if (seconds <= 60) return 3;
        if (seconds <= 300) return 4;
        if (seconds <= 900) return 5;
        return 6;
    }

    private String hashPasscode(String passcode) {
        if (passcode == null) return "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(("SimpleClockLauncher:" + passcode).getBytes());
            StringBuilder builder = new StringBuilder(bytes.length * 2);
            for (byte b : bytes) {
                builder.append(String.format(Locale.US, "%02x", b & 0xff));
            }
            return builder.toString();
        } catch (Exception ignored) {
            return "fallback:" + passcode;
        }
    }

    private boolean passcodeMatches(String passcode) {
        if (drawerPassHash == null || drawerPassHash.length() == 0) return false;
        String candidate = hashPasscode(passcode);
        if (candidate.length() != drawerPassHash.length()) return false;
        int diff = 0;
        for (int i = 0; i < candidate.length(); i++) {
            diff |= candidate.charAt(i) ^ drawerPassHash.charAt(i);
        }
        return diff == 0;
    }

    private EditText makePasscodeInput(String hint) {
        EditText editText = new EditText(this);
        editText.setSingleLine(true);
        editText.setHint(hint);
        editText.setHintTextColor(Color.argb(125, 238, 220, 224));
        editText.setTextColor(Color.rgb(250, 232, 236));
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        editText.setGravity(Gravity.CENTER);
        editText.setSelectAllOnFocus(true);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        editText.setPadding(dp(12), 0, dp(12), 0);
        editText.setBackground(makeSearchBackground());
        return editText;
    }

    private void showSetDrawerPasscodeDialog(final Runnable afterSet) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LinearLayout panel = new LinearLayout(this);
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setPadding(dp(18), dp(16), dp(18), dp(14));
        panel.setBackground(makePanelBackground());

        TextView title = new TextView(this);
        title.setText("Set app drawer passcode");
        title.setTextColor(Color.rgb(250, 232, 236));
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        title.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        panel.addView(title, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        TextView hint = new TextView(this);
        hint.setText("Use at least 4 digits. This protects the app drawer only, not Android settings.");
        hint.setTextColor(Color.argb(145, 238, 220, 224));
        hint.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        LinearLayout.LayoutParams hintParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        hintParams.setMargins(0, dp(2), 0, dp(10));
        panel.addView(hint, hintParams);

        final EditText first = makePasscodeInput("New passcode");
        panel.addView(first, editParams());
        final EditText second = makePasscodeInput("Confirm passcode");
        LinearLayout.LayoutParams secondParams = editParams();
        secondParams.setMargins(0, dp(6), 0, dp(8));
        panel.addView(second, secondParams);

        LinearLayout buttons = new LinearLayout(this);
        buttons.setOrientation(LinearLayout.HORIZONTAL);
        buttons.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);

        Button cancelButton = new Button(this);
        cancelButton.setText("Cancel");
        cancelButton.setTextColor(Color.rgb(60, 0, 10));
        buttons.addView(cancelButton, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        Button saveButton = new Button(this);
        saveButton.setText("Save");
        saveButton.setTextColor(Color.rgb(60, 0, 10));
        LinearLayout.LayoutParams saveParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        saveParams.setMargins(dp(8), 0, 0, 0);
        buttons.addView(saveButton, saveParams);
        panel.addView(buttons, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = first.getText() == null ? "" : first.getText().toString().trim();
                String b = second.getText() == null ? "" : second.getText().toString().trim();
                if (a.length() < 4) {
                    Toast.makeText(MainActivity.this, "Use at least 4 digits", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!a.equals(b)) {
                    Toast.makeText(MainActivity.this, "Passcodes do not match", Toast.LENGTH_SHORT).show();
                    return;
                }
                drawerPassHash = hashPasscode(a);
                drawerLockEnabled = true;
                drawerFailedAttempts = 0;
                drawerLockoutUntil = 0L;
                drawerUnlockedUntil = 0L;
                saveSettings();
                if (afterSet != null) {
                    afterSet.run();
                }
                dialog.dismiss();
            }
        });

        dialog.setContentView(panel);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.setOnDismissListener(new android.content.DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(android.content.DialogInterface dialogInterface) {
                hideSystemUi();
            }
        });
        dialog.show();
        Window shownWindow = dialog.getWindow();
        if (shownWindow != null) {
            shownWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(shownWindow.getAttributes());
            lp.width = Math.max(dp(190), Math.min(getResources().getDisplayMetrics().widthPixels - dp(24), dp(390)));
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            shownWindow.setAttributes(lp);
        }
        first.requestFocus();
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.showSoftInput(first, InputMethodManager.SHOW_IMPLICIT);
        } catch (Exception ignored) {
        }
    }

    private void showDrawerUnlockDialog(final boolean animate) {
        if (unlockDialogShowing) return;
        unlockDialogShowing = true;
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LinearLayout panel = new LinearLayout(this);
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setPadding(dp(18), dp(16), dp(18), dp(14));
        panel.setBackground(makePanelBackground());

        TextView title = new TextView(this);
        title.setText("App drawer locked");
        title.setTextColor(Color.rgb(250, 232, 236));
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        title.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        panel.addView(title, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        final TextView status = new TextView(this);
        status.setText("Enter passcode to open apps.");
        status.setTextColor(Color.argb(145, 238, 220, 224));
        status.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        LinearLayout.LayoutParams statusParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        statusParams.setMargins(0, dp(2), 0, dp(10));
        panel.addView(status, statusParams);

        final EditText passcodeInput = makePasscodeInput("Passcode");
        panel.addView(passcodeInput, editParams());

        LinearLayout buttons = new LinearLayout(this);
        buttons.setOrientation(LinearLayout.HORIZONTAL);
        buttons.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams buttonRowParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        buttonRowParams.setMargins(0, dp(8), 0, 0);

        Button cancelButton = new Button(this);
        cancelButton.setText("Cancel");
        cancelButton.setTextColor(Color.rgb(60, 0, 10));
        buttons.addView(cancelButton, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        Button unlockButton = new Button(this);
        unlockButton.setText("Unlock");
        unlockButton.setTextColor(Color.rgb(60, 0, 10));
        LinearLayout.LayoutParams unlockParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        unlockParams.setMargins(dp(8), 0, 0, 0);
        buttons.addView(unlockButton, unlockParams);
        panel.addView(buttons, buttonRowParams);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        unlockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String entered = passcodeInput.getText() == null ? "" : passcodeInput.getText().toString().trim();
                if (passcodeMatches(entered)) {
                    drawerFailedAttempts = 0;
                    drawerLockoutUntil = 0L;
                    markDrawerTemporarilyUnlocked();
                    dialog.dismiss();
                    openDrawer(animate);
                    return;
                }
                drawerFailedAttempts = clampInt(drawerFailedAttempts + 1, 0, DRAWER_LOCKOUT_AFTER_FAILS);
                int attemptsLeft = Math.max(0, DRAWER_LOCKOUT_AFTER_FAILS - drawerFailedAttempts);
                if (attemptsLeft <= 0 && drawerLockoutSeconds > 0) {
                    drawerLockoutUntil = System.currentTimeMillis() + drawerLockoutSeconds * 1000L;
                    drawerFailedAttempts = 0;
                    saveSettings();
                    status.setText("Locked for " + formatLockoutRemaining(getDrawerLockoutRemainingMs()) + ".");
                    Toast.makeText(MainActivity.this, "App drawer locked for " + formatLockoutRemaining(getDrawerLockoutRemainingMs()), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    if (attemptsLeft <= 0) {
                        drawerFailedAttempts = 0;
                        attemptsLeft = DRAWER_LOCKOUT_AFTER_FAILS;
                    }
                    saveSettings();
                    passcodeInput.setText("");
                    status.setText("Wrong passcode. " + attemptsLeft + " tries left.");
                }
            }
        });

        dialog.setContentView(panel);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.setOnDismissListener(new android.content.DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(android.content.DialogInterface dialogInterface) {
                unlockDialogShowing = false;
                hideSystemUi();
            }
        });
        dialog.show();
        Window shownWindow = dialog.getWindow();
        if (shownWindow != null) {
            shownWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(shownWindow.getAttributes());
            lp.width = Math.max(dp(190), Math.min(getResources().getDisplayMetrics().widthPixels - dp(24), dp(360)));
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            shownWindow.setAttributes(lp);
        }
        passcodeInput.requestFocus();
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.showSoftInput(passcodeInput, InputMethodManager.SHOW_IMPLICIT);
        } catch (Exception ignored) {
        }
    }

    private void requestOpenSettings() {
        cancelSettingsLongPressWatch();
        if (!drawerLockEnabled || drawerPassHash == null || drawerPassHash.trim().length() == 0) {
            showSettingsDialog();
            return;
        }
        if (isDrawerTemporarilyUnlocked()) {
            showSettingsDialog();
            return;
        }
        long remainingMs = getDrawerLockoutRemainingMs();
        if (remainingMs > 0L) {
            Toast.makeText(this, "Settings locked for " + formatLockoutRemaining(remainingMs), Toast.LENGTH_SHORT).show();
            return;
        }
        showSettingsUnlockDialog();
    }

    private void showSettingsUnlockDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LinearLayout panel = new LinearLayout(this);
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setPadding(dp(18), dp(16), dp(18), dp(14));
        panel.setBackground(makePanelBackground());

        TextView title = new TextView(this);
        title.setText("Settings locked");
        title.setTextColor(Color.rgb(250, 232, 236));
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        title.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        panel.addView(title, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        final TextView status = new TextView(this);
        status.setText("Enter the app drawer passcode to change launcher settings.");
        status.setTextColor(Color.argb(145, 238, 220, 224));
        status.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        LinearLayout.LayoutParams statusParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        statusParams.setMargins(0, dp(2), 0, dp(10));
        panel.addView(status, statusParams);

        final EditText passcodeInput = makePasscodeInput("Passcode");
        panel.addView(passcodeInput, editParams());

        LinearLayout buttons = new LinearLayout(this);
        buttons.setOrientation(LinearLayout.HORIZONTAL);
        buttons.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams buttonRowParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        buttonRowParams.setMargins(0, dp(8), 0, 0);

        Button cancelButton = new Button(this);
        cancelButton.setText("Cancel");
        cancelButton.setTextColor(Color.rgb(60, 0, 10));
        buttons.addView(cancelButton, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        Button unlockButton = new Button(this);
        unlockButton.setText("Unlock");
        unlockButton.setTextColor(Color.rgb(60, 0, 10));
        LinearLayout.LayoutParams unlockParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        unlockParams.setMargins(dp(8), 0, 0, 0);
        buttons.addView(unlockButton, unlockParams);
        panel.addView(buttons, buttonRowParams);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        unlockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String entered = passcodeInput.getText() == null ? "" : passcodeInput.getText().toString().trim();
                if (passcodeMatches(entered)) {
                    drawerFailedAttempts = 0;
                    drawerLockoutUntil = 0L;
                    markDrawerTemporarilyUnlocked();
                    dialog.dismiss();
                    showSettingsDialog();
                    return;
                }
                drawerFailedAttempts = clampInt(drawerFailedAttempts + 1, 0, DRAWER_LOCKOUT_AFTER_FAILS);
                int attemptsLeft = Math.max(0, DRAWER_LOCKOUT_AFTER_FAILS - drawerFailedAttempts);
                if (attemptsLeft <= 0 && drawerLockoutSeconds > 0) {
                    drawerLockoutUntil = System.currentTimeMillis() + drawerLockoutSeconds * 1000L;
                    drawerFailedAttempts = 0;
                    saveSettings();
                    Toast.makeText(MainActivity.this, "Settings locked for " + formatLockoutRemaining(getDrawerLockoutRemainingMs()), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    if (attemptsLeft <= 0) {
                        drawerFailedAttempts = 0;
                        attemptsLeft = DRAWER_LOCKOUT_AFTER_FAILS;
                    }
                    saveSettings();
                    passcodeInput.setText("");
                    status.setText("Wrong passcode. " + attemptsLeft + " tries left.");
                }
            }
        });

        dialog.setContentView(panel);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.setOnDismissListener(new android.content.DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(android.content.DialogInterface dialogInterface) {
                hideSystemUi();
            }
        });
        dialog.show();
        Window shownWindow = dialog.getWindow();
        if (shownWindow != null) {
            shownWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(shownWindow.getAttributes());
            lp.width = Math.max(dp(190), Math.min(getResources().getDisplayMetrics().widthPixels - dp(24), dp(380)));
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            shownWindow.setAttributes(lp);
        }
        passcodeInput.requestFocus();
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.showSoftInput(passcodeInput, InputMethodManager.SHOW_IMPLICIT);
        } catch (Exception ignored) {
        }
    }

    private void showSettingsDialog() {
        cancelSettingsLongPressWatch();
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        ScrollView settingsScroll = new ScrollView(this);
        settingsScroll.setFillViewport(false);
        settingsScroll.setBackgroundColor(Color.TRANSPARENT);

        final LinearLayout panel = new LinearLayout(this);
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setPadding(dp(18), dp(16), dp(18), dp(14));
        panel.setBackground(makePanelBackground());
        settingsScroll.addView(panel, new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        TextView title = new TextView(this);
        title.setText("Launcher settings");
        title.setTextColor(Color.rgb(250, 232, 236));
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        title.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        title.setGravity(Gravity.LEFT);
        panel.addView(title, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        TextView subtitle = new TextView(this);
        subtitle.setText("Hold the clock for 1 second to come back here.");
        subtitle.setTextColor(Color.argb(145, 238, 220, 224));
        subtitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        LinearLayout.LayoutParams subtitleParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        subtitleParams.setMargins(0, dp(2), 0, dp(12));
        panel.addView(subtitle, subtitleParams);

        final Switch analogSwitch = makeSettingsSwitch("Analog clock", analogClock);
        panel.addView(analogSwitch, switchParams());

        final Switch hourSwitch = makeSettingsSwitch("24-hour digital clock", use24Hour);
        panel.addView(hourSwitch, switchParams());
        updateClockModeControls(analogSwitch, hourSwitch);

        final Switch batterySwitch = makeSettingsSwitch("Show battery percentage", showBatteryPercent);
        panel.addView(batterySwitch, switchParams());

        final Switch unreadSwitch = makeSettingsSwitch("Show unread SMS/email counter", showUnreadCounter);
        panel.addView(unreadSwitch, switchParams());

        TextView unreadHint = new TextView(this);
        unreadHint.setText("SMS can use READ_SMS permission. Email counts use notification access, so they reflect active email notifications rather than logging into your mail apps.");
        unreadHint.setTextColor(Color.argb(145, 238, 220, 224));
        unreadHint.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        LinearLayout.LayoutParams unreadHintParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        unreadHintParams.setMargins(0, dp(1), 0, dp(5));
        panel.addView(unreadHint, unreadHintParams);

        LinearLayout counterButtons = new LinearLayout(this);
        counterButtons.setOrientation(LinearLayout.HORIZONTAL);
        counterButtons.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        Button smsPermissionButton = new Button(this);
        smsPermissionButton.setText(hasReadSmsPermission() ? "SMS access OK" : "Grant SMS access");
        smsPermissionButton.setTextColor(Color.rgb(60, 0, 10));
        counterButtons.addView(smsPermissionButton, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        Button notificationAccessButton = new Button(this);
        notificationAccessButton.setText(isNotificationAccessEnabled() ? "Notif access OK" : "Notification access");
        notificationAccessButton.setTextColor(Color.rgb(60, 0, 10));
        LinearLayout.LayoutParams notificationButtonParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        notificationButtonParams.setMargins(dp(8), 0, 0, 0);
        counterButtons.addView(notificationAccessButton, notificationButtonParams);
        LinearLayout.LayoutParams counterButtonsParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        counterButtonsParams.setMargins(0, dp(1), 0, dp(10));
        panel.addView(counterButtons, counterButtonsParams);

        final Switch weatherSwitch = makeSettingsSwitch("Show weather", showWeather);
        panel.addView(weatherSwitch, switchParams());

        TextView weatherHint = new TextView(this);
        weatherHint.setText("Weather uses only the postal code you enter. No GPS/location permission is used. Canada works best with the first 3 characters, like K1A.");
        weatherHint.setTextColor(Color.argb(145, 238, 220, 224));
        weatherHint.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        LinearLayout.LayoutParams weatherHintParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        weatherHintParams.setMargins(0, dp(1), 0, dp(5));
        panel.addView(weatherHint, weatherHintParams);

        LinearLayout weatherRow = new LinearLayout(this);
        weatherRow.setOrientation(LinearLayout.HORIZONTAL);
        weatherRow.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        final Spinner weatherCountrySpinner = new Spinner(this);
        final String[] weatherCountries = new String[]{"US", "Canada", "Mexico", "UK"};
        ArrayAdapter<String> weatherCountryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, weatherCountries);
        weatherCountryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weatherCountrySpinner.setAdapter(weatherCountryAdapter);
        int weatherCountryPosition = 0;
        String normalizedWeatherCountry = normalizeWeatherCountry(weatherCountry);
        if ("CA".equals(normalizedWeatherCountry)) weatherCountryPosition = 1;
        else if ("MX".equals(normalizedWeatherCountry)) weatherCountryPosition = 2;
        else if ("UK".equals(normalizedWeatherCountry)) weatherCountryPosition = 3;
        weatherCountrySpinner.setSelection(weatherCountryPosition);
        weatherRow.addView(weatherCountrySpinner, new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0.9f
        ));

        final EditText weatherPostalEdit = new EditText(this);
        weatherPostalEdit.setSingleLine(true);
        weatherPostalEdit.setText(weatherPostalCode);
        weatherPostalEdit.setHint("Zip/postal");
        weatherPostalEdit.setTextColor(Color.rgb(250, 232, 236));
        weatherPostalEdit.setHintTextColor(Color.argb(130, 238, 220, 224));
        weatherPostalEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        weatherPostalEdit.setSelectAllOnFocus(false);
        LinearLayout.LayoutParams weatherPostalParams = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.4f
        );
        weatherPostalParams.setMargins(dp(8), 0, 0, 0);
        weatherRow.addView(weatherPostalEdit, weatherPostalParams);
        panel.addView(weatherRow, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        LinearLayout weatherButtonRow = new LinearLayout(this);
        weatherButtonRow.setOrientation(LinearLayout.HORIZONTAL);
        weatherButtonRow.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        final Spinner weatherUnitSpinner = new Spinner(this);
        final String[] weatherUnits = new String[]{"°F", "°C"};
        ArrayAdapter<String> weatherUnitAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, weatherUnits);
        weatherUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weatherUnitSpinner.setAdapter(weatherUnitAdapter);
        weatherUnitSpinner.setSelection("C".equals(weatherUnit) ? 1 : 0);
        weatherButtonRow.addView(weatherUnitSpinner, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        Button weatherUpdateButton = new Button(this);
        weatherUpdateButton.setText("Update weather");
        weatherUpdateButton.setTextColor(Color.rgb(60, 0, 10));
        LinearLayout.LayoutParams weatherUpdateParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        weatherUpdateParams.setMargins(dp(8), 0, 0, 0);
        weatherButtonRow.addView(weatherUpdateButton, weatherUpdateParams);
        final TextView weatherPreview = makeSettingsLabel(buildWeatherDisplayText().length() > 0 ? buildWeatherDisplayText() : "Weather preview --");
        LinearLayout.LayoutParams weatherButtonRowParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        weatherButtonRowParams.setMargins(0, dp(4), 0, dp(4));
        panel.addView(weatherButtonRow, weatherButtonRowParams);
        LinearLayout.LayoutParams weatherPreviewParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        weatherPreviewParams.setMargins(0, 0, 0, dp(10));
        panel.addView(weatherPreview, weatherPreviewParams);

        TextView fontLabel = makeSettingsLabel("Clock font");
        LinearLayout.LayoutParams fontLabelParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        fontLabelParams.setMargins(0, dp(12), 0, dp(4));
        panel.addView(fontLabel, fontLabelParams);

        final ArrayList<String> fontNames = new ArrayList<>();
        for (ClockFontOption option : availableFonts) {
            fontNames.add(option.displayName);
        }
        final Spinner fontSpinner = new Spinner(this);
        ArrayAdapter<String> fontAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fontNames);
        fontAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontSpinner.setAdapter(fontAdapter);
        int selectedFontIndex = findClockFontIndex(clockFontKey);
        if (selectedFontIndex < 0) selectedFontIndex = 0;
        fontSpinner.setSelection(selectedFontIndex);
        panel.addView(fontSpinner, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        final Switch outlineSwitch = makeSettingsSwitch("Clock text outline", clockOutline);
        LinearLayout.LayoutParams outlineParams = switchParams();
        outlineParams.setMargins(0, dp(10), 0, dp(2));
        panel.addView(outlineSwitch, outlineParams);

        TextView numberColorLabel = makeSettingsLabel("Number color");
        panel.addView(numberColorLabel, smallLabelParams());
        final EditText numberColorInput = makeHexInput(colorToHex(clockNumberColor));
        panel.addView(numberColorInput, editParams());

        TextView outlineColorLabel = makeSettingsLabel("Outline color");
        panel.addView(outlineColorLabel, smallLabelParams());
        final EditText outlineColorInput = makeHexInput(colorToHex(clockOutlineColor));
        panel.addView(outlineColorInput, editParams());

        TextView colorHint = new TextView(this);
        colorHint.setText("Use #RRGGBB or #AARRGGBB. Color changes apply when you tap Done.");
        colorHint.setTextColor(Color.argb(145, 238, 220, 224));
        colorHint.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        LinearLayout.LayoutParams colorHintParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        colorHintParams.setMargins(0, dp(1), 0, dp(10));
        panel.addView(colorHint, colorHintParams);

        TextView tintColorLabel = makeSettingsLabel("Background / shade tint color");
        LinearLayout.LayoutParams tintLabelParams = smallLabelParams();
        tintLabelParams.setMargins(0, dp(10), 0, dp(2));
        panel.addView(tintColorLabel, tintLabelParams);
        final EditText tintColorInput = makeHexInput(colorToHex(tintColor));
        panel.addView(tintColorInput, editParams());

        final TextView tintOpacityLabel = makeSettingsLabel(makePercentLabel("Background / shade tint opacity", tintOpacity));
        panel.addView(tintOpacityLabel, smallLabelParams());
        final SeekBar tintOpacitySeek = new SeekBar(this);
        tintOpacitySeek.setMax(100);
        tintOpacitySeek.setProgress(tintOpacity);
        panel.addView(tintOpacitySeek, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        TextView gradientStyleLabel = makeSettingsLabel("Background style");
        LinearLayout.LayoutParams gradientStyleLabelParams = smallLabelParams();
        gradientStyleLabelParams.setMargins(0, dp(8), 0, dp(2));
        panel.addView(gradientStyleLabel, gradientStyleLabelParams);
        final Spinner backgroundStyleSpinner = new Spinner(this);
        final String[] backgroundStyleNames = new String[]{
                "Solid tint",
                "Radial gradient",
                "Vertical gradient",
                "Diagonal gradient",
                "Square gradient",
                "Spiral sweep"
        };
        ArrayAdapter<String> backgroundStyleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, backgroundStyleNames);
        backgroundStyleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        backgroundStyleSpinner.setAdapter(backgroundStyleAdapter);
        backgroundStyleSpinner.setSelection(backgroundStyleToSpinnerIndex(backgroundStyle));
        panel.addView(backgroundStyleSpinner, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        final Switch swipeHintSwitch = makeSettingsSwitch("Show swipe-up hint", showSwipeHint);
        panel.addView(swipeHintSwitch, switchParams());

        final Switch drawerWatermarkSwitch = makeSettingsSwitch("Show app drawer watermark", showDrawerWatermark);
        panel.addView(drawerWatermarkSwitch, switchParams());

        final Switch shadeFixSwitch = makeSettingsSwitch("Tint system bars", shadeFixEnabled);
        panel.addView(shadeFixSwitch, switchParams());

        TextView shadeHint = new TextView(this);
        shadeHint.setText("This only tints Android system bars. Android SystemUI owns the real notification shade, so the launcher avoids drawing an extra fake tint strip that can make the pull-down look worse.");
        shadeHint.setTextColor(Color.argb(145, 238, 220, 224));
        shadeHint.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        LinearLayout.LayoutParams shadeHintParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        shadeHintParams.setMargins(0, dp(1), 0, dp(10));
        panel.addView(shadeHint, shadeHintParams);

        final Switch fullscreenSwitch = makeSettingsSwitch("Fullscreen / hide system bars", fullscreenMode);
        panel.addView(fullscreenSwitch, switchParams());

        TextView fullscreenHint = new TextView(this);
        fullscreenHint.setText("This keeps the launcher in Android immersive fullscreen. It can hide the status/nav bars while the launcher is active, but the actual notification shade is still drawn by Android SystemUI.");
        fullscreenHint.setTextColor(Color.argb(145, 238, 220, 224));
        fullscreenHint.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        LinearLayout.LayoutParams fullscreenHintParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        fullscreenHintParams.setMargins(0, dp(1), 0, dp(10));
        panel.addView(fullscreenHint, fullscreenHintParams);

        TextView backgroundLabel = makeSettingsLabel("Custom launcher background");
        LinearLayout.LayoutParams backgroundLabelParams = smallLabelParams();
        backgroundLabelParams.setMargins(0, dp(10), 0, dp(2));
        panel.addView(backgroundLabel, backgroundLabelParams);

        final TextView backgroundStatus = new TextView(this);
        backgroundStatus.setText(customBackgroundUri == null || customBackgroundUri.trim().length() == 0 ? "Using Android wallpaper passthrough." : "Using custom image background.");
        backgroundStatus.setTextColor(Color.argb(145, 238, 220, 224));
        backgroundStatus.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        panel.addView(backgroundStatus, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        LinearLayout backgroundButtons = new LinearLayout(this);
        backgroundButtons.setOrientation(LinearLayout.HORIZONTAL);
        backgroundButtons.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        Button pickBackgroundButton = new Button(this);
        pickBackgroundButton.setText("Pick image");
        pickBackgroundButton.setTextColor(Color.rgb(60, 0, 10));
        backgroundButtons.addView(pickBackgroundButton, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        Button clearBackgroundButton = new Button(this);
        clearBackgroundButton.setText("Clear");
        clearBackgroundButton.setTextColor(Color.rgb(60, 0, 10));
        LinearLayout.LayoutParams clearBgParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        clearBgParams.setMargins(dp(8), 0, 0, 0);
        backgroundButtons.addView(clearBackgroundButton, clearBgParams);
        LinearLayout.LayoutParams bgButtonsParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        bgButtonsParams.setMargins(0, dp(3), 0, dp(10));
        panel.addView(backgroundButtons, bgButtonsParams);

        final TextView marginVerticalLabel = makeSettingsLabel(makePercentLabel("Clock top/bottom margin", clockMarginVertical));
        LinearLayout.LayoutParams marginVerticalLabelParams = smallLabelParams();
        marginVerticalLabelParams.setMargins(0, dp(8), 0, dp(2));
        panel.addView(marginVerticalLabel, marginVerticalLabelParams);
        final SeekBar marginVerticalSeek = new SeekBar(this);
        marginVerticalSeek.setMax(40);
        marginVerticalSeek.setProgress(clockMarginVertical);
        panel.addView(marginVerticalSeek, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        final TextView marginSideLabel = makeSettingsLabel(makePercentLabel("Clock side margin", clockMarginSide));
        panel.addView(marginSideLabel, smallLabelParams());
        final SeekBar marginSideSeek = new SeekBar(this);
        marginSideSeek.setMax(40);
        marginSideSeek.setProgress(clockMarginSide);
        panel.addView(marginSideSeek, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        final TextView offsetXLabel = makeSettingsLabel(makeSignedPercentLabel("Clock X offset", clockOffsetX));
        panel.addView(offsetXLabel, smallLabelParams());
        final SeekBar offsetXSeek = new SeekBar(this);
        offsetXSeek.setMax(100);
        offsetXSeek.setProgress(clockOffsetX + 50);
        panel.addView(offsetXSeek, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        final TextView offsetYLabel = makeSettingsLabel(makeSignedPercentLabel("Clock Y offset", clockOffsetY));
        panel.addView(offsetYLabel, smallLabelParams());
        final SeekBar offsetYSeek = new SeekBar(this);
        offsetYSeek.setMax(100);
        offsetYSeek.setProgress(clockOffsetY + 50);
        panel.addView(offsetYSeek, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        TextView layoutHint = new TextView(this);
        layoutHint.setText("Margins shrink the safe area. Offsets nudge the clock after alignment.");
        layoutHint.setTextColor(Color.argb(145, 238, 220, 224));
        layoutHint.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        LinearLayout.LayoutParams layoutHintParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutHintParams.setMargins(0, dp(1), 0, dp(8));
        panel.addView(layoutHint, layoutHintParams);

        TextView horizontalAlignLabel = makeSettingsLabel("Horizontal clock alignment");
        panel.addView(horizontalAlignLabel, smallLabelParams());
        final Spinner horizontalAlignSpinner = new Spinner(this);
        ArrayAdapter<String> horizontalAlignAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{"Left", "Center", "Right"});
        horizontalAlignAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        horizontalAlignSpinner.setAdapter(horizontalAlignAdapter);
        horizontalAlignSpinner.setSelection(clockAlignHorizontal);
        panel.addView(horizontalAlignSpinner, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        TextView verticalAlignLabel = makeSettingsLabel("Vertical clock alignment");
        panel.addView(verticalAlignLabel, smallLabelParams());
        final Spinner verticalAlignSpinner = new Spinner(this);
        ArrayAdapter<String> verticalAlignAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{"Top", "Center", "Bottom"});
        verticalAlignAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        verticalAlignSpinner.setAdapter(verticalAlignAdapter);
        verticalAlignSpinner.setSelection(clockAlignVertical);
        panel.addView(verticalAlignSpinner, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        final TextView columnLabel = new TextView(this);
        columnLabel.setTextColor(Color.rgb(250, 232, 236));
        columnLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        columnLabel.setText(makeColumnLabel(drawerColumns));
        LinearLayout.LayoutParams columnLabelParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        columnLabelParams.setMargins(0, dp(8), 0, dp(3));
        panel.addView(columnLabel, columnLabelParams);

        final SeekBar columnSeek = new SeekBar(this);
        columnSeek.setMax(8);
        columnSeek.setProgress(drawerColumns);
        panel.addView(columnSeek, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        TextView columnHint = new TextView(this);
        columnHint.setText("0 means auto. 1–8 forces that many app drawer columns.");
        columnHint.setTextColor(Color.argb(145, 238, 220, 224));
        columnHint.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        LinearLayout.LayoutParams hintParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        hintParams.setMargins(0, dp(1), 0, dp(12));
        panel.addView(columnHint, hintParams);

        final TextView iconSizeLabel = makeSettingsLabel(makeIconSizeLabel(drawerIconSizeDp));
        LinearLayout.LayoutParams iconSizeLabelParams = smallLabelParams();
        iconSizeLabelParams.setMargins(0, dp(8), 0, dp(2));
        panel.addView(iconSizeLabel, iconSizeLabelParams);
        final SeekBar iconSizeSeek = new SeekBar(this);
        iconSizeSeek.setMax(80);
        iconSizeSeek.setProgress(iconSizeToSeekProgress(drawerIconSizeDp));
        panel.addView(iconSizeSeek, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        final TextView labelSizeLabel = makeSettingsLabel(makeLabelSizeLabel(drawerLabelSizeSp));
        panel.addView(labelSizeLabel, smallLabelParams());
        final SeekBar labelSizeSeek = new SeekBar(this);
        labelSizeSeek.setMax(18);
        labelSizeSeek.setProgress(labelSizeToSeekProgress(drawerLabelSizeSp));
        panel.addView(labelSizeSeek, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        TextView drawerSizeHint = new TextView(this);
        drawerSizeHint.setText("0 means auto. Larger icons may need fewer columns on tiny screens.");
        drawerSizeHint.setTextColor(Color.argb(145, 238, 220, 224));
        drawerSizeHint.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        LinearLayout.LayoutParams drawerSizeHintParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        drawerSizeHintParams.setMargins(0, dp(1), 0, dp(10));
        panel.addView(drawerSizeHint, drawerSizeHintParams);

        TextView iconThemeLabel = makeSettingsLabel("Drawer icon theme");
        panel.addView(iconThemeLabel, smallLabelParams());
        final List<IconPackOption> iconPackOptions = buildIconPackOptions();
        final Spinner iconThemeSpinner = new Spinner(this);
        ArrayList<String> iconThemeLabels = new ArrayList<String>();
        for (IconPackOption option : iconPackOptions) {
            iconThemeLabels.add(option.label);
        }
        ArrayAdapter<String> iconThemeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, iconThemeLabels);
        iconThemeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        iconThemeSpinner.setAdapter(iconThemeAdapter);
        iconThemeSpinner.setSelection(findIconPackOptionIndex(iconPackOptions));
        panel.addView(iconThemeSpinner, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        TextView iconThemeHint = new TextView(this);
        iconThemeHint.setText("Zoey Minimal uses the uploaded red/dark icon sheet for matching default and popular apps, then falls back to normal system icons. ADW/Nova-style packs use appfilter.xml when available.");
        iconThemeHint.setTextColor(Color.argb(145, 238, 220, 224));
        iconThemeHint.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        LinearLayout.LayoutParams iconThemeHintParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        iconThemeHintParams.setMargins(0, dp(1), 0, dp(10));
        panel.addView(iconThemeHint, iconThemeHintParams);

        Button manageHiddenButton = new Button(this);
        manageHiddenButton.setText("Manage hidden apps (" + countHiddenApps() + ")");
        manageHiddenButton.setTextColor(Color.rgb(60, 0, 10));
        LinearLayout.LayoutParams hiddenButtonParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        hiddenButtonParams.setMargins(0, dp(2), 0, dp(10));
        panel.addView(manageHiddenButton, hiddenButtonParams);

        TextView lockSectionLabel = makeSettingsLabel("App drawer lock");
        LinearLayout.LayoutParams lockSectionParams = smallLabelParams();
        lockSectionParams.setMargins(0, dp(10), 0, dp(3));
        panel.addView(lockSectionLabel, lockSectionParams);

        final TextView lockStatusLabel = new TextView(this);
        lockStatusLabel.setText(makeDrawerLockStatusText());
        lockStatusLabel.setTextColor(Color.argb(145, 238, 220, 224));
        lockStatusLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        panel.addView(lockStatusLabel, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        final Switch drawerLockSwitch = makeSettingsSwitch("Require passcode for app drawer", drawerLockEnabled);
        panel.addView(drawerLockSwitch, switchParams());

        Button setPasscodeButton = new Button(this);
        setPasscodeButton.setText(drawerPassHash == null || drawerPassHash.length() == 0 ? "Set passcode" : "Change passcode");
        setPasscodeButton.setTextColor(Color.rgb(60, 0, 10));
        LinearLayout.LayoutParams setPassParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        setPassParams.setMargins(0, dp(2), 0, dp(4));
        panel.addView(setPasscodeButton, setPassParams);

        TextView lockoutLabel = makeSettingsLabel("Lockout timer after 5 wrong attempts");
        panel.addView(lockoutLabel, smallLabelParams());
        final Spinner lockoutSpinner = new Spinner(this);
        final int[] lockoutValues = new int[]{0, 15, 30, 60, 300, 900, 1800};
        ArrayAdapter<String> lockoutAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{"Off", "15 seconds", "30 seconds", "1 minute", "5 minutes", "15 minutes", "30 minutes"});
        lockoutAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lockoutSpinner.setAdapter(lockoutAdapter);
        lockoutSpinner.setSelection(findLockoutIndex(drawerLockoutSeconds));
        panel.addView(lockoutSpinner, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        TextView relockLabel = makeSettingsLabel("Require passcode again after unlock");
        LinearLayout.LayoutParams relockLabelParams = smallLabelParams();
        relockLabelParams.setMargins(0, dp(8), 0, dp(2));
        panel.addView(relockLabel, relockLabelParams);
        final Spinner relockSpinner = new Spinner(this);
        final int[] relockValues = new int[]{0, 15, 30, 60, 300, 900, 1800};
        ArrayAdapter<String> relockAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{"Immediately", "15 seconds", "30 seconds", "1 minute", "5 minutes", "15 minutes", "30 minutes"});
        relockAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relockSpinner.setAdapter(relockAdapter);
        relockSpinner.setSelection(findRelockIndex(drawerRelockSeconds));
        panel.addView(relockSpinner, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        TextView lockHint = new TextView(this);
        lockHint.setText("When enabled, the same passcode is required before opening the app drawer or launcher settings. The re-lock timer controls how long a successful unlock stays valid. This is still a local launcher lock, not device encryption; Android settings and ADB are stronger than it.");
        lockHint.setTextColor(Color.argb(145, 238, 220, 224));
        lockHint.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        LinearLayout.LayoutParams lockHintParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        lockHintParams.setMargins(0, dp(1), 0, dp(12));
        panel.addView(lockHint, lockHintParams);

        LinearLayout buttons = new LinearLayout(this);
        buttons.setOrientation(LinearLayout.HORIZONTAL);
        buttons.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);

        Button resetButton = new Button(this);
        resetButton.setText("Reset");
        resetButton.setTextColor(Color.rgb(60, 0, 10));
        buttons.addView(resetButton, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        Button doneButton = new Button(this);
        doneButton.setText("Done");
        doneButton.setTextColor(Color.rgb(60, 0, 10));
        LinearLayout.LayoutParams doneParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        doneParams.setMargins(dp(8), 0, 0, 0);
        buttons.addView(doneButton, doneParams);

        panel.addView(buttons, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        analogSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                analogClock = analogSwitch.isChecked();
                if (analogClock) {
                    use24Hour = false;
                    hourSwitch.setChecked(false);
                } else if (!hourSwitch.isChecked()) {
                    use24Hour = false;
                }
                updateClockModeControls(analogSwitch, hourSwitch);
                saveSettings();
                updateClock();
            }
        });

        hourSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (analogClock) {
                    hourSwitch.setChecked(false);
                    return;
                }
                use24Hour = hourSwitch.isChecked();
                saveSettings();
                updateClock();
            }
        });

        batterySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBatteryPercent = batterySwitch.isChecked();
                saveSettings();
                updateClock();
            }
        });

        unreadSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUnreadCounter = unreadSwitch.isChecked();
                saveSettings();
                if (showUnreadCounter && !hasReadSmsPermission()) {
                    requestReadSmsPermissionIfNeeded();
                }
                updateClock();
            }
        });

        smsPermissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestReadSmsPermissionIfNeeded();
                smsPermissionButton.setText(hasReadSmsPermission() ? "SMS access OK" : "Grant SMS access");
            }
        });

        notificationAccessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNotificationAccessSettings();
            }
        });

        weatherSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWeather = weatherSwitch.isChecked();
                saveSettings();
                updateClock();
                if (showWeather) {
                    refreshWeatherIfNeeded(true);
                }
            }
        });

        weatherCountrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) weatherCountry = "CA";
                else if (position == 2) weatherCountry = "MX";
                else if (position == 3) weatherCountry = "UK";
                else weatherCountry = "US";
                weatherText = "";
                weatherUpdated = 0L;
                saveSettings();
                updateClock();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        weatherPostalEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                weatherPostalCode = s == null ? "" : s.toString();
                weatherText = "";
                weatherUpdated = 0L;
                saveSettings();
                updateClock();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        weatherUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                weatherUnit = position == 1 ? "C" : "F";
                weatherText = "";
                weatherUpdated = 0L;
                saveSettings();
                updateClock();
                if (showWeather && weatherPostalCode != null && weatherPostalCode.trim().length() > 0) {
                    refreshWeatherIfNeeded(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        weatherUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherPostalCode = weatherPostalEdit.getText() == null ? "" : weatherPostalEdit.getText().toString();
                showWeather = true;
                weatherSwitch.setChecked(true);
                weatherPreview.setText("Updating weather...");
                saveSettings();
                updateClock();
                refreshWeatherIfNeeded(true);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        weatherPreview.setText(buildWeatherDisplayText().length() > 0 ? buildWeatherDisplayText() : "Weather preview --");
                    }
                }, 1800L);
            }
        });

        fontSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < availableFonts.size()) {
                    clockFontKey = availableFonts.get(position).key;
                    clockTypeface = makeTypefaceForKey(clockFontKey);
                    saveSettings();
                    updateClock();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        outlineSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clockOutline = outlineSwitch.isChecked();
                saveSettings();
                updateClock();
            }
        });

        tintOpacitySeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tintOpacity = clampInt(progress, 0, 100);
                tintOpacityLabel.setText(makePercentLabel("Background / shade tint opacity", tintOpacity));
                saveSettings();
                applyTintSettings();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        backgroundStyleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                backgroundStyle = spinnerIndexToBackgroundStyle(position);
                saveSettings();
                applyTintSettings();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        swipeHintSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSwipeHint = swipeHintSwitch.isChecked();
                saveSettings();
                applyTintSettings();
            }
        });

        drawerWatermarkSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDrawerWatermark = drawerWatermarkSwitch.isChecked();
                saveSettings();
                applyTintSettings();
            }
        });

        shadeFixSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shadeFixEnabled = shadeFixSwitch.isChecked();
                saveSettings();
                applyTintSettings();
                hideSystemUi();
            }
        });

        fullscreenSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullscreenMode = fullscreenSwitch.isChecked();
                saveSettings();
                applyFullscreenMode();
                hideSystemUi();
            }
        });

        pickBackgroundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                pickCustomBackground();
            }
        });

        clearBackgroundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCustomBackground();
                backgroundStatus.setText("Using Android wallpaper passthrough.");
            }
        });

        marginVerticalSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                clockMarginVertical = clampInt(progress, 0, 40);
                marginVerticalLabel.setText(makePercentLabel("Clock top/bottom margin", clockMarginVertical));
                saveSettings();
                updateClock();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        marginSideSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                clockMarginSide = clampInt(progress, 0, 40);
                marginSideLabel.setText(makePercentLabel("Clock side margin", clockMarginSide));
                saveSettings();
                updateClock();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        offsetXSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                clockOffsetX = clampInt(progress - 50, -50, 50);
                offsetXLabel.setText(makeSignedPercentLabel("Clock X offset", clockOffsetX));
                saveSettings();
                updateClock();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        offsetYSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                clockOffsetY = clampInt(progress - 50, -50, 50);
                offsetYLabel.setText(makeSignedPercentLabel("Clock Y offset", clockOffsetY));
                saveSettings();
                updateClock();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        horizontalAlignSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clockAlignHorizontal = clampInt(position, CLOCK_ALIGN_START, CLOCK_ALIGN_END);
                saveSettings();
                updateClock();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        verticalAlignSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clockAlignVertical = clampInt(position, CLOCK_ALIGN_START, CLOCK_ALIGN_END);
                saveSettings();
                updateClock();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        columnSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                drawerColumns = Math.max(0, Math.min(8, progress));
                columnLabel.setText(makeColumnLabel(drawerColumns));
                saveSettings();
                populateAppGrid(filteredApps);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        iconSizeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                drawerIconSizeDp = seekProgressToIconSize(progress);
                iconSizeLabel.setText(makeIconSizeLabel(drawerIconSizeDp));
                saveSettings();
                populateAppGrid(filteredApps);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        labelSizeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                drawerLabelSizeSp = seekProgressToLabelSize(progress);
                labelSizeLabel.setText(makeLabelSizeLabel(drawerLabelSizeSp));
                saveSettings();
                populateAppGrid(filteredApps);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        iconThemeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < iconPackOptions.size()) {
                    IconPackOption selected = iconPackOptions.get(position);
                    boolean changed = iconStyle != selected.style
                            || !safeString(iconPackPackage).equals(safeString(selected.packageName));
                    iconStyle = selected.style;
                    iconPackPackage = selected.packageName == null ? "" : selected.packageName;
                    if (changed) {
                        saveSettings();
                        loadApps();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        manageHiddenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHiddenAppsDialog(manageHiddenButton);
            }
        });

        drawerLockSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLockSwitch.isChecked()) {
                    if (drawerPassHash == null || drawerPassHash.length() == 0) {
                        drawerLockSwitch.setChecked(false);
                        showSetDrawerPasscodeDialog(new Runnable() {
                            @Override
                            public void run() {
                                drawerLockEnabled = true;
                                drawerLockSwitch.setChecked(true);
                                lockStatusLabel.setText(makeDrawerLockStatusText());
                                saveSettings();
                            }
                        });
                    } else {
                        drawerLockEnabled = true;
                        lockStatusLabel.setText(makeDrawerLockStatusText());
                        saveSettings();
                    }
                } else {
                    drawerLockEnabled = false;
                    drawerUnlockedUntil = 0L;
                    lockStatusLabel.setText(makeDrawerLockStatusText());
                    saveSettings();
                }
            }
        });

        setPasscodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSetDrawerPasscodeDialog(new Runnable() {
                    @Override
                    public void run() {
                        drawerLockEnabled = true;
                        drawerLockSwitch.setChecked(true);
                        setPasscodeButton.setText("Change passcode");
                        lockStatusLabel.setText(makeDrawerLockStatusText());
                        saveSettings();
                    }
                });
            }
        });

        lockoutSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < lockoutValues.length) {
                    drawerLockoutSeconds = lockoutValues[position];
                    saveSettings();
                    lockStatusLabel.setText(makeDrawerLockStatusText());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        relockSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < relockValues.length) {
                    drawerRelockSeconds = relockValues[position];
                    if (drawerRelockSeconds <= 0) {
                        drawerUnlockedUntil = 0L;
                    } else if (drawerUnlockedUntil > System.currentTimeMillis()) {
                        drawerUnlockedUntil = System.currentTimeMillis() + drawerRelockSeconds * 1000L;
                    }
                    saveSettings();
                    lockStatusLabel.setText(makeDrawerLockStatusText());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                analogClock = false;
                use24Hour = true;
                drawerColumns = 0;
                clockFontKey = DEFAULT_CLOCK_FONT_KEY;
                clockTypeface = makeTypefaceForKey(clockFontKey);
                clockOutline = false;
                clockNumberColor = DEFAULT_CLOCK_NUMBER_COLOR;
                clockOutlineColor = DEFAULT_CLOCK_OUTLINE_COLOR;
                clockMarginVertical = DEFAULT_CLOCK_MARGIN_VERTICAL;
                clockMarginSide = DEFAULT_CLOCK_MARGIN_SIDE;
                clockOffsetX = DEFAULT_CLOCK_OFFSET;
                clockOffsetY = DEFAULT_CLOCK_OFFSET;
                clockAlignHorizontal = CLOCK_ALIGN_CENTER;
                clockAlignVertical = CLOCK_ALIGN_CENTER;
                tintColor = DEFAULT_TINT_COLOR;
                tintOpacity = DEFAULT_TINT_OPACITY;
                backgroundStyle = DEFAULT_BACKGROUND_STYLE;
                showSwipeHint = true;
                showDrawerWatermark = true;
                shadeFixEnabled = true;
                fullscreenMode = true;
                customBackgroundUri = "";
                iconStyle = ICON_STYLE_SYSTEM;
                iconPackPackage = "";
                drawerIconSizeDp = 0;
                drawerLabelSizeSp = 0;
                applyCustomBackground();
                analogSwitch.setChecked(false);
                hourSwitch.setChecked(true);
                batterySwitch.setChecked(showBatteryPercent);
                unreadSwitch.setChecked(showUnreadCounter);
                updateClockModeControls(analogSwitch, hourSwitch);
                int defaultFontIndex = findClockFontIndex(clockFontKey);
                if (defaultFontIndex >= 0) {
                    fontSpinner.setSelection(defaultFontIndex);
                }
                outlineSwitch.setChecked(false);
                numberColorInput.setText(colorToHex(clockNumberColor));
                outlineColorInput.setText(colorToHex(clockOutlineColor));
                tintColorInput.setText(colorToHex(tintColor));
                tintOpacitySeek.setProgress(tintOpacity);
                tintOpacityLabel.setText(makePercentLabel("Background / shade tint opacity", tintOpacity));
                backgroundStyleSpinner.setSelection(backgroundStyleToSpinnerIndex(backgroundStyle));
                swipeHintSwitch.setChecked(showSwipeHint);
                drawerWatermarkSwitch.setChecked(showDrawerWatermark);
                iconThemeSpinner.setSelection(findIconPackOptionIndex(iconPackOptions));
                shadeFixSwitch.setChecked(shadeFixEnabled);
                fullscreenSwitch.setChecked(fullscreenMode);
                backgroundStatus.setText("Using Android wallpaper passthrough.");
                iconSizeSeek.setProgress(iconSizeToSeekProgress(drawerIconSizeDp));
                labelSizeSeek.setProgress(labelSizeToSeekProgress(drawerLabelSizeSp));
                iconSizeLabel.setText(makeIconSizeLabel(drawerIconSizeDp));
                labelSizeLabel.setText(makeLabelSizeLabel(drawerLabelSizeSp));
                marginVerticalSeek.setProgress(clockMarginVertical);
                marginSideSeek.setProgress(clockMarginSide);
                offsetXSeek.setProgress(clockOffsetX + 50);
                offsetYSeek.setProgress(clockOffsetY + 50);
                marginVerticalLabel.setText(makePercentLabel("Clock top/bottom margin", clockMarginVertical));
                marginSideLabel.setText(makePercentLabel("Clock side margin", clockMarginSide));
                offsetXLabel.setText(makeSignedPercentLabel("Clock X offset", clockOffsetX));
                offsetYLabel.setText(makeSignedPercentLabel("Clock Y offset", clockOffsetY));
                horizontalAlignSpinner.setSelection(clockAlignHorizontal);
                verticalAlignSpinner.setSelection(clockAlignVertical);
                columnSeek.setProgress(0);
                columnLabel.setText(makeColumnLabel(0));
                saveSettings();
                applyTintSettings();
                applyFullscreenMode();
                updateClock();
                populateAppGrid(filteredApps);
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer parsedNumberColor = parseColorOrNull(numberColorInput.getText().toString());
                Integer parsedOutlineColor = parseColorOrNull(outlineColorInput.getText().toString());
                Integer parsedTintColor = parseColorOrNull(tintColorInput.getText().toString());
                if (parsedNumberColor == null) {
                    Toast.makeText(MainActivity.this, "Number color needs #RRGGBB or #AARRGGBB", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (parsedOutlineColor == null) {
                    Toast.makeText(MainActivity.this, "Outline color needs #RRGGBB or #AARRGGBB", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (parsedTintColor == null) {
                    Toast.makeText(MainActivity.this, "Tint color needs #RRGGBB or #AARRGGBB", Toast.LENGTH_SHORT).show();
                    return;
                }
                clockNumberColor = parsedNumberColor;
                clockOutlineColor = parsedOutlineColor;
                tintColor = parsedTintColor;
                saveSettings();
                applyTintSettings();
                applyFullscreenMode();
                updateClock();
                dialog.dismiss();
            }
        });

        dialog.setContentView(settingsScroll);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.setOnDismissListener(new android.content.DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(android.content.DialogInterface dialogInterface) {
                hideSystemUi();
            }
        });
        dialog.show();
        Window shownWindow = dialog.getWindow();
        if (shownWindow != null) {
            shownWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(shownWindow.getAttributes());
            lp.width = Math.max(dp(160), Math.min(getResources().getDisplayMetrics().widthPixels - dp(24), dp(430)));
            lp.height = Math.max(dp(160), Math.min(getResources().getDisplayMetrics().heightPixels - dp(28), dp(820)));
            shownWindow.setAttributes(lp);
        }
    }

    private void pickCustomBackground() {
        try {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            startActivityForResult(intent, REQUEST_PICK_BACKGROUND);
        } catch (Exception e) {
            Toast.makeText(this, "Could not open image picker", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearCustomBackground() {
        customBackgroundUri = "";
        saveSettings();
        applyCustomBackground();
        Toast.makeText(this, "Custom background cleared", Toast.LENGTH_SHORT).show();
    }

    private void applyCustomBackground() {
        if (backgroundImageView == null) return;
        if (customBackgroundUri == null || customBackgroundUri.trim().length() == 0) {
            backgroundImageView.setImageDrawable(null);
            backgroundImageView.setVisibility(View.GONE);
            return;
        }
        try {
            backgroundImageView.setVisibility(View.VISIBLE);
            backgroundImageView.setImageURI(Uri.parse(customBackgroundUri));
        } catch (Exception e) {
            backgroundImageView.setImageDrawable(null);
            backgroundImageView.setVisibility(View.GONE);
            Toast.makeText(this, "Could not load custom background", Toast.LENGTH_SHORT).show();
        }
    }

    private String makeIconSizeLabel(int sizeDp) {
        if (sizeDp <= 0) return "Drawer icon size: Auto";
        return "Drawer icon size: " + sizeDp + "dp";
    }

    private String makeLabelSizeLabel(int sizeSp) {
        if (sizeSp <= 0) return "Drawer label size: Auto";
        return "Drawer label size: " + sizeSp + "sp";
    }

    private int iconSizeToSeekProgress(int sizeDp) {
        if (sizeDp <= 0) return 0;
        return clampInt(sizeDp - 16, 1, 80);
    }

    private int seekProgressToIconSize(int progress) {
        if (progress <= 0) return 0;
        return clampInt(progress + 16, 18, 96);
    }

    private int labelSizeToSeekProgress(int sizeSp) {
        if (sizeSp <= 0) return 0;
        return clampInt(sizeSp - 6, 1, 18);
    }

    private int seekProgressToLabelSize(int progress) {
        if (progress <= 0) return 0;
        return clampInt(progress + 6, 7, 24);
    }

    private void showHiddenAppsDialog(final Button sourceButton) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(false);
        LinearLayout panel = new LinearLayout(this);
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setPadding(dp(18), dp(16), dp(18), dp(14));
        panel.setBackground(makePanelBackground());
        scroll.addView(panel, new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        TextView title = new TextView(this);
        title.setText("Hidden apps");
        title.setTextColor(Color.rgb(250, 232, 236));
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        title.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        panel.addView(title, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        int count = 0;
        for (final AppEntry app : allApps) {
            if (!isAppHidden(app)) continue;
            count++;
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER_VERTICAL);
            row.setPadding(0, dp(4), 0, dp(4));

            ImageView icon = new ImageView(this);
            icon.setImageDrawable(app.icon);
            row.addView(icon, new LinearLayout.LayoutParams(dp(30), dp(30)));

            TextView label = new TextView(this);
            label.setText(app.label);
            label.setTextColor(Color.rgb(242, 226, 230));
            label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            label.setSingleLine(true);
            label.setEllipsize(TextUtils.TruncateAt.END);
            LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
            labelParams.setMargins(dp(8), 0, dp(8), 0);
            row.addView(label, labelParams);

            Button unhide = new Button(this);
            unhide.setText("Unhide");
            unhide.setTextColor(Color.rgb(60, 0, 10));
            row.addView(unhide, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            unhide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    unhideApp(app);
                    dialog.dismiss();
                    showHiddenAppsDialog(sourceButton);
                }
            });
            panel.addView(row, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
        }

        if (count == 0) {
            TextView empty = new TextView(this);
            empty.setText("No hidden apps yet. Long-press an app in the drawer and choose Hide from drawer.");
            empty.setTextColor(Color.argb(145, 238, 220, 224));
            empty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            LinearLayout.LayoutParams emptyParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            emptyParams.setMargins(0, dp(6), 0, dp(10));
            panel.addView(empty, emptyParams);
        }

        Button close = makeActionButton("Close");
        panel.addView(close, actionButtonParams());
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(scroll);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.setOnDismissListener(new android.content.DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(android.content.DialogInterface dialogInterface) {
                if (sourceButton != null) sourceButton.setText("Manage hidden apps (" + countHiddenApps() + ")");
                hideSystemUi();
            }
        });
        dialog.show();
        Window shownWindow = dialog.getWindow();
        if (shownWindow != null) {
            shownWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(shownWindow.getAttributes());
            lp.width = Math.max(dp(180), Math.min(getResources().getDisplayMetrics().widthPixels - dp(24), dp(430)));
            lp.height = Math.max(dp(160), Math.min(getResources().getDisplayMetrics().heightPixels - dp(28), dp(650)));
            shownWindow.setAttributes(lp);
        }
    }

    private Switch makeSettingsSwitch(String text, boolean checked) {
        Switch sw = new Switch(this);
        sw.setText(text);
        sw.setChecked(checked);
        sw.setTextColor(Color.rgb(250, 232, 236));
        sw.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        sw.setPadding(0, dp(4), 0, dp(4));
        return sw;
    }

    private LinearLayout.LayoutParams switchParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, dp(2), 0, dp(2));
        return params;
    }

    private void updateClockModeControls(Switch analogSwitch, Switch hourSwitch) {
        boolean analogSelected = analogSwitch.isChecked();
        if (analogSelected) {
            hourSwitch.setChecked(false);
            hourSwitch.setEnabled(false);
            hourSwitch.setAlpha(0.45f);
        } else {
            hourSwitch.setEnabled(true);
            hourSwitch.setAlpha(1.0f);
        }
    }

    private TextView makeSettingsLabel(String text) {
        TextView label = new TextView(this);
        label.setText(text);
        label.setTextColor(Color.rgb(250, 232, 236));
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        return label;
    }

    private LinearLayout.LayoutParams smallLabelParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, dp(6), 0, dp(2));
        return params;
    }

    private LinearLayout.LayoutParams editParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dp(40)
        );
        params.setMargins(0, 0, 0, dp(2));
        return params;
    }

    private EditText makeHexInput(String text) {
        EditText editText = new EditText(this);
        editText.setSingleLine(true);
        editText.setText(text);
        editText.setSelectAllOnFocus(true);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        editText.setTextColor(Color.rgb(250, 232, 236));
        editText.setHintTextColor(Color.argb(125, 238, 220, 224));
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        editText.setPadding(dp(12), 0, dp(12), 0);
        editText.setBackground(makeSearchBackground());
        return editText;
    }

    private int backgroundStyleToSpinnerIndex(int style) {
        style = clampInt(style, BG_STYLE_SOLID, BG_STYLE_SPIRAL);
        return style;
    }

    private int spinnerIndexToBackgroundStyle(int index) {
        return clampInt(index, BG_STYLE_SOLID, BG_STYLE_SPIRAL);
    }

    private String safeString(String value) {
        return value == null ? "" : value;
    }

    private Integer parseColorOrNull(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        if (!(trimmed.matches("^#[0-9a-fA-F]{6}$") || trimmed.matches("^#[0-9a-fA-F]{8}$"))) {
            return null;
        }
        try {
            return Color.parseColor(trimmed);
        } catch (Exception ignored) {
            return null;
        }
    }

    private String colorToHex(int color) {
        int alpha = Color.alpha(color);
        if (alpha == 255) {
            return String.format(Locale.US, "#%02X%02X%02X", Color.red(color), Color.green(color), Color.blue(color));
        }
        return String.format(Locale.US, "#%02X%02X%02X%02X", alpha, Color.red(color), Color.green(color), Color.blue(color));
    }

    private String makePercentLabel(String title, int percent) {
        return title + ": " + percent + "%";
    }

    private String makeSignedPercentLabel(String title, int percent) {
        String sign = percent > 0 ? "+" : "";
        return title + ": " + sign + percent + "%";
    }

    private String makeColumnLabel(int columns) {
        if (columns <= 0) return "App drawer columns: Auto";
        return "App drawer columns: " + columns;
    }

    private Drawable makeSelectableBackground() {
        android.graphics.drawable.StateListDrawable states = new android.graphics.drawable.StateListDrawable();
        android.graphics.drawable.GradientDrawable pressed = new android.graphics.drawable.GradientDrawable();
        pressed.setColor(Color.argb(70, 145, 0, 28));
        pressed.setCornerRadius(dp(14));
        android.graphics.drawable.GradientDrawable normal = new android.graphics.drawable.GradientDrawable();
        normal.setColor(Color.TRANSPARENT);
        normal.setCornerRadius(dp(14));
        states.addState(new int[]{android.R.attr.state_pressed}, pressed);
        states.addState(new int[]{android.R.attr.state_focused}, pressed);
        states.addState(new int[]{}, normal);
        return states;
    }

    private Drawable makeSearchBackground() {
        android.graphics.drawable.GradientDrawable drawable = new android.graphics.drawable.GradientDrawable();
        drawable.setColor(Color.argb(82, 0, 0, 0));
        drawable.setStroke(dp(1), Color.argb(105, 238, 220, 224));
        drawable.setCornerRadius(dp(18));
        return drawable;
    }

    private Drawable makePanelBackground() {
        android.graphics.drawable.GradientDrawable drawable = new android.graphics.drawable.GradientDrawable();
        drawable.setColor(Color.argb(245, 18, 0, 4));
        drawable.setStroke(dp(1), Color.argb(155, 170, 0, 34));
        drawable.setCornerRadius(dp(18));
        return drawable;
    }

    private void hideKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            View focus = getCurrentFocus();
            if (imm != null && focus != null) {
                imm.hideSoftInputFromWindow(focus.getWindowToken(), 0);
            }
        } catch (Exception ignored) {
        }
    }

    private void hideSystemUi() {
        applyFullscreenMode();
        if (Build.VERSION.SDK_INT >= 21) {
            int barColor = shadeFixEnabled ? withOpacity(tintColor, tintOpacity) : Color.TRANSPARENT;
            getWindow().setStatusBarColor(barColor);
            getWindow().setNavigationBarColor(barColor);
        }
    }

    private void applyFullscreenMode() {
        View decor = getWindow().getDecorView();
        if (fullscreenMode) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            decor.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            applyModernFullscreenReflect(true);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            applyModernFullscreenReflect(false);
        }
    }

    private void applyModernFullscreenReflect(boolean hideBars) {
        if (Build.VERSION.SDK_INT < 30) {
            return;
        }
        try {
            getWindow().getClass().getMethod("setDecorFitsSystemWindows", boolean.class).invoke(getWindow(), !hideBars);
        } catch (Exception ignored) {
        }
        try {
            Object controller = getWindow().getClass().getMethod("getInsetsController").invoke(getWindow());
            if (controller == null) return;
            Class<?> typeClass = Class.forName("android.view.WindowInsets$Type");
            int statusBars = ((Integer) typeClass.getMethod("statusBars").invoke(null)).intValue();
            int navigationBars = ((Integer) typeClass.getMethod("navigationBars").invoke(null)).intValue();
            int bars = statusBars | navigationBars;
            Class<?> controllerClass = Class.forName("android.view.WindowInsetsController");
            if (hideBars) {
                try {
                    controllerClass.getMethod("setSystemBarsBehavior", int.class).invoke(controller, 2);
                } catch (Exception ignored) {
                }
                controllerClass.getMethod("hide", int.class).invoke(controller, bars);
            } else {
                controllerClass.getMethod("show", int.class).invoke(controller, bars);
            }
        } catch (Exception ignored) {
        }
    }

    private int dp(float value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    private int clampInt(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private void loadClockFonts() {
        availableFonts.clear();
        addClockFont(new ClockFontOption("System thin", "family:sans-serif-thin", null, "sans-serif-thin"));
        addClockFont(new ClockFontOption("System light", "family:sans-serif-light", null, "sans-serif-light"));
        addClockFont(new ClockFontOption("System regular", "family:sans-serif", null, "sans-serif"));
        addClockFont(new ClockFontOption("System medium", "family:sans-serif-medium", null, "sans-serif-medium"));
        addClockFont(new ClockFontOption("Condensed", "family:sans-serif-condensed", null, "sans-serif-condensed"));
        addClockFont(new ClockFontOption("Serif", "family:serif", null, "serif"));
        addClockFont(new ClockFontOption("Monospace", "family:monospace", null, "monospace"));
        addClockFont(new ClockFontOption("Serif monospace", "family:serif-monospace", null, "serif-monospace"));
        addClockFont(new ClockFontOption("Casual", "family:casual", null, "casual"));
        addClockFont(new ClockFontOption("Cursive", "family:cursive", null, "cursive"));

        scanFontDir("/system/fonts");
        scanFontDir("/product/fonts");
        scanFontDir("/vendor/fonts");
        scanFontDir("/system_ext/fonts");

        if (availableFonts.isEmpty()) {
            availableFonts.add(new ClockFontOption("System regular", "family:sans-serif", null, "sans-serif"));
        }
    }

    private void scanFontDir(String path) {
        File dir = new File(path);
        File[] files = dir.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (file == null || !file.isFile() || !file.canRead()) continue;
            String name = file.getName();
            String lower = name.toLowerCase(Locale.US);
            if (!(lower.endsWith(".ttf") || lower.endsWith(".otf") || lower.endsWith(".ttc"))) continue;
            String display = name;
            int dot = display.lastIndexOf('.');
            if (dot > 0) display = display.substring(0, dot);
            display = display.replace('_', ' ').replace('-', ' ');
            addClockFont(new ClockFontOption(display, "file:" + file.getAbsolutePath(), file.getAbsolutePath(), null));
        }
    }

    private void addClockFont(ClockFontOption option) {
        if (option == null || option.key == null) return;
        for (ClockFontOption existing : availableFonts) {
            if (option.key.equals(existing.key)) return;
        }
        availableFonts.add(option);
    }

    private int findClockFontIndex(String key) {
        if (key == null) return -1;
        for (int i = 0; i < availableFonts.size(); i++) {
            if (key.equals(availableFonts.get(i).key)) return i;
        }
        return -1;
    }

    private Typeface makeTypefaceForKey(String key) {
        ClockFontOption option = null;
        if (key != null) {
            for (ClockFontOption candidate : availableFonts) {
                if (key.equals(candidate.key)) {
                    option = candidate;
                    break;
                }
            }
        }
        if (option == null && !availableFonts.isEmpty()) {
            option = availableFonts.get(0);
        }
        if (option == null) {
            return Typeface.create("sans-serif-thin", Typeface.NORMAL);
        }
        try {
            if (option.filePath != null) {
                return Typeface.createFromFile(option.filePath);
            }
            if (option.familyName != null) {
                return Typeface.create(option.familyName, Typeface.NORMAL);
            }
        } catch (Exception ignored) {
        }
        return Typeface.create("sans-serif-thin", Typeface.NORMAL);
    }

    private static class ClockFontOption {
        final String displayName;
        final String key;
        final String filePath;
        final String familyName;

        ClockFontOption(String displayName, String key, String filePath, String familyName) {
            this.displayName = displayName;
            this.key = key;
            this.filePath = filePath;
            this.familyName = familyName;
        }
    }


    private static class IconPackOption {
        final String label;
        final int style;
        final String packageName;

        IconPackOption(String label, int style, String packageName) {
            this.label = label == null ? "" : label;
            this.style = style;
            this.packageName = packageName == null ? "" : packageName;
        }
    }

    private static class IconPackData {
        final String packageName;
        final Resources resources;
        final Map<String, String> componentToDrawable = new HashMap<String, String>();
        final Map<String, Integer> drawableResourceCache = new HashMap<String, Integer>();

        IconPackData(String packageName, Resources resources) {
            this.packageName = packageName;
            this.resources = resources;
        }

        Drawable loadDrawableFor(Context context, String appPackage, String activityName) {
            if (resources == null || appPackage == null || activityName == null) return null;
            String exact = (appPackage + "/" + activityName).toLowerCase(Locale.US);
            String shortActivity = activityName;
            if (activityName.startsWith(appPackage + ".")) {
                shortActivity = "." + activityName.substring(appPackage.length() + 1);
            }
            String shortened = (appPackage + "/" + shortActivity).toLowerCase(Locale.US);
            String drawableName = componentToDrawable.get(exact);
            if (drawableName == null) drawableName = componentToDrawable.get(shortened);
            if (drawableName == null) return null;
            Integer cached = drawableResourceCache.get(drawableName);
            int resId;
            if (cached != null) {
                resId = cached.intValue();
            } else {
                resId = resources.getIdentifier(drawableName, "drawable", packageName);
                if (resId == 0) resId = resources.getIdentifier(drawableName, "mipmap", packageName);
                drawableResourceCache.put(drawableName, Integer.valueOf(resId));
            }
            if (resId == 0) return null;
            try {
                if (Build.VERSION.SDK_INT >= 21) {
                    return resources.getDrawable(resId, context.getTheme());
                }
                return resources.getDrawable(resId);
            } catch (Exception ignored) {
                return null;
            }
        }
    }

    private static class MinimalIconDrawable extends Drawable {
        private final Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final Paint glowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final Paint fallbackPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.SUBPIXEL_TEXT_FLAG);
        private final Drawable sourceIcon;
        private final String fallbackMark;
        private final int glyphTintColor;
        private final int outlineColor;
        private final int backgroundColor;
        private final ColorMatrixColorFilter grayscaleFilter;
        private int alpha = 255;

        MinimalIconDrawable(Drawable sourceIcon, String label, int tintColor, int glyphColor, int outlineColor) {
            Drawable safeIcon = null;
            if (sourceIcon != null) {
                try {
                    safeIcon = sourceIcon.mutate();
                } catch (Exception ignored) {
                    safeIcon = sourceIcon;
                }
            }
            this.sourceIcon = safeIcon;
            this.fallbackMark = makeFallbackMark(label);
            this.glyphTintColor = glyphColor;
            this.outlineColor = outlineColor;
            this.backgroundColor = blendColors(Color.rgb(12, 12, 16), tintColor, 0.36f);

            fillPaint.setStyle(Paint.Style.FILL);
            fillPaint.setColor(backgroundColor);

            strokePaint.setStyle(Paint.Style.STROKE);
            strokePaint.setStrokeWidth(3f);
            strokePaint.setColor(adjustAlpha(outlineColor, 175));

            glowPaint.setStyle(Paint.Style.STROKE);
            glowPaint.setStrokeCap(Paint.Cap.ROUND);
            glowPaint.setColor(adjustAlpha(outlineColor, 120));

            fallbackPaint.setStyle(Paint.Style.STROKE);
            fallbackPaint.setStrokeCap(Paint.Cap.ROUND);
            fallbackPaint.setStrokeJoin(Paint.Join.ROUND);
            fallbackPaint.setColor(glyphColor);
            fallbackPaint.setTextAlign(Paint.Align.CENTER);
            fallbackPaint.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));

            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0f);
            // Keep the original app art recognizable, but pull it toward the launcher text color
            // so the built-in icon style feels like a real minimal theme instead of an alphabet grid.
            float r = Color.red(glyphColor) / 255f;
            float g = Color.green(glyphColor) / 255f;
            float b = Color.blue(glyphColor) / 255f;
            ColorMatrix tintMatrix = new ColorMatrix(new float[] {
                    0.55f + 0.45f * r, 0f, 0f, 0f, 0f,
                    0f, 0.55f + 0.45f * g, 0f, 0f, 0f,
                    0f, 0f, 0.55f + 0.45f * b, 0f, 0f,
                    0f, 0f, 0f, 1f, 0f
            });
            matrix.postConcat(tintMatrix);
            grayscaleFilter = new ColorMatrixColorFilter(matrix);
        }

        @Override
        public void draw(Canvas canvas) {
            android.graphics.Rect b = getBounds();
            int w = b.width();
            int h = b.height();
            int size = Math.min(w, h);
            if (size <= 0) return;
            float cx = b.left + w / 2f;
            float cy = b.top + h / 2f;
            float pad = size * 0.095f;
            RectF r = new RectF(cx - size / 2f + pad, cy - size / 2f + pad, cx + size / 2f - pad, cy + size / 2f - pad);
            float radius = size * 0.235f;

            fillPaint.setAlpha(Math.round(alpha * 0.92f));
            strokePaint.setAlpha(Math.round(alpha * 0.65f));
            glowPaint.setAlpha(Math.round(alpha * 0.28f));
            canvas.drawRoundRect(r, radius, radius, fillPaint);
            canvas.drawRoundRect(r, radius, radius, strokePaint);

            glowPaint.setStrokeWidth(Math.max(1f, size * 0.018f));
            float arcInset = size * 0.17f;
            RectF arc = new RectF(r.left + arcInset, r.top + arcInset, r.right - arcInset, r.bottom - arcInset);
            canvas.drawArc(arc, -35f, 72f, false, glowPaint);
            canvas.drawArc(arc, 145f, 72f, false, glowPaint);

            if (sourceIcon != null) {
                drawThemedSourceIcon(canvas, b, size, cx, cy);
            } else {
                drawFallbackGlyph(canvas, size, cx, cy);
            }
        }

        private void drawThemedSourceIcon(Canvas canvas, android.graphics.Rect bounds, int size, float cx, float cy) {
            int iconSize = Math.round(size * 0.66f);
            int left = Math.round(cx - iconSize / 2f);
            int top = Math.round(cy - iconSize / 2f);
            int right = left + iconSize;
            int bottom = top + iconSize;
            int save = canvas.saveLayer(bounds.left, bounds.top, bounds.right, bounds.bottom, null);
            try {
                sourceIcon.setBounds(left, top, right, bottom);
                sourceIcon.setAlpha(Math.round(alpha * 0.92f));
                sourceIcon.setColorFilter(grayscaleFilter);
                sourceIcon.draw(canvas);
            } catch (Exception ignored) {
                drawFallbackGlyph(canvas, size, cx, cy);
            } finally {
                try {
                    sourceIcon.setColorFilter(null);
                    sourceIcon.setAlpha(255);
                } catch (Exception ignored) {
                    // Some drawables are dramatic. Let them be dramatic somewhere else.
                }
                canvas.restoreToCount(save);
            }
        }

        private void drawFallbackGlyph(Canvas canvas, int size, float cx, float cy) {
            fallbackPaint.setAlpha(alpha);
            fallbackPaint.setStrokeWidth(Math.max(2f, size * 0.055f));
            float s = size * 0.22f;
            if ("call".equals(fallbackMark)) {
                canvas.drawArc(new RectF(cx - s, cy - s, cx + s, cy + s), 115f, 150f, false, fallbackPaint);
                canvas.drawLine(cx - s * 0.85f, cy + s * 0.12f, cx - s * 1.22f, cy + s * 0.48f, fallbackPaint);
                canvas.drawLine(cx + s * 0.85f, cy - s * 0.12f, cx + s * 1.22f, cy - s * 0.48f, fallbackPaint);
            } else if ("message".equals(fallbackMark)) {
                RectF bubble = new RectF(cx - s * 1.3f, cy - s * 0.82f, cx + s * 1.3f, cy + s * 0.72f);
                canvas.drawRoundRect(bubble, s * 0.28f, s * 0.28f, fallbackPaint);
                canvas.drawLine(cx - s * 0.45f, cy + s * 0.72f, cx - s * 0.82f, cy + s * 1.08f, fallbackPaint);
            } else if ("camera".equals(fallbackMark)) {
                RectF body = new RectF(cx - s * 1.28f, cy - s * 0.68f, cx + s * 1.28f, cy + s * 0.92f);
                canvas.drawRoundRect(body, s * 0.22f, s * 0.22f, fallbackPaint);
                canvas.drawCircle(cx, cy + s * 0.12f, s * 0.48f, fallbackPaint);
                canvas.drawLine(cx - s * 0.55f, cy - s * 0.68f, cx - s * 0.25f, cy - s * 1.0f, fallbackPaint);
                canvas.drawLine(cx - s * 0.25f, cy - s * 1.0f, cx + s * 0.35f, cy - s * 1.0f, fallbackPaint);
            } else if ("music".equals(fallbackMark)) {
                canvas.drawLine(cx + s * 0.45f, cy - s * 1.1f, cx + s * 0.45f, cy + s * 0.45f, fallbackPaint);
                canvas.drawLine(cx + s * 0.45f, cy - s * 1.1f, cx - s * 0.42f, cy - s * 0.85f, fallbackPaint);
                canvas.drawCircle(cx - s * 0.38f, cy + s * 0.55f, s * 0.33f, fallbackPaint);
                canvas.drawCircle(cx + s * 0.45f, cy + s * 0.40f, s * 0.33f, fallbackPaint);
            } else if ("settings".equals(fallbackMark)) {
                canvas.drawCircle(cx, cy, s * 0.72f, fallbackPaint);
                for (int i = 0; i < 8; i++) {
                    double a = Math.PI * 2.0 * i / 8.0;
                    float x1 = cx + (float)Math.cos(a) * s * 0.92f;
                    float y1 = cy + (float)Math.sin(a) * s * 0.92f;
                    float x2 = cx + (float)Math.cos(a) * s * 1.25f;
                    float y2 = cy + (float)Math.sin(a) * s * 1.25f;
                    canvas.drawLine(x1, y1, x2, y2, fallbackPaint);
                }
                canvas.drawCircle(cx, cy, s * 0.24f, fallbackPaint);
            } else {
                canvas.drawCircle(cx, cy, s * 0.95f, fallbackPaint);
                canvas.drawLine(cx - s * 0.58f, cy + s * 0.58f, cx + s * 0.58f, cy - s * 0.58f, fallbackPaint);
            }
        }

        @Override
        public void setAlpha(int alpha) {
            this.alpha = alpha;
            invalidateSelf();
        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {
            fillPaint.setColorFilter(colorFilter);
            strokePaint.setColorFilter(colorFilter);
            glowPaint.setColorFilter(colorFilter);
            fallbackPaint.setColorFilter(colorFilter);
            if (sourceIcon != null) {
                sourceIcon.setColorFilter(colorFilter);
            }
            invalidateSelf();
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

        private static String makeFallbackMark(String label) {
            String text = label == null ? "" : label.toLowerCase(Locale.US);
            if (text.contains("phone") || text.contains("dial") || text.contains("call")) return "call";
            if (text.contains("message") || text.contains("sms") || text.contains("mail") || text.contains("chat")) return "message";
            if (text.contains("camera") || text.contains("photo") || text.contains("gallery")) return "camera";
            if (text.contains("music") || text.contains("audio") || text.contains("spotify") || text.contains("sound")) return "music";
            if (text.contains("settings") || text.contains("tools") || text.contains("config")) return "settings";
            return "generic";
        }

        private static int adjustAlpha(int color, int alpha) {
            return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
        }

        private static int blendColors(int a, int b, float amount) {
            float t = Math.max(0f, Math.min(1f, amount));
            int r = Math.round(Color.red(a) * (1f - t) + Color.red(b) * t);
            int g = Math.round(Color.green(a) * (1f - t) + Color.green(b) * t);
            int bl = Math.round(Color.blue(a) * (1f - t) + Color.blue(b) * t);
            return Color.rgb(r, g, bl);
        }
    }

    private static class AppEntry {
        final String label;
        final Drawable icon;
        final Intent launchIntent;
        final String packageName;
        final String activityName;
        final String key;

        AppEntry(String label, Drawable icon, Intent launchIntent, String packageName, String activityName) {
            this.label = label;
            this.icon = icon;
            this.launchIntent = launchIntent;
            this.packageName = packageName;
            this.activityName = activityName;
            this.key = packageName + "/" + activityName;
        }
    }

    private static class ClockFaceView extends View {
        private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.SUBPIXEL_TEXT_FLAG);
        private final Paint analogPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.SUBPIXEL_TEXT_FLAG);
        private final RectF tempRect = new RectF();
        private int hour24 = 0;
        private int minute = 0;
        private int batteryPercent = -1;
        private boolean batteryVisible = true;
        private String topInfoText = "";
        private boolean analog = false;
        private boolean use24Hour = true;
        private Typeface clockTypeface = Typeface.create("sans-serif-thin", Typeface.NORMAL);
        private int numberColor = Color.rgb(245, 232, 235);
        private boolean outlineEnabled = false;
        private int outlineColor = Color.rgb(70, 0, 10);
        private int shadowTintColor = DEFAULT_TINT_COLOR;
        private int shadowTintOpacity = DEFAULT_TINT_OPACITY;
        private int marginVerticalPercent = DEFAULT_CLOCK_MARGIN_VERTICAL;
        private int marginSidePercent = DEFAULT_CLOCK_MARGIN_SIDE;
        private int offsetXPercent = DEFAULT_CLOCK_OFFSET;
        private int offsetYPercent = DEFAULT_CLOCK_OFFSET;
        private int horizontalAlign = CLOCK_ALIGN_CENTER;
        private int verticalAlign = CLOCK_ALIGN_CENTER;
        private float cachedTextSize = -1f;
        private int cachedWidth = -1;
        private int cachedHeight = -1;
        private boolean cachedUse24Hour = true;
        private Typeface cachedTypeface = null;

        ClockFaceView(android.content.Context context) {
            super(context);
            setWillNotDraw(false);
            paint.setColor(numberColor);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(clockTypeface);
            paint.setShadowLayer(18f, 0f, 4f, makeClockShadowColor());
            analogPaint.setColor(numberColor);
            analogPaint.setTextAlign(Paint.Align.CENTER);
            analogPaint.setTypeface(clockTypeface);
            analogPaint.setShadowLayer(10f, 0f, 3f, makeClockShadowColor());
        }

        void setClockSettings(boolean analog, boolean use24Hour) {
            if (this.analog != analog || this.use24Hour != use24Hour) {
                this.analog = analog;
                this.use24Hour = use24Hour;
                this.cachedTextSize = -1f;
                invalidate();
            }
        }

        void setClockAppearance(Typeface typeface, int numberColor, boolean outlineEnabled, int outlineColor) {
            if (typeface == null) {
                typeface = Typeface.create("sans-serif-thin", Typeface.NORMAL);
            }
            boolean changed = this.clockTypeface != typeface
                    || this.numberColor != numberColor
                    || this.outlineEnabled != outlineEnabled
                    || this.outlineColor != outlineColor;
            this.clockTypeface = typeface;
            this.numberColor = numberColor;
            this.outlineEnabled = outlineEnabled;
            this.outlineColor = outlineColor;
            paint.setTypeface(typeface);
            analogPaint.setTypeface(typeface);
            if (changed) {
                cachedTextSize = -1f;
                invalidate();
            }
        }

        void setShadowTint(int tintColor, int tintOpacity) {
            tintOpacity = Math.max(0, Math.min(100, tintOpacity));
            if (this.shadowTintColor != tintColor || this.shadowTintOpacity != tintOpacity) {
                this.shadowTintColor = tintColor;
                this.shadowTintOpacity = tintOpacity;
                int shadowColor = makeClockShadowColor();
                paint.setShadowLayer(18f, 0f, 4f, shadowColor);
                analogPaint.setShadowLayer(10f, 0f, 3f, shadowColor);
                invalidate();
            }
        }

        void setClockLayout(int marginVerticalPercent, int marginSidePercent, int offsetXPercent, int offsetYPercent, int horizontalAlign, int verticalAlign) {
            marginVerticalPercent = Math.max(0, Math.min(40, marginVerticalPercent));
            marginSidePercent = Math.max(0, Math.min(40, marginSidePercent));
            offsetXPercent = Math.max(-50, Math.min(50, offsetXPercent));
            offsetYPercent = Math.max(-50, Math.min(50, offsetYPercent));
            horizontalAlign = Math.max(CLOCK_ALIGN_START, Math.min(CLOCK_ALIGN_END, horizontalAlign));
            verticalAlign = Math.max(CLOCK_ALIGN_START, Math.min(CLOCK_ALIGN_END, verticalAlign));
            boolean changed = this.marginVerticalPercent != marginVerticalPercent
                    || this.marginSidePercent != marginSidePercent
                    || this.offsetXPercent != offsetXPercent
                    || this.offsetYPercent != offsetYPercent
                    || this.horizontalAlign != horizontalAlign
                    || this.verticalAlign != verticalAlign;
            this.marginVerticalPercent = marginVerticalPercent;
            this.marginSidePercent = marginSidePercent;
            this.offsetXPercent = offsetXPercent;
            this.offsetYPercent = offsetYPercent;
            this.horizontalAlign = horizontalAlign;
            this.verticalAlign = verticalAlign;
            if (changed) {
                cachedTextSize = -1f;
                invalidate();
            }
        }

        void setTime(int hour24, int minute) {
            if (this.hour24 != hour24 || this.minute != minute) {
                this.hour24 = hour24;
                this.minute = minute;
                invalidate();
            }
        }

        void setBatteryPercent(int batteryPercent) {
            batteryPercent = Math.max(-1, Math.min(100, batteryPercent));
            if (this.batteryPercent != batteryPercent) {
                this.batteryPercent = batteryPercent;
                invalidate();
            }
        }

        void setBatteryVisible(boolean visible) {
            if (this.batteryVisible != visible) {
                this.batteryVisible = visible;
                this.cachedTextSize = -1f;
                invalidate();
            }
        }

        void setTopInfoText(String text) {
            if (text == null) text = "";
            if (!this.topInfoText.equals(text)) {
                this.topInfoText = text;
                this.cachedTextSize = -1f;
                invalidate();
            }
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            cachedTextSize = -1f;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int width = getWidth();
            int height = getHeight();
            if (width <= 0 || height <= 0) return;

            if (analog) {
                drawAnalog(canvas, width, height);
            } else {
                drawDigital(canvas, width, height);
            }
        }

        private void drawDigital(Canvas canvas, int width, int height) {
            RectF area = getClockArea(width, height);
            float availableWidth = Math.max(1f, area.width());
            float availableHeight = Math.max(1f, area.height());

            if (cachedTextSize <= 0f || cachedWidth != width || cachedHeight != height || cachedUse24Hour != use24Hour || cachedTypeface != clockTypeface) {
                cachedWidth = width;
                cachedHeight = height;
                cachedUse24Hour = use24Hour;
                cachedTypeface = clockTypeface;
                cachedTextSize = findBestClockTextSize(availableWidth, availableHeight);
            }

            String hourText;
            if (use24Hour) {
                hourText = String.format(Locale.US, "%02d", hour24);
            } else {
                int hour12 = hour24 % 12;
                if (hour12 == 0) hour12 = 12;
                hourText = String.format(Locale.US, "%02d", hour12);
            }
            String minuteText = String.format(Locale.US, "%02d", minute);
            String topText = getTopInfoText();
            String batteryText = getBatteryText();
            float infoSize = getInfoTextSize(cachedTextSize);
            float batterySize = getBatteryTextSize(cachedTextSize);
            Typeface infoTypeface = Typeface.create("sans-serif-medium", Typeface.NORMAL);

            paint.setTextSize(cachedTextSize);
            paint.setTypeface(clockTypeface);
            Paint.FontMetrics metrics = paint.getFontMetrics();
            float lineHeight = metrics.descent - metrics.ascent;
            float gap = cachedTextSize * 0.04f;
            float totalHeight = lineHeight * 2f + gap;
            float textWidth = Math.max(paint.measureText(hourText), paint.measureText(minuteText));

            if (topText.length() > 0) {
                totalHeight += getTextHeight(paint, infoSize, infoTypeface) + Math.max(3f, cachedTextSize * 0.055f);
                textWidth = Math.max(textWidth, measureTextWithTypeface(paint, topText, infoSize, infoTypeface));
            }

            if (!use24Hour && width > 120 && height > 120) {
                float amPmSize = Math.max(10f, cachedTextSize * 0.09f);
                totalHeight += Math.max(2f, cachedTextSize * 0.03f) + getTextHeight(paint, amPmSize, infoTypeface);
            }

            if (batteryText.length() > 0) {
                totalHeight += Math.max(3f, cachedTextSize * 0.06f) + getTextHeight(paint, batterySize, infoTypeface);
                textWidth = Math.max(textWidth, measureTextWithTypeface(paint, batteryText, batterySize, infoTypeface));
            }

            if (outlineEnabled) {
                textWidth += cachedTextSize * 0.06f;
                totalHeight += cachedTextSize * 0.06f;
            }
            float top = alignedTop(area, totalHeight) + height * offsetYPercent / 100f;
            float cursorY = top;
            float centerX = alignedCenterX(area, textWidth) + width * offsetXPercent / 100f;

            if (topText.length() > 0) {
                Typeface oldTypeface = paint.getTypeface();
                paint.setTypeface(infoTypeface);
                paint.setTextSize(infoSize);
                Paint.FontMetrics topMetrics = paint.getFontMetrics();
                float topBaseline = cursorY - topMetrics.ascent;
                drawOutlinedText(canvas, paint, topText, centerX, topBaseline, infoSize, false);
                cursorY = topBaseline + topMetrics.descent + Math.max(3f, cachedTextSize * 0.055f);
                paint.setTypeface(oldTypeface);
            }

            paint.setTypeface(clockTypeface);
            paint.setTextSize(cachedTextSize);
            metrics = paint.getFontMetrics();
            float baselineHour = cursorY - metrics.ascent;
            float baselineMinute = baselineHour + lineHeight + gap;

            drawOutlinedText(canvas, paint, hourText, centerX, baselineHour, cachedTextSize, true);
            drawOutlinedText(canvas, paint, minuteText, centerX, baselineMinute, cachedTextSize, true);

            float contentBottom = baselineMinute + metrics.descent;
            if (!use24Hour && width > 120 && height > 120) {
                Typeface oldTypeface = paint.getTypeface();
                paint.setTypeface(infoTypeface);
                float amPmSize = Math.max(10f, cachedTextSize * 0.09f);
                paint.setTextSize(amPmSize);
                Paint.FontMetrics amPmMetrics = paint.getFontMetrics();
                float amPmY = contentBottom + Math.max(2f, cachedTextSize * 0.03f) - amPmMetrics.ascent;
                drawOutlinedText(canvas, paint, hour24 < 12 ? "AM" : "PM", centerX, amPmY, amPmSize, false);
                contentBottom = amPmY + amPmMetrics.descent;
                paint.setTypeface(oldTypeface);
            }

            if (batteryText.length() > 0) {
                Typeface oldTypeface = paint.getTypeface();
                paint.setTypeface(infoTypeface);
                paint.setTextSize(batterySize);
                Paint.FontMetrics batteryMetrics = paint.getFontMetrics();
                float batteryY = contentBottom + Math.max(3f, cachedTextSize * 0.06f) - batteryMetrics.ascent;
                drawOutlinedText(canvas, paint, batteryText, centerX, batteryY, batterySize, false);
                paint.setTypeface(oldTypeface);
            }
        }

        private float findBestClockTextSize(float availableWidth, float availableHeight) {
            float low = 8f;
            float high = Math.max(12f, Math.min(availableWidth, availableHeight) * 1.25f);
            paint.setTypeface(clockTypeface);
            Typeface infoTypeface = Typeface.create("sans-serif-medium", Typeface.NORMAL);
            for (int i = 0; i < 28; i++) {
                float mid = (low + high) * 0.5f;
                paint.setTextSize(mid);
                Paint.FontMetrics metrics = paint.getFontMetrics();
                float lineHeight = metrics.descent - metrics.ascent;
                float gap = mid * 0.04f;
                float totalHeight = lineHeight * 2f + gap;
                float textWidth = Math.max(paint.measureText("88"), Math.max(paint.measureText("23"), paint.measureText("12")));

                String topText = getTopInfoText();
                if (topText.length() > 0) {
                    float infoSize = getInfoTextSize(mid);
                    totalHeight += getTextHeight(paint, infoSize, infoTypeface) + Math.max(3f, mid * 0.055f);
                    textWidth = Math.max(textWidth, measureTextWithTypeface(paint, topText, infoSize, infoTypeface));
                }

                if (!use24Hour) {
                    float amPmSize = Math.max(10f, mid * 0.09f);
                    totalHeight += Math.max(2f, mid * 0.03f) + getTextHeight(paint, amPmSize, infoTypeface);
                    textWidth = Math.max(textWidth, measureTextWithTypeface(paint, "PM", amPmSize, infoTypeface));
                }

                String batteryText = getBatteryText();
                if (batteryText.length() > 0) {
                    float batterySize = getBatteryTextSize(mid);
                    totalHeight += Math.max(3f, mid * 0.06f) + getTextHeight(paint, batterySize, infoTypeface);
                    textWidth = Math.max(textWidth, measureTextWithTypeface(paint, batteryText.length() > 0 ? batteryText : "100%", batterySize, infoTypeface));
                }

                if (outlineEnabled) {
                    textWidth += mid * 0.06f;
                    totalHeight += mid * 0.06f;
                }
                if (textWidth <= availableWidth && totalHeight <= availableHeight) {
                    low = mid;
                } else {
                    high = mid;
                }
            }
            return low;
        }

        private void drawAnalog(Canvas canvas, int width, int height) {
            RectF area = getClockArea(width, height);
            float availableWidth = Math.max(1f, area.width());
            float availableHeight = Math.max(1f, area.height());

            String topText = getTopInfoText();
            String batteryText = getBatteryText();
            Typeface infoTypeface = Typeface.create("sans-serif-medium", Typeface.NORMAL);

            float trialBase = Math.min(availableWidth, availableHeight);
            float infoSize = getInfoTextSize(trialBase * 0.72f);
            float batterySize = getBatteryTextSize(trialBase * 0.72f);
            Paint.FontMetrics topMetrics = null;
            Paint.FontMetrics batteryMetrics = null;
            float topGap = 0f;
            float topHeight = 0f;
            float batteryGap = 0f;
            float batteryHeight = 0f;

            if (topText.length() > 0) {
                Typeface oldTypeface = analogPaint.getTypeface();
                analogPaint.setTypeface(infoTypeface);
                analogPaint.setTextSize(infoSize);
                topMetrics = analogPaint.getFontMetrics();
                topHeight = topMetrics.descent - topMetrics.ascent;
                topGap = Math.max(4f, trialBase * 0.025f);
                analogPaint.setTypeface(oldTypeface);
            }
            if (batteryText.length() > 0) {
                Typeface oldTypeface = analogPaint.getTypeface();
                analogPaint.setTypeface(infoTypeface);
                analogPaint.setTextSize(batterySize);
                batteryMetrics = analogPaint.getFontMetrics();
                batteryHeight = batteryMetrics.descent - batteryMetrics.ascent;
                batteryGap = Math.max(4f, trialBase * 0.025f);
                analogPaint.setTypeface(oldTypeface);
            }

            float radius = Math.min(availableWidth * 0.5f, Math.max(1f, (availableHeight - topHeight - topGap - batteryHeight - batteryGap) * 0.5f));
            float diameter = radius * 2f;
            float totalHeight = topHeight + topGap + diameter + batteryGap + batteryHeight;
            float centerX = alignedCenterX(area, diameter) + width * offsetXPercent / 100f;
            float top = alignedTop(area, totalHeight) + height * offsetYPercent / 100f;

            if (topText.length() > 0 && topMetrics != null) {
                Typeface oldTypeface = analogPaint.getTypeface();
                analogPaint.setTypeface(infoTypeface);
                analogPaint.setTextSize(infoSize);
                float topBaseline = top - topMetrics.ascent;
                drawOutlinedText(canvas, analogPaint, topText, centerX, topBaseline, infoSize, false);
                analogPaint.setTypeface(oldTypeface);
            }

            float clockTop = top + topHeight + topGap;
            float centerY = clockTop + radius;

            analogPaint.setStyle(Paint.Style.STROKE);
            analogPaint.setStrokeWidth(Math.max(1.2f, radius * 0.018f));
            analogPaint.setColor(applyAlpha(numberColor, 225));
            tempRect.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
            if (outlineEnabled) {
                analogPaint.setStrokeWidth(Math.max(2.4f, radius * 0.032f));
                analogPaint.setColor(outlineColor);
                canvas.drawOval(tempRect, analogPaint);
                analogPaint.setStrokeWidth(Math.max(1.2f, radius * 0.018f));
                analogPaint.setColor(applyAlpha(numberColor, 225));
            }
            canvas.drawOval(tempRect, analogPaint);

            for (int i = 0; i < 60; i++) {
                double angle = Math.toRadians(i * 6.0 - 90.0);
                boolean major = i % 5 == 0;
                float outer = radius * 0.92f;
                float inner = radius * (major ? 0.80f : 0.86f);
                analogPaint.setStrokeWidth(major ? Math.max(1.6f, radius * 0.018f) : Math.max(1f, radius * 0.007f));
                analogPaint.setColor(major ? applyAlpha(numberColor, 235) : applyAlpha(numberColor, 150));
                float sx = centerX + (float) Math.cos(angle) * inner;
                float sy = centerY + (float) Math.sin(angle) * inner;
                float ex = centerX + (float) Math.cos(angle) * outer;
                float ey = centerY + (float) Math.sin(angle) * outer;
                canvas.drawLine(sx, sy, ex, ey, analogPaint);
            }

            if (radius >= 62f) {
                analogPaint.setStyle(Paint.Style.FILL);
                analogPaint.setTextSize(Math.max(14f, radius * 0.15f));
                analogPaint.setTypeface(clockTypeface);
                Paint.FontMetrics numberMetrics = analogPaint.getFontMetrics();
                float numberBaselineOffset = -(numberMetrics.ascent + numberMetrics.descent) / 2f;
                drawAnalogNumber(canvas, "12", centerX, centerY - radius * 0.66f + numberBaselineOffset);
                drawAnalogNumber(canvas, "3", centerX + radius * 0.66f, centerY + numberBaselineOffset);
                drawAnalogNumber(canvas, "6", centerX, centerY + radius * 0.66f + numberBaselineOffset);
                drawAnalogNumber(canvas, "9", centerX - radius * 0.66f, centerY + numberBaselineOffset);
            }

            float minuteAngle = (minute / 60f) * 360f - 90f;
            float hourAngle = ((hour24 % 12) / 12f) * 360f + (minute / 60f) * 30f - 90f;

            drawHand(canvas, centerX, centerY, hourAngle, radius * 0.46f, Math.max(4f, radius * 0.035f));
            drawHand(canvas, centerX, centerY, minuteAngle, radius * 0.70f, Math.max(2.5f, radius * 0.022f));

            analogPaint.setStyle(Paint.Style.FILL);
            if (outlineEnabled) {
                analogPaint.setColor(outlineColor);
                canvas.drawCircle(centerX, centerY, Math.max(5.5f, radius * 0.045f), analogPaint);
            }
            analogPaint.setColor(numberColor);
            canvas.drawCircle(centerX, centerY, Math.max(4f, radius * 0.032f), analogPaint);

            if (batteryText.length() > 0 && batteryMetrics != null) {
                Typeface oldTypeface = analogPaint.getTypeface();
                analogPaint.setTypeface(infoTypeface);
                analogPaint.setTextSize(batterySize);
                float batteryBaseline = clockTop + diameter + batteryGap - batteryMetrics.ascent;
                drawOutlinedText(canvas, analogPaint, batteryText, centerX, batteryBaseline, batterySize, false);
                analogPaint.setTypeface(oldTypeface);
            }
        }

        private String getBatteryText() {
            if (!batteryVisible || batteryPercent < 0) {
                return "";
            }
            return String.format(Locale.US, "%d%%", batteryPercent);
        }

        private String getTopInfoText() {
            return topInfoText == null ? "" : topInfoText.trim();
        }

        private float getBatteryTextSize(float baseSize) {
            return Math.max(9f, baseSize * 0.115f);
        }

        private float getInfoTextSize(float baseSize) {
            return Math.max(9f, baseSize * 0.105f);
        }

        private float getTextHeight(Paint targetPaint, float textSize, Typeface typeface) {
            Typeface oldTypeface = targetPaint.getTypeface();
            float oldTextSize = targetPaint.getTextSize();
            if (typeface != null) {
                targetPaint.setTypeface(typeface);
            }
            targetPaint.setTextSize(textSize);
            Paint.FontMetrics metrics = targetPaint.getFontMetrics();
            targetPaint.setTypeface(oldTypeface);
            targetPaint.setTextSize(oldTextSize);
            return metrics.descent - metrics.ascent;
        }

        private float measureTextWithTypeface(Paint targetPaint, String text, float textSize, Typeface typeface) {
            Typeface oldTypeface = targetPaint.getTypeface();
            float oldTextSize = targetPaint.getTextSize();
            if (typeface != null) {
                targetPaint.setTypeface(typeface);
            }
            targetPaint.setTextSize(textSize);
            float width = targetPaint.measureText(text);
            targetPaint.setTypeface(oldTypeface);
            targetPaint.setTextSize(oldTextSize);
            return width;
        }

        private RectF getClockArea(int width, int height) {
            float marginX = width * marginSidePercent / 100f;
            float marginY = height * marginVerticalPercent / 100f;
            if (marginX * 2f >= width) marginX = width * 0.45f;
            if (marginY * 2f >= height) marginY = height * 0.45f;
            return new RectF(marginX, marginY, width - marginX, height - marginY);
        }

        private float alignedCenterX(RectF area, float contentWidth) {
            if (horizontalAlign == CLOCK_ALIGN_START) {
                return area.left + contentWidth * 0.5f;
            }
            if (horizontalAlign == CLOCK_ALIGN_END) {
                return area.right - contentWidth * 0.5f;
            }
            return area.centerX();
        }

        private float alignedTop(RectF area, float contentHeight) {
            if (verticalAlign == CLOCK_ALIGN_START) {
                return area.top;
            }
            if (verticalAlign == CLOCK_ALIGN_END) {
                return area.bottom - contentHeight;
            }
            return area.top + (area.height() - contentHeight) * 0.5f;
        }

        private int makeClockShadowColor() {
            int opacity = Math.max(0, Math.min(100, shadowTintOpacity));
            int alpha = Math.max(35, Math.min(205, Math.round(55f + opacity * 1.35f)));
            return Color.argb(alpha, Color.red(shadowTintColor), Color.green(shadowTintColor), Color.blue(shadowTintColor));
        }

        private int applyAlpha(int color, int maxAlpha) {
            int existingAlpha = Color.alpha(color);
            int alpha = Math.min(existingAlpha, maxAlpha);
            return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
        }

        private void drawOutlinedText(Canvas canvas, Paint textPaint, String text, float x, float baseline, float textSize, boolean useClockTypeface) {
            Typeface oldTypeface = textPaint.getTypeface();
            Paint.Style oldStyle = textPaint.getStyle();
            int oldColor = textPaint.getColor();
            float oldStrokeWidth = textPaint.getStrokeWidth();
            float oldTextSize = textPaint.getTextSize();
            if (useClockTypeface) {
                textPaint.setTypeface(clockTypeface);
            }
            textPaint.setTextSize(textSize);
            if (outlineEnabled) {
                textPaint.setStyle(Paint.Style.STROKE);
                textPaint.setStrokeJoin(Paint.Join.ROUND);
                textPaint.setStrokeCap(Paint.Cap.ROUND);
                textPaint.setStrokeWidth(Math.max(2f, textSize * 0.035f));
                textPaint.setColor(outlineColor);
                canvas.drawText(text, x, baseline, textPaint);
            }
            textPaint.setStyle(Paint.Style.FILL);
            textPaint.setStrokeWidth(0f);
            textPaint.setColor(numberColor);
            canvas.drawText(text, x, baseline, textPaint);
            textPaint.setTypeface(oldTypeface);
            textPaint.setStyle(oldStyle);
            textPaint.setColor(oldColor);
            textPaint.setStrokeWidth(oldStrokeWidth);
            textPaint.setTextSize(oldTextSize);
        }

        private void drawAnalogNumber(Canvas canvas, String text, float x, float baseline) {
            drawOutlinedText(canvas, analogPaint, text, x, baseline, analogPaint.getTextSize(), true);
        }

        private void drawHand(Canvas canvas, float centerX, float centerY, float degrees, float length, float strokeWidth) {
            double angle = Math.toRadians(degrees);
            float endX = centerX + (float) Math.cos(angle) * length;
            float endY = centerY + (float) Math.sin(angle) * length;
            analogPaint.setStyle(Paint.Style.STROKE);
            analogPaint.setStrokeCap(Paint.Cap.ROUND);
            if (outlineEnabled) {
                analogPaint.setStrokeWidth(strokeWidth + Math.max(2f, strokeWidth * 0.75f));
                analogPaint.setColor(outlineColor);
                canvas.drawLine(centerX, centerY, endX, endY, analogPaint);
            }
            analogPaint.setStrokeWidth(strokeWidth);
            analogPaint.setColor(numberColor);
            canvas.drawLine(centerX, centerY, endX, endY, analogPaint);
            analogPaint.setStrokeCap(Paint.Cap.BUTT);
        }
    }

    private static class GradientOverlayView extends View {
        private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final Paint extraPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private int lastWidth = -1;
        private int lastHeight = -1;
        private int tintColor = DEFAULT_TINT_COLOR;
        private int tintOpacity = DEFAULT_TINT_OPACITY;
        private int backgroundStyle = DEFAULT_BACKGROUND_STYLE;

        GradientOverlayView(android.content.Context context) {
            super(context);
            setWillNotDraw(false);
        }

        void setTintSettings(int color, int opacity, int style) {
            opacity = Math.max(0, Math.min(100, opacity));
            style = Math.max(BG_STYLE_SOLID, Math.min(BG_STYLE_SPIRAL, style));
            if (tintColor != color || tintOpacity != opacity || backgroundStyle != style) {
                tintColor = color;
                tintOpacity = opacity;
                backgroundStyle = style;
                paint.setShader(null);
                extraPaint.setShader(null);
                lastWidth = -1;
                lastHeight = -1;
                invalidate();
            }
        }

        private int scaledTint(int alphaAtFullOpacity, float darken) {
            float baseAlpha = Color.alpha(tintColor) / 255f;
            int alpha = Math.max(0, Math.min(255, Math.round(alphaAtFullOpacity * (tintOpacity / 100f) * baseAlpha)));
            int r = Math.max(0, Math.min(255, Math.round(Color.red(tintColor) * darken)));
            int g = Math.max(0, Math.min(255, Math.round(Color.green(tintColor) * darken)));
            int b = Math.max(0, Math.min(255, Math.round(Color.blue(tintColor) * darken)));
            return Color.argb(alpha, r, g, b);
        }

        private int blackAlpha(int alphaAtFullOpacity) {
            return Math.max(0, Math.min(255, Math.round(alphaAtFullOpacity * (tintOpacity / 100f))));
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int width = getWidth();
            int height = getHeight();
            if (width <= 0 || height <= 0) return;

            if (backgroundStyle == BG_STYLE_SOLID) {
                canvas.drawColor(scaledTint(235, 1.0f));
                return;
            }

            if (width != lastWidth || height != lastHeight || paint.getShader() == null) {
                lastWidth = width;
                lastHeight = height;
                buildShader(width, height);
            }

            if (backgroundStyle == BG_STYLE_SQUARE) {
                canvas.drawColor(Color.argb(blackAlpha(190), 0, 0, 0));
                int layers = 18;
                float maxInset = Math.min(width, height) * 0.48f;
                for (int i = 0; i < layers; i++) {
                    float t = i / (float) (layers - 1);
                    float inset = maxInset * t;
                    int c = scaledTint(Math.round(210 * (1f - t * 0.72f)), 1.0f - t * 0.55f);
                    paint.setShader(null);
                    paint.setColor(c);
                    canvas.drawRect(inset, inset, width - inset, height - inset, paint);
                }
                extraPaint.setShader(new RadialGradient(
                        width * 0.5f,
                        height * 0.42f,
                        Math.max(width, height) * 0.75f,
                        Color.argb(0, 0, 0, 0),
                        Color.argb(blackAlpha(260), 0, 0, 0),
                        Shader.TileMode.CLAMP));
                canvas.drawRect(0, 0, width, height, extraPaint);
                return;
            }

            canvas.drawRect(0, 0, width, height, paint);
            if (backgroundStyle == BG_STYLE_SPIRAL) {
                extraPaint.setShader(new RadialGradient(
                        width * 0.5f,
                        height * 0.45f,
                        Math.max(width, height) * 0.72f,
                        Color.argb(0, 0, 0, 0),
                        Color.argb(blackAlpha(285), 0, 0, 0),
                        Shader.TileMode.CLAMP));
                canvas.drawRect(0, 0, width, height, extraPaint);
            }
        }

        private void buildShader(int width, int height) {
            if (backgroundStyle == BG_STYLE_VERTICAL) {
                paint.setShader(new LinearGradient(
                        0, 0, 0, height,
                        new int[]{scaledTint(240, 1.0f), scaledTint(210, 0.45f), Color.argb(blackAlpha(310), 0, 0, 0)},
                        new float[]{0f, 0.48f, 1f},
                        Shader.TileMode.CLAMP));
            } else if (backgroundStyle == BG_STYLE_DIAGONAL) {
                paint.setShader(new LinearGradient(
                        0, 0, width, height,
                        new int[]{scaledTint(250, 1.05f), scaledTint(225, 0.45f), Color.argb(blackAlpha(320), 0, 0, 0)},
                        new float[]{0f, 0.55f, 1f},
                        Shader.TileMode.CLAMP));
            } else if (backgroundStyle == BG_STYLE_SPIRAL) {
                paint.setShader(new SweepGradient(
                        width * 0.5f,
                        height * 0.45f,
                        new int[]{scaledTint(230, 1.05f), scaledTint(80, 0.65f), scaledTint(215, 0.42f), scaledTint(120, 0.85f), scaledTint(230, 1.05f)},
                        new float[]{0f, 0.24f, 0.50f, 0.76f, 1f}));
            } else {
                float radius = Math.max(width, height) * 0.82f;
                paint.setShader(new RadialGradient(
                        width * 0.5f,
                        height * 0.38f,
                        radius,
                        new int[]{
                                scaledTint(240, 1.00f),
                                scaledTint(305, 0.45f),
                                Color.argb(blackAlpha(350), 0, 0, 0)
                        },
                        new float[]{0.0f, 0.55f, 1.0f},
                        Shader.TileMode.CLAMP
                ));
            }
        }
    }

    private static class DrawerBackgroundView extends View {
        private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final Paint topPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private int lastWidth = -1;
        private int lastHeight = -1;
        private int tintColor = DEFAULT_TINT_COLOR;
        private int tintOpacity = DEFAULT_TINT_OPACITY;
        private int backgroundStyle = DEFAULT_BACKGROUND_STYLE;

        DrawerBackgroundView(android.content.Context context) {
            super(context);
            setWillNotDraw(false);
        }

        void setTintSettings(int color, int opacity, int style) {
            opacity = Math.max(0, Math.min(100, opacity));
            style = Math.max(BG_STYLE_SOLID, Math.min(BG_STYLE_SPIRAL, style));
            if (tintColor != color || tintOpacity != opacity || backgroundStyle != style) {
                tintColor = color;
                tintOpacity = opacity;
                backgroundStyle = style;
                paint.setShader(null);
                topPaint.setShader(null);
                lastWidth = -1;
                lastHeight = -1;
                invalidate();
            }
        }

        private int scaledTint(int alphaAtFullOpacity, float darken) {
            float baseAlpha = Color.alpha(tintColor) / 255f;
            int alpha = Math.max(0, Math.min(255, Math.round(alphaAtFullOpacity * (tintOpacity / 100f) * baseAlpha)));
            int r = Math.max(0, Math.min(255, Math.round(Color.red(tintColor) * darken)));
            int g = Math.max(0, Math.min(255, Math.round(Color.green(tintColor) * darken)));
            int b = Math.max(0, Math.min(255, Math.round(Color.blue(tintColor) * darken)));
            return Color.argb(alpha, r, g, b);
        }

        private int blackAlpha(int alphaAtFullOpacity) {
            return Math.max(0, Math.min(255, Math.round(alphaAtFullOpacity * (tintOpacity / 100f))));
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int width = getWidth();
            int height = getHeight();
            if (width <= 0 || height <= 0) return;

            if (backgroundStyle == BG_STYLE_SOLID) {
                canvas.drawColor(scaledTint(245, 0.85f));
                canvas.drawColor(Color.argb(blackAlpha(210), 0, 0, 0));
                return;
            }

            if (backgroundStyle == BG_STYLE_SQUARE) {
                canvas.drawColor(Color.argb(blackAlpha(230), 0, 0, 0));
                int layers = 16;
                float maxInset = Math.min(width, height) * 0.46f;
                for (int i = 0; i < layers; i++) {
                    float t = i / (float) (layers - 1);
                    float inset = maxInset * t;
                    paint.setShader(null);
                    paint.setColor(scaledTint(Math.round(260 * (1f - t * 0.65f)), 0.9f - t * 0.55f));
                    canvas.drawRect(inset, inset, width - inset, height - inset, paint);
                }
                return;
            }

            if (width != lastWidth || height != lastHeight || paint.getShader() == null) {
                lastWidth = width;
                lastHeight = height;
                buildShader(width, height);
                topPaint.setShader(new LinearGradient(
                        0, 0, 0, Math.max(1, height * 0.30f),
                        Color.argb(blackAlpha(300), 0, 0, 0),
                        Color.argb(0, 0, 0, 0),
                        Shader.TileMode.CLAMP
                ));
            }
            canvas.drawRect(0, 0, width, height, paint);
            canvas.drawRect(0, 0, width, height * 0.35f, topPaint);
        }

        private void buildShader(int width, int height) {
            if (backgroundStyle == BG_STYLE_VERTICAL) {
                paint.setShader(new LinearGradient(
                        0, 0, 0, height,
                        new int[]{scaledTint(325, 0.90f), scaledTint(290, 0.28f), Color.argb(blackAlpha(360), 0, 0, 0)},
                        new float[]{0f, 0.50f, 1f},
                        Shader.TileMode.CLAMP));
            } else if (backgroundStyle == BG_STYLE_DIAGONAL) {
                paint.setShader(new LinearGradient(
                        0, 0, width, height,
                        new int[]{scaledTint(335, 0.95f), scaledTint(300, 0.30f), Color.argb(blackAlpha(360), 0, 0, 0)},
                        new float[]{0f, 0.52f, 1f},
                        Shader.TileMode.CLAMP));
            } else if (backgroundStyle == BG_STYLE_SPIRAL) {
                paint.setShader(new SweepGradient(
                        width * 0.5f,
                        height * 0.25f,
                        new int[]{scaledTint(330, 0.95f), scaledTint(150, 0.55f), scaledTint(320, 0.25f), scaledTint(170, 0.75f), scaledTint(330, 0.95f)},
                        new float[]{0f, 0.24f, 0.55f, 0.80f, 1f}));
            } else {
                paint.setShader(new RadialGradient(
                        width * 0.5f,
                        height * 0.12f,
                        Math.max(width, height) * 0.85f,
                        new int[]{
                                scaledTint(345, 0.95f),
                                scaledTint(354, 0.30f),
                                Color.argb(blackAlpha(360), 0, 0, 0)
                        },
                        new float[]{0.0f, 0.50f, 1.0f},
                        Shader.TileMode.CLAMP
                ));
            }
        }
    }}
