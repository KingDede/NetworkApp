package jason.networkapp;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;

public class NotificationService extends IntentService {

    private PowerManager.WakeLock mWakeLock;
    RemoteDBHelper remoteDBHelper;
    Context context = this;
    SharedPreferences sharedPref;

    public NotificationService() {
        super("NotificationService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // obtain the wake lock
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Service");
        mWakeLock.acquire();

        // check the global background data setting
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (!cm.getActiveNetworkInfo().isConnected()) {
            stopSelf();
            return;
        }

        remoteDBHelper = new RemoteDBHelper(context);
        ArrayList<UpdateData> checkUpdates;

        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String last_update = sharedPref.getString(getApplicationContext().getString(R.string.last_update), "2001-01-01 00:00:00");
        Log.e("date in service", "" + last_update);
        checkUpdates = remoteDBHelper.update(last_update);

        Intent i = new Intent("jason.networkapp.android.NOTIFY");
        if (checkUpdates.size() > 0) {
            sendBroadcast(i);
        }
        stopSelf();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        onHandleIntent(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onHandleIntent(intent);
        Log.e("service", " service started");
        return START_NOT_STICKY;
    }

    public void onDestroy() {
        super.onDestroy();
        Log.e("service: ", "Stopped");
        mWakeLock.release();
    }
}
