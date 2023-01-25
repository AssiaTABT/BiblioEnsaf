package ma.ensaf.biblio.EspaceAdmin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import ma.ensaf.biblio.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ma.ensaf.biblio.Adapters.UserAdapter;
import ma.ensaf.biblio.Models.User;

public class EtudiantsActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ImageButton backBtn;
    private ArrayList<User> UserArrayList;
    private UserAdapter userAdapter;
    private RecyclerView recyclerView;
    private EditText SearchUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiants);

        firebaseAuth = FirebaseAuth.getInstance();

        loadEtudiant();

        backBtn = (ImageButton) findViewById(R.id.BackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.EtudiantBiblio);
        SearchUser = (EditText) findViewById(R.id.searchEv);

        SearchUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                try {

                    userAdapter.getFilter().filter(charSequence);

                }catch (Exception e){

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void loadEtudiant() {

        UserArrayList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserArrayList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    //get data
                    User model = ds.getValue(User.class);

                    //add to arraylist
                    UserArrayList.add(model);
                }
                //setupAdapter
                userAdapter = new UserAdapter(EtudiantsActivity.this,UserArrayList);

                //set Adapter to RecyclerView
                recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}