package ma.ensaf.biblio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import ma.ensaf.biblio.databinding.ActivityAccueilBinding;
import ma.ensaf.biblio.databinding.ActivityMainBinding;

public class Accueil extends drawer_base {
Button bouton;
FirebaseAuth auth;
FirebaseUser firebaseUser;
DatabaseReference ref;
String id;
    ActivityAccueilBinding activityAccueilBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAccueilBinding= ActivityAccueilBinding.inflate(getLayoutInflater());
        setContentView(activityAccueilBinding.getRoot());
        activityTitle("Accueil");
        id=getIntent().getStringExtra("id");
        ref=FirebaseDatabase.getInstance().getReference("Emprunts");
        ref.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if((Long)snapshot.child("daysLeft").getValue() ==3){
                    //    Toast.makeText(Accueil.this, "yohoo", Toast.LENGTH_SHORT).show();
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(Accueil.this)
                            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                            .setContentTitle("Vous devez rendre le livre")
                            .setContentText("Il vous reste "+snapshot.child("daysLeft").getValue()+" jours");

                    // Creates the intent needed to show the notification
                    Intent notificationIntent = new Intent(Accueil.this, Accueil.class);
                    PendingIntent contentIntent = PendingIntent.getActivity(Accueil.this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(contentIntent);

                    // Add as notification
                    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.notify(0, builder.build());
                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Accueil.this, "didn't work", Toast.LENGTH_SHORT).show();
            }
        });
      /*  bouton=findViewById(R.id.button2);
        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });*/
    }

    private void addBook() {
       // Book bk=new Book("another","Paul Kimmel","juste pour tester","https://firebasestorage.googleapis.com/v0/b/biblio-ca7a8.appspot.com/o/advanced-C-Sharp-programming.png?alt=media&token=36c8a88d-cbb6-4707-93f4-cca53bef3019");
//        String bookId=auth.getUid();
        //setup data to upload
     /*   HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("bookid",""+bookId);
        hashMap.put("auteur","Paul Kimmel");
        hashMap.put("pages","251");
        hashMap.put("titre","Advanced C#");
        hashMap.put("description","juste pour tester");
        hashMap.put("image","https://firebasestorage.googleapis.com/v0/b/biblio-ca7a8.appspot.com/o/advanced-C-Sharp-programming.png?alt=media&token=36c8a88d-cbb6-4707-93f4-cca53bef3019");
*/
        //add books to db
    //   ref.push().setValue(bk);
       Toast.makeText(Accueil.this,"lol",Toast.LENGTH_SHORT).show();




    }
}