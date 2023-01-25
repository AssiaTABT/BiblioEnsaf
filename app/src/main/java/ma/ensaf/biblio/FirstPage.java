package ma.ensaf.biblio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirstPage extends AppCompatActivity {
private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        firebaseAuth=FirebaseAuth.getInstance();
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
               /* Intent mainIntent = new Intent(FirstPage.this,Login.class);
                FirstPage.this.startActivity(mainIntent);
                finish();*/
                checkUser();
            }
        }, 2000);


    }

    private void checkUser() {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser==null){
            //user not logged in
            //start main screen
           startActivity(new Intent(FirstPage.this,Login.class));
            finish();//finsih this activity
        }else{
            //user logged in
            //check in db
            DatabaseReference df= FirebaseDatabase.getInstance().getReference("Users");
            //extract the date from the document
            df.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String userType=""+snapshot.child("userType").getValue();
                    if(userType.equals("Prof")){
                        startActivity(new Intent(getApplicationContext(),Accueil.class));
                        finish();
                    }else if(userType.equals("Student")){
                        startActivity(new Intent(getApplicationContext(),Accueil.class));
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}
