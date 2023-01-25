package ma.ensaf.biblio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.HeaderViewListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class header extends AppCompatActivity {
TextView email,nom;
    String Id;
    FirebaseAuth auth;

    FirebaseUser firebaseUser;
    DatabaseReference df;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header);
        nom = findViewById(R.id.nomComplet2);
        auth=FirebaseAuth.getInstance();
        Id=auth.getCurrentUser().getUid();
        if(Id!=null){
            getData();
        }else{
            Toast.makeText(header.this, "NO DATA", Toast.LENGTH_SHORT).show();
        }


        df = FirebaseDatabase.getInstance().getReference("Users");

    }

    private void getData() {
        df.child(Id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                nom.setText( snapshot.child("FullName").getValue(String.class));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}