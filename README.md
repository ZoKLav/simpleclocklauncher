# Simple Clock Launcher

A deliberately minimal Android home launcher for people who opened a modern launcher, saw six search bars, three feeds, a sponsored widget, and a motivational weather frog, and decided civilization had made a wrong turn.

Simple Clock Launcher shows the time, opens apps, and otherwise keeps its mouth shut. Radical stuff.

## Purpose

This launcher exists to be a quiet home screen, not a brand ecosystem with commitment issues.

It is built around a large clock, a swipe-up app drawer, and enough settings to make it usable on normal phones, weird tiny Android watches, and other devices that Android technically supports while quietly pretending not to notice.

## Features

### Home screen clock

The home screen is dominated by a clock because the app is called Simple Clock Launcher and apparently we still need to reward names that describe things accurately.

Supported clock behavior includes:

- 24-hour digital clock
- 12-hour digital clock
- Analog clock mode
- Mutually exclusive clock mode selection
- Custom number color
- Optional clock outline
- Custom outline color
- Device font selection
- Adjustable clock margins
- Adjustable X/Y offsets
- Horizontal alignment: left, center, right
- Vertical alignment: top, center, bottom

If you use the layout controls to make the clock unreadable, that is not a bug. That is you successfully operating the machinery of your own bad taste.

### App drawer

Swipe up to open the app drawer. It launches apps, which is apparently a feature worth mentioning in a world where launchers increasingly behave like shopping malls with wallpaper.

The app drawer includes:

- Alphabetical app list
- Search bar
- Adjustable column count
- Tap-to-launch behavior
- Small `Made by ZoeyKL` watermark
- Optional passcode protection
- Failed-attempt lockout timer

The app drawer lock is a launcher-level inconvenience lock. It is not encryption, not device-owner management, not a real lock screen, and not a tiny cyberpunk vault living inside your phone. If someone has Android Settings, ADB, safe mode, physical access, or two functioning brain cells, they may still find ways around it.

### Settings access

Hold the clock for about one second to open launcher settings.

Once app drawer locking is enabled, the same passcode protects launcher settings too, because otherwise the lock would be a cardboard fence around a door marked "disable lock here."

### Wallpaper and system bars

The launcher uses Android wallpaper passthrough and includes an immersive fullscreen option.

Android's notification shade belongs to SystemUI, not the launcher. That means this app cannot force the notification panel to tint itself correctly just because everyone involved would be happier if it did. Some Android versions and vendor skins composite the notification shade over wallpaper in strange ways. The fullscreen toggle exists partly as a workaround and partly as a monument to Android being Android.

## What this launcher does not include

This launcher does not include:

- Widgets
- Dock
- News feed
- Account login
- Ads
- Analytics
- Internet permission
- Cloud sync
- AI features
- A monetization funnel wearing a trench coat and calling itself customization

If you need those things, there are plenty of launchers waiting to turn your home screen into a sponsored airport kiosk.

## Security notes

The passcode feature only protects the launcher app drawer and launcher settings inside this launcher. It does not secure the device.

Use Android's actual lock screen if you need actual security. This should not need to be said, but here we are, carefully placing cones around the obvious.

## Compatibility target

The source is intended for Android 7.0 Nougat / API 24 and newer.

It is written in plain Java with traditional Android Views. This is intentional. Jetpack Compose is nice until you want a tiny, fast launcher that behaves on old phones, oddball watches, and haunted Android hardware from the bin marked "technically supported."

## Repository contents

This is a source-only archive. It does not include Gradle wrapper files, build output, downloaded Gradle distributions, `.gradle` directories, IDE trash, or local SDK paths from somebody else's cursed Windows install.

Relevant source tree:

```text
app/src/main/AndroidManifest.xml
app/src/main/java/com/zoey/simpleclocklauncher/MainActivity.java
app/src/main/res/
```

Bring your own Android project/build setup. If you are using Android Studio, create or open an Android app project and copy this `app/src/main` tree into the app module.

## Building

This archive intentionally does not ship with Gradle wrapper files.

You can build it however you normally build Android apps. The expected setup is:

```text
Minimum SDK: 24
Target/compile SDK: your installed modern Android SDK platform
Language: Java
UI: traditional Android Views
```

If your build fails because your SDK, Gradle, Java, PATH, username, drive location, or moon phase is wrong, that is not a launcher feature. That is Windows and Android development forming a joint task force against joy.

## Release model

The APK is expected to be uploaded separately as a release artifact. This repository/source archive exists so people can inspect the code, fork it, judge it, patch it, break it, or open an issue explaining that it does not contain a feature it very clearly never claimed to contain.

## Support policy

There is no warranty.

Reasonable bug reports are welcome. Feature requests may be considered if they do not turn the launcher into a productivity cult, a widget landfill, or a subscription service with icons.

Requests for ads, analytics, social feeds, crypto widgets, shopping integrations, mandatory accounts, or anything that makes the home screen worse will be treated with the solemn respect normally reserved for expired coupons and wet cardboard.

![Preview](https://i.imgur.com/VfjPFIc.png)
![Preview](https://i.imgur.com/56kdopv.png)
![Preview](https://i.imgur.com/QoJwom9.png)
