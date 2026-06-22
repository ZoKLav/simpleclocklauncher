package com.zoey.simpleclocklauncher;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import java.util.Locale;

public class NotificationCounterService extends NotificationListenerService {
    private static final String PREFS_NAME = "launcher_settings";
    private static final String PREF_NOTIFICATION_SMS_COUNT = "notification_sms_count";
    private static final String PREF_NOTIFICATION_EMAIL_COUNT = "notification_email_count";
    private static final String ACTION_COUNTERS_UPDATED = "com.zoey.simpleclocklauncher.COUNTERS_UPDATED";

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        updateCounts();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        updateCounts();
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        updateCounts();
    }

    private void updateCounts() {
        int sms = 0;
        int email = 0;
        try {
            StatusBarNotification[] notifications = getActiveNotifications();
            if (notifications != null) {
                for (StatusBarNotification sbn : notifications) {
                    if (sbn == null || sbn.getPackageName() == null) continue;
                    Notification notification = sbn.getNotification();
                    if (notification == null) continue;
                    if (Build.VERSION.SDK_INT >= 20 && (notification.flags & Notification.FLAG_GROUP_SUMMARY) != 0) {
                        continue;
                    }
                    String pkg = sbn.getPackageName().toLowerCase(Locale.US);
                    int amount = notification.number > 0 ? notification.number : 1;
                    if (isSmsPackage(pkg)) {
                        sms += amount;
                    } else if (isEmailPackage(pkg)) {
                        email += amount;
                    }
                }
            }
        } catch (Exception ignored) {
        }

        try {
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            prefs.edit()
                    .putInt(PREF_NOTIFICATION_SMS_COUNT, Math.max(0, sms))
                    .putInt(PREF_NOTIFICATION_EMAIL_COUNT, Math.max(0, email))
                    .apply();
            Intent intent = new Intent(ACTION_COUNTERS_UPDATED);
            intent.setPackage(getPackageName());
            sendBroadcast(intent);
        } catch (Exception ignored) {
        }
    }

    private boolean isSmsPackage(String pkg) {
        return pkg.equals("com.google.android.apps.messaging")
                || pkg.equals("com.samsung.android.messaging")
                || pkg.equals("com.android.messaging")
                || pkg.equals("com.android.mms")
                || pkg.equals("com.android.mms.service")
                || pkg.equals("com.google.android.apps.googlevoice")
                || pkg.equals("com.verizon.messaging.vzmsgs")
                || pkg.equals("com.motorola.messaging")
                || pkg.contains("messaging") && pkg.contains("sms")
                || pkg.contains("mms");
    }

    private boolean isEmailPackage(String pkg) {
        return pkg.equals("com.google.android.gm")
                || pkg.equals("com.google.android.email")
                || pkg.equals("com.android.email")
                || pkg.equals("com.samsung.android.email.provider")
                || pkg.equals("com.microsoft.office.outlook")
                || pkg.equals("com.yahoo.mobile.client.android.mail")
                || pkg.equals("ch.protonmail.android")
                || pkg.equals("me.proton.android.mail")
                || pkg.equals("me.bluemail.mail")
                || pkg.equals("com.fsck.k9")
                || pkg.equals("app.k9mail")
                || pkg.equals("org.kman.AquaMail")
                || pkg.equals("com.readdle.spark")
                || pkg.equals("com.fastmail.app")
                || pkg.contains("mail")
                || pkg.contains("email")
                || pkg.contains("outlook");
    }
}
