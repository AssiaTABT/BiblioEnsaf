package ma.ensaf.biblio.EspaceAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import ma.ensaf.biblio.R;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ma.ensaf.biblio.Adapters.EEAdapter;
import ma.ensaf.biblio.Models.User;

public class EEEActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ImageButton backBtn;
    private ArrayList<User> UserArrayList;
    private EEAdapter userAdapter;
    private RecyclerView recyclerView;
    private EditText SearchUser;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eeeactivity);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        backBtn = (ImageButton) findViewById(R.id.BackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.profRVV);

        loadProf();
    }

    private void loadProf() {

        progressDialog.setMessage("Recherche des Etudiants");
        progressDialog.show();


        UserArrayList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");

        Query query1=ref.orderByChild("userType").equalTo("Student");
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserArrayList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    //get data
                    User model = new User();

                    //add to arraylist


                    String name = ds.child("FullName").getValue().toString();
                    String Email = ds.child("Email").getValue().toString();
                    String filiere1 = ds.child("Filiere").getValue().toString();
                    model.setFullName(name);
                    model.setEmail(Email);
                    //model.setFiliere(filiere1);

                   // Toast.makeText(EEEActivity.this, ""+filiere1, Toast.LENGTH_SHORT).show();
                    UserArrayList.add(model);


                }


                //setupAdapter
                userAdapter = new EEAdapter(EEEActivity.this,UserArrayList);

                //set Adapter to RecyclerView
                recyclerView.setAdapter(userAdapter);

                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}