package ma.ensaf.biblio;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationAlarmReceiver extends BroadcastReceiver {
    DatabaseReference db;
    FirebaseAuth firebaseAuth;

    private static final int NOTIFICATION_ID = 5413;
    private static int DAYS_LEFT_NOTIFY = 3; //Notify when DAYS_LEFT_NOTIFY days are left before Deadline
    public NotificationAlarmReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        //Set Alarm After Reboot
        Utilities.setAlarmIfRequired(context);

        ArrayList<String> notificationList = new ArrayList<String>();

        //Import Library from SharedPrefs
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child("Emprunts");

        db = FirebaseDatabase.getInstance().getReference("Users");
        db.child(firebaseAuth.getUid()).child("Emprunts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Book bk = new Book();
                    bk.setBookId(snapshot.child("bookId").getValue().toString());
                    Borrow br = new Borrow(bk);

                    notificationList.add(bk.getTitre() + " is due in " + br.getDaysLeft() + " days");
                    if (!notificationList.isEmpty()) {

                        //Set an Inbox Style for notifications
                        NotificationCompat.InboxStyle inboxStyle =
                                new NotificationCompat.InboxStyle();
                        // Moves Messages into the expanded layout
                        for (int i = 0; i < notificationList.size(); i++)
                            inboxStyle.addLine(notificationList.get(i));
                        inboxStyle.setSummaryText("Check your book list");
                        // Sets a title for the Inbox in expanded layout
                        inboxStyle.setBigContentTitle("You Have Unreturned Books: ");

                        Intent returnIntent = new Intent(context.getApplicationContext(), Details.class);
                        returnIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        PendingIntent pendingIntent =
                                PendingIntent.getActivity(
                                        context.getApplicationContext(),
                                        0,
                                        returnIntent,
                                        PendingIntent.FLAG_UPDATE_CURRENT
                                );

                        Notification bookNotification = new NotificationCompat.Builder(context)
                                .setContentTitle("You Have Unreturned Books")
                                .setSmallIcon(R.drawable.ic_audiotrack_dark)
                                .setStyle(inboxStyle)
                                .setContentIntent(pendingIntent)
                                .setLights(0xff00ff00, 1000, 100)
                                .setAutoCancel(true)
                                .build();

                        //Add Vibrate, Sound, and Lights
                        bookNotification.defaults |= Notification.DEFAULT_VIBRATE;
                        bookNotification.defaults |= Notification.DEFAULT_SOUND;


                        //Notify
                        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
                        notificationManager.notify(NOTIFICATION_ID, bookNotification);
                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(this.getClass().getSimpleName(), "No Library imported");
                return;
            }
        });


    }}

