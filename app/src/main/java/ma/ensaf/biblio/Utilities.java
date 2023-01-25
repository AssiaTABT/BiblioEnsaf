package ma.ensaf.biblio;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.databinding.adapters.Converters;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;

public class Utilities {
    public static void setAlarmIfRequired(Context context) {

        Intent notificationIntent = new Intent(context, NotificationAlarmReceiver.class);
        final PendingIntent pendingIntent =
                PendingIntent.getBroadcast(context, 0, notificationIntent,
                        PendingIntent.FLAG_NO_CREATE);
        if (pendingIntent == null) {
            // There is no alarm set for same
            //Set Notifications AlarmManager
            AlarmManager AM = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pending = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            cal.set(Calendar.HOUR_OF_DAY, 18); //Fire Alarm at 18:00
            cal.set(Calendar.MINUTE, 00);
            cal.set(Calendar.SECOND,0);
            AM.setRepeating(AlarmManager.RTC, cal.getTimeInMillis(), AlarmManager.INTERVAL_HALF_DAY , pending); //12 Hour Interval
        }
    }


}
