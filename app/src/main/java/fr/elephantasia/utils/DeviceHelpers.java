package fr.elephantasia.utils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;

public class DeviceHelpers {

  static public boolean IsConnected(Context context) {
    ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
    if (cm == null) return false;
    NetworkInfo networkInfo = cm.getActiveNetworkInfo();

    return (networkInfo != null && networkInfo.isConnected());
  }

  static public int GetBatteryPercent(Context context) {
    int level = 100;
    IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    Intent batteryStatus = context.registerReceiver(null, ifilter);

    if (batteryStatus != null) {
      level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
    }
    return level;
  }

  static public boolean IsBatteryCharging(Context context) {
    IntentFilter batteryIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    Intent intent = context.registerReceiver(null, batteryIntentFilter);
    if (intent == null) return false;
    int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
    return (plugged == BatteryManager.BATTERY_PLUGGED_AC
      || plugged == BatteryManager.BATTERY_PLUGGED_USB
      || plugged == BatteryManager.BATTERY_PLUGGED_WIRELESS);
  }

}
