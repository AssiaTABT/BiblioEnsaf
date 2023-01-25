package ma.ensaf.biblio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class Recommend extends AppCompatActivity {
    EditText titre;
    Button send;
    DatabaseReference ref;
    User user;
    FirebaseUser auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        auth=FirebaseAuth.getInstance().getCurrentUser();
        ref= FirebaseDatabase.getInstance().getReference("Recommands");
        titre=findViewById(R.id.booktitre);
        send=findViewById(R.id.recom);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();

            }
        });
    }

    private void add() {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("uid", auth.getUid());
        userInfo.put("titre",titre.getText().toString());

        ref.child(auth.getUid()).setValue(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Recommend.this, "Thanks for recommending", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Recommend.this, Accueil.class);
                startActivity(intent);
                finish();
            }
        })   .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Recommend.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        })     ;
    }
}