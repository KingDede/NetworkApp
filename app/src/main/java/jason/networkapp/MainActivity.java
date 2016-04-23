package jason.networkapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private DBHelper DBHelper;
    private RemoteDBHelper RemoteDBHelper;
    private SharedPreferences sharedPref;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper = new DBHelper(this);
        RemoteDBHelper = new RemoteDBHelper(this);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        final Context context = this;
        File database = getApplicationContext().getDatabasePath("Gw2Build");

        ImageButton eleBtn = (ImageButton)findViewById(R.id.eleButton);
        ImageButton mesBtn = (ImageButton)findViewById(R.id.mesButton);
        ImageButton necroBtn = (ImageButton)findViewById(R.id.necroButton);
        ImageButton rangerBtn = (ImageButton)findViewById(R.id.rangerButton);
        ImageButton thiefBtn = (ImageButton)findViewById(R.id.teefButton);
        ImageButton engiBtn = (ImageButton)findViewById(R.id.engiButton);
        ImageButton warrBtn = (ImageButton)findViewById(R.id.warrButton);
        ImageButton guardBtn = (ImageButton)findViewById(R.id.guardButton);
        ImageButton revBtn = (ImageButton)findViewById(R.id.revButton);

        eleBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(context, ListActivity.class);
                i.putExtra("profession", "elementalist");
                startActivity(i);
            }
        });
        mesBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(context, ListActivity.class);
                i.putExtra("profession", "mesmer");
                startActivity(i);
            }
        });
        necroBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(context, ListActivity.class);
                i.putExtra("profession", "necromancer");
                startActivity(i);
            }
        });
        rangerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(context, ListActivity.class);
                i.putExtra("profession", "ranger");
                startActivity(i);
            }
        });
        thiefBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(context, ListActivity.class);
                i.putExtra("profession", "thief");
                startActivity(i);
            }
        });
        engiBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(context, ListActivity.class);
                i.putExtra("profession", "engineer");
                startActivity(i);
            }
        });
        warrBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(context, ListActivity.class);
                i.putExtra("profession", "warrior");
                startActivity(i);
            }
        });
        guardBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(context, ListActivity.class);
                i.putExtra("profession", "guardian");
                startActivity(i);
            }
        });
        revBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(context, ListActivity.class);
                i.putExtra("profession", "revenant");
                startActivity(i);
            }
        });

        if (!database.exists()) {
            // Database does not exist so copy it from assets here
            String updateDate;
//            updateDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            updateDate = sdf.format(new Date());
            SharedPreferences.Editor editPref = sharedPref.edit();
            editPref.putString(getString(R.string.last_update), updateDate);
            editPref.apply();
            DBHelper.getWritableDatabase();
            Log.i("Database", "Not Found");
            Log.i("Time", updateDate);
        } else {
            Log.i("Database", "Found");
        }
        if(!sharedPref.contains("interval")) {
            SharedPreferences.Editor editPref = sharedPref.edit();
            editPref.putInt("interval", 1);
            editPref.apply();
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int count = DBHelper.specRowCount();
//
//                if (count != -1) {
//                    Snackbar.make(view, "Number of rows in specializations table: " + count, Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                } else {
//                    Snackbar.make(view, "no rows to count", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                }
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sync) {
            String update = DBHelper.updateDB(sharedPref.getString(getString(R.string.last_update), "2000-01-01 00:00:00"));
            SharedPreferences.Editor editPref = sharedPref.edit();
            editPref.putString(getString(R.string.last_update), update);
            editPref.apply();
            Toast.makeText(MainActivity.this, "Updated!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_notify) {
            int notify = sharedPref.getInt("interval", 0);
            if (notify > 0) {
                SharedPreferences.Editor editPref = sharedPref.edit();
                editPref.putInt("interval", 0);
                editPref.apply();
                Toast.makeText(this, "Notifications Off", Toast.LENGTH_SHORT).show();
            }
            else {
                SharedPreferences.Editor editPref = sharedPref.edit();
                editPref.putInt("interval", 1);
                editPref.apply();
                Toast.makeText(this, "Notifications On", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onResume() {
        super.onResume();
        int minutes = sharedPref.getInt("interval", 0);
        Log.e("resumed interval: ", String.valueOf(minutes));
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent i = new Intent().setClass(this, NotificationService.class);
        //i.putExtra("last_update", sharedPref.getString(getString(R.string.last_update), "2001-01-01 00:00:00"));
        PendingIntent pi = PendingIntent.getService(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        am.cancel(pi);
        // minutes <= 0 means notifications are disabled
        if (minutes > 0) {
            am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    0,
                    20000, pi);
        }
    }

}
